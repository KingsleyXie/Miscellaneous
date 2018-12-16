#include <stdio.h>
#include <unistd.h>
#include <pthread.h>

#define MAIN_SLEEP 1
#define SUB_SLEEP 1

int shared_var = 0;

void sub_thread()
{
    while(1)
    {
        shared_var--;
        printf("sub thread: %d\n",shared_var);
        sleep(SUB_SLEEP);
    }
}

int main()
{
    pthread_t thread_id;
    int pt_id = pthread_create(&thread_id, NULL, (void*)sub_thread, NULL);

    if(pt_id != 0)
    {
        printf("Error occured while creating thread\n");
    }
    else
    {
        while(1)
        {
            shared_var++;
            printf("main thread: %d\n",shared_var);
            sleep(MAIN_SLEEP);
        }
    }

    pthread_join(pt_id, NULL);

    return 0;
}
