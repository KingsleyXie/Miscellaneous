#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/wait.h>
#include <sys/mman.h>
#include <sys/types.h>

#define RAND_UPPER_BOUND 1000;

// Polyfill for the `union semun` compile problem
#ifdef _SEM_SEMUN_UNDEFINED
union semun
{
    int val;
    struct semid_ds *buf;
    unsigned short *array;
    struct seminfo *__buf;
};
#endif

struct sembuf buf_s;
typedef int semaphore;
semaphore mutex = 1, buf_mtx = 1;
int *buffer, *reader;

void down(int *sem_id);
void up(int *sem_id);
void reader_come();
void writer_come();

int main(int argc, char* argv[])
{
    union semun smn;
    pid_t writer1,writer2;
    pid_t reader1,reader2,reader3;

    buffer = (int*)mmap(NULL, sizeof(int), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    reader = (int*)mmap(NULL, sizeof(int), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);

    mutex = semget(IPC_PRIVATE, 1, 0666 | IPC_CREAT);
    buf_mtx = semget(IPC_PRIVATE, 1, 0666 | IPC_CREAT);

    smn.val = 1;
    if (semctl(mutex, 0, SETVAL, smn) == -1) exit(1);

    smn.val = 1;
    if (semctl(buf_mtx, 0, SETVAL, smn) == -1) exit(1);

    if((writer1 = fork()) == 0) writer_come();
    else if((writer2 = fork()) == 0) writer_come();
    else if((reader1 = fork()) == 0) reader_come();
    else if((reader2 = fork()) == 0) reader_come();
    else if((reader3 = fork()) == 0) reader_come();
    else
    {
        waitpid(writer1, 0, 0);
        waitpid(writer2, 0, 0);
        waitpid(reader1, 0, 0);
        waitpid(reader2, 0, 0);
        waitpid(reader3, 0, 0);
    }
}

void down(int *sem_id)
{
    buf_s.sem_num = 0;
    buf_s.sem_op = -1;
    buf_s.sem_flg = SEM_UNDO;

    if (semop(*sem_id, &buf_s, 1) == -1) exit(1);
}

void up(int *sem_id)
{
    buf_s.sem_num = 0;
    buf_s.sem_op = 1;
    buf_s.sem_flg = SEM_UNDO;

    if (semop(*sem_id, &buf_s, 1) == -1) exit(1);
}

void reader_come()
{
    while(1)
    {
        down(&mutex);
        *reader = *reader + 1;
        printf("A reader has come, there are %d readers currently\n", *reader);
        if(*reader == 1) down(&buf_mtx);

        up(&mutex);
        sleep(rand() % 3);
        printf("The reader is reading %d\n", *buffer);
        down(&mutex);

        *reader = *reader - 1;
        printf("The reader has gone, there are %d readers currently\n", *reader);
        if(*reader == 0) up(&buf_mtx);

        up(&mutex);
        sleep(1);
    }
}

void writer_come()
{
    while(1)
    {
        printf("A writer has come\n");

        down(&buf_mtx);
        *buffer = rand() % RAND_UPPER_BOUND;
        printf("The writer is writing %d\n", *buffer);
        up(&buf_mtx);

        printf("The writer has gone\n");
        sleep(rand() % 5);
    }
}
