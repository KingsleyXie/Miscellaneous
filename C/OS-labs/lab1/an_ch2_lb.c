#include <unistd.h>
#include <stdio.h>
#include <time.h>

int main()
{
    time_t curr_time;
    char* fmt_time;

    while(1)
    {
        curr_time = time(NULL);
        fmt_time = ctime(&curr_time);

        printf("Those output come from child, %s", fmt_time);
        sleep(1);
    }

    return 0;
}
