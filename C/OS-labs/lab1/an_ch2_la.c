#include <unistd.h>
#include <stdio.h>
// #include <sys/wait.h>

int main(int argc, char *argv[])
{
    int pid = fork();

    if (pid < 0) printf("Error occured while forking\n");
    else if (pid == 0)
    {
        execv("./lab1/an_ch2_lb", argv);
    }
    else
    {
        // waitpid(pid, 0, 0);
    }

    return 0;
}
