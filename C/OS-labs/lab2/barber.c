#include <time.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

#define MAXN 5

int cust_no, cust_cnt = 0;
int queue[MAXN], head = 0, tail = 0;
int is_studio_empty = 1, is_barber_sleeping = 1;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond;

void enqueue(int num);
int dequeue();
void* barber_work(void * t_id);
void* customer_come();

int main(int argc, char* argv[])
{
    printf("The barber is sleeping now\n");
    srand(time(NULL));

    pthread_t pt_bar, pt_cus;
    pthread_cond_init(&cond, 0);

    pthread_create(&pt_bar, NULL, barber_work, NULL);
    pthread_create(&pt_cus, NULL, customer_come, NULL);
    pthread_join(pt_bar, NULL);
    pthread_join(pt_cus, NULL);

    pthread_cond_destroy(&cond);
    pthread_mutex_destroy(&mutex);
}

void enqueue(int num)
{
    queue[tail] = num;

    if (tail == MAXN - 1) tail = 0;
    else tail++;
}

int dequeue()
{
    int ret = queue[head];

    if (head == MAXN-1) head = 0;
    else head++;
    return ret;
}

void* barber_work(void * t_id)
{
    while(1)
    {
        pthread_mutex_lock(&mutex);
        if (is_studio_empty && cust_cnt > 0)
        {
            cust_no = dequeue();
            is_studio_empty = 0;
            cust_cnt--;
        }

        if (!is_studio_empty)
        {
            printf("The barber is getting a haircut for customer %d\n", cust_no);
            sleep(3);
            printf("The customer %d has finished his haircut\n", cust_no);
            is_studio_empty = 1;
        }

        if (cust_cnt == 0)
        {
            is_studio_empty = 1;
            if (!is_barber_sleeping) printf("The barber get back to sleep now\n");
        }

        pthread_mutex_unlock(&mutex);
    }
}

void* customer_come()
{
    int position = 0;
    while(1)
    {
        printf("Customer %d has come\n", position);

        if (is_studio_empty)
        {
            is_studio_empty = 0;
            cust_no = position;
            pthread_cond_signal(&cond);
            is_barber_sleeping = 0;
        }
        else if (cust_cnt < MAXN)
        {
            printf("There are %d seats left and he is sitting down to wait\n", MAXN - cust_cnt);
            enqueue(position);
            cust_cnt++;
        }
        else if (cust_cnt >= MAXN)
        {
            printf("There is no empty seat so he is leaving\n");
        }

        position++;
        sleep(rand() % 3);
    }
}
