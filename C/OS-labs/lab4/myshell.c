#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/resource.h>

#define CH_MX 32

int main(int argc, char* argv[])
{
    printf("\n\033[1;33m----------------------- WELCOME -----------------------\n");
    printf("Please enter the command to execute ");
    printf("(MAXIMUM %d CHARS)\nType `quit` to quit the program\033[0m\n\n", CH_MX);

    char buffer_cmd[CH_MX];
    char *cmd = buffer_cmd;

    while (1)
    {
        printf("\033[1;36m(myshell) $ \033[0m");
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
        }
    }
}
