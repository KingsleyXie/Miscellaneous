#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/mman.h>
#include <sys/types.h>

#define MAXN 7

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
char* buffer;
int mutex, full_slot, empty_slot;

void show_curr_buffer();
void down(int *sem_id);
void up(int *sem_id);
void produce(pid_t pid, int base);
void consume(pid_t pid);

void main()
{
    srand(time(NULL));
    buffer = (char*)mmap(NULL, sizeof(char) * MAXN, PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    for (int i = 0; i < MAXN; i++) buffer[i] = ' ';

    union semun smn;
    pid_t producer1, producer2;
    pid_t consumer1, consumer2, consumer3;

    mutex = semget(IPC_PRIVATE, 1, 0666 | IPC_CREAT);
    full_slot = semget(IPC_PRIVATE, 1, 0666 | IPC_CREAT);
    empty_slot = semget(IPC_PRIVATE, 1, 0666 | IPC_CREAT);

    smn.val = 1;
    if (semctl(mutex, 0, SETVAL, smn) == -1) exit(1);

    smn.val = 0;
    if (semctl(full_slot, 0, SETVAL, smn) == -1) exit(1);

    smn.val = MAXN;
    if (semctl(empty_slot, 0, SETVAL, smn) == -1) exit(1);

    if ((producer1 = fork()) == 0) produce(getpid(), 65);
    else if ((producer2 = fork()) == 0) produce(getpid(), 97);
    else if ((consumer1 = fork()) == 0) consume(getpid());
    else if ((consumer2 = fork()) == 0) consume(getpid());
    else if ((consumer3 = fork()) == 0) consume(getpid());
    else
    {
        while (1)
        {
            show_curr_buffer();
            sleep(3);
        }
    }
}

void show_curr_buffer()
{
    down(&mutex);
    for(int i = 0; i < MAXN; i++)
    {
        printf("Current buffer: ");
        for (int j = 0; j < MAXN; j++)
            printf("%c ", buffer[j]);
        printf("\n");
    }
    up(&mutex);
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

void produce(pid_t pid, int base)
{
    while (1)
    {
        down(&empty_slot);
        down(&mutex);

        char chr = base + rand() % 26;
        for (int i = 0; i < MAXN; i++)
        {
            if (buffer[i] == ' ')
            {
                buffer[i] = chr;
                printf("Process %d is producing %c\n", pid, chr);
                break;
            }
        }

        up(&mutex);
        up(&full_slot);

        sleep(rand() % 5);
    }
}

void consume(pid_t pid)
{
    while (1)
    {
        down(&full_slot);
        down(&mutex);

        char chr;
        for (int i = 0; i < MAXN; i++)
        {
            if (buffer[i] != ' ')
            {
                chr = buffer[i];
                buffer[i] = ' ';
                printf("Process %d is consuming %c\n", pid, chr);
                break;
            }
        }

        up(&mutex);
        up(&empty_slot);

        sleep(rand() % 6);
    }
}
