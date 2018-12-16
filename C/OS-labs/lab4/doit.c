#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/resource.h>

#define CH_MX 32

long parse_microsec(struct timeval time);

int main(int argc, char* argv[])
{
    printf("\n\033[1;33m----------------------- WELCOME -----------------------\n");
    printf("Please enter the command to execute ");
    printf("(MAXIMUM %d CHARS)\nType `quit` to quit the program\033[0m\n\n", CH_MX);

    char buffer_cmd[CH_MX];
    char *cmd = buffer_cmd;

    while (1)
    {
        printf("\033[1;36m(doit) $ \033[0m");
        size_t ch_mx = CH_MX;
        getline(&cmd, &ch_mx, stdin);

        if (strcmp(cmd, "quit\n") == 0)
        {
            printf("\033[1;31mBye\033[0m\n\n");
            break;
        }

        struct timeval start,end;
        struct timezone tz;
        gettimeofday(&start, &tz);

        int pid = fork();

        if (pid < 0) printf("Error occured while forking\n");
        else if (pid == 0)
        {
            execl("/bin/sh", "sh", "-c", cmd, NULL);
        }
        else
        {
            waitpid(pid, 0, 0);
            gettimeofday(&end, &tz);

            struct rusage res_usg;
            getrusage(RUSAGE_CHILDREN, &res_usg);

            printf("\nCommand: %s", cmd);
            printf("Execute time: %ldms\n", parse_microsec(end) - parse_microsec(start));

            printf("User CPU time used: %ldms\n", parse_microsec(res_usg.ru_utime));
            printf("System CPU time used: %ldms\n", parse_microsec(res_usg.ru_stime));

            printf("Involuntary context switches: %ld\n",res_usg.ru_nivcsw);
            printf("Voluntary context switches: %ld\n",res_usg.ru_nvcsw);

            printf("Page reclaims (soft page faults): %ld\n",res_usg.ru_minflt);
            printf("Page faults (hard page faults): %ld\n\n",res_usg.ru_majflt);
        }
    }
}

long parse_microsec(struct timeval time)
{
    return time.tv_sec * 1000 + time.tv_usec / 1000;
}
