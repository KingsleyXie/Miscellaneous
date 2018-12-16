#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/resource.h>

#define CH_MX 32

// Polyfill for boolean value types
typedef int bool;
#define true 1
#define false 0

bool start_with(const char *pre, const char *str);

int main(int argc, char* argv[])
{
    printf("\n\033[1;33m----------------------- WELCOME -----------------------\n");
    printf("Please enter the command to execute ");
    printf("(MAXIMUM %d CHARS)\nType `quit` to quit the program\033[0m\n\n", CH_MX);

    char buffer_cmd[CH_MX];
    char *cmd = buffer_cmd;
    char cwd[256];

    while (1)
    {
        getcwd(cwd, sizeof(cwd));
        printf("\033[1;36m(%s myshell) $ \033[0m", cwd);

        size_t ch_mx = CH_MX;
        getline(&cmd, &ch_mx, stdin);

        size_t cmd_end = (strchr(cmd, '\n') - cmd);

        if (strcmp(cmd, "quit\n") == 0)
        {
            printf("\033[1;31mBye\033[0m\n\n");
            break;
        }

        // Internal `exit` & `cd` command
        if (start_with("exit", cmd)) exit(0);
        if (start_with("cd", cmd))
        {
            char path[CH_MX] = {0};
            strncpy(path, cmd + 3, cmd_end - 3);
            chdir(path);
        }

        // Fork process for external jobs
        int pid = fork();

        if (pid < 0) printf("Error occured while forking\n");
        else if (pid == 0)
        {
            execl("/bin/sh", "sh", "-c", cmd, NULL);
        }
        else
        {
            // Background job ends with `&`
            if (cmd[cmd_end - 1] != '&') waitpid(pid, 0, 0);
        }
    }
}

bool start_with(const char *pre, const char *str)
{
    size_t lenpre = strlen(pre), lenstr = strlen(str);
    return lenstr < lenpre ? false : strncmp(pre, str, lenpre) == 0;
}
