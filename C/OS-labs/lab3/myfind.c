#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>
#include <dirent.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/resource.h>

#define CH_MX 100
#define DEFAULT_DIR "."

int name_isset, prune_isset, mtime_isset, ctime_isset, print_isset;
int mtime_val, ctime_val;
char path[CH_MX], name_val[CH_MX], prune_val[CH_MX];

struct stat file_stat;
long secperday = 24 * 60 * 60;

void reset_status();
void list_files(char* dirpath);
int filestat_match(char* filename);
int dfs_search(char* filename, char* pattern, int i, int j);

int main(int argc, char* argv[])
{
    printf("\n\033[1;33m----------------------- WELCOME -----------------------\n");
    printf("Please enter the command to execute\n");
    printf("Type `quit` to quit the program\033[0m\n\n");

    char cmd[CH_MX], chr;
    while (1)
    {
        printf("\033[1;36m(myfind) $ \033[0m");
        if ((chr = getchar()) == '\n') continue;
        else
        {
            ungetc(chr, stdin);
            scanf("%s", cmd);
        }

        if (strcmp(cmd, "quit") == 0)
        {
            printf("\033[1;31mBye\033[0m\n\n");
            break;
        }

        if (strcmp(cmd, "myfind") != 0)
        {
            printf("Usage: myfind PATH -option param ...\n");
            while (getchar() != '\n');
            continue;
        }

        if (getchar() != '\n')
        {
            chr = getchar();
            ungetc(chr, stdin);
            if (chr == '-') strcpy(path, DEFAULT_DIR);
            else scanf("%s", path);
        }
        else
        {
            print_isset = 1;
            list_files(DEFAULT_DIR);
            continue;
        }

        reset_status();
        while ((chr = getchar()) != '\n')
        {
            ungetc(chr, stdin);
            scanf("%s", cmd);
            if (strcmp(cmd, "-name") == 0) { name_isset = 1; scanf("%s", name_val); }
            else if (strcmp(cmd, "-prune") == 0) { prune_isset = 1; scanf("%s", prune_val); }
            else if (strcmp(cmd, "-mtime") == 0) { mtime_isset = 1; scanf("%d", &mtime_val); }
            else if (strcmp(cmd, "-ctime") == 0) { ctime_isset = 1; scanf("%d", &ctime_val); }
            else if (strcmp(cmd, "-print") == 0) { print_isset = 1; }
            else { printf("Unknown command!\n"); while (getchar() != '\n'); break; }
        }
        list_files(path);
    }
}

void reset_status()
{
    name_isset = 0;
    prune_isset = 0;
    mtime_isset = 0;
    ctime_isset = 0;
    print_isset = 0;
}

void list_files(char* dirpath)
{
    DIR *dir;
    struct dirent *ptr;
    char filepath[CH_MX];

    dir = opendir(dirpath);
    if (dir == NULL)
    {
        printf("Directory %s does not exist!\n", dirpath);
        exit(0);
    }

    while ((ptr = readdir(dir)) != NULL)
    {
        if ((strcmp(ptr->d_name, ".") == 0 || strcmp(ptr->d_name, "..") == 0)) continue;
        strcpy(filepath, dirpath); strcat(filepath, "/"); strcat(filepath, ptr->d_name);

        if (ptr->d_type == DT_DIR && strcmp(prune_val, ptr->d_name)) list_files(filepath);

        if (ptr->d_type == DT_REG)
        {
            if ((stat(filepath, &file_stat)) == -1) continue;
            if (filestat_match(ptr->d_name) && print_isset) printf("%s/%s\n", dirpath, ptr->d_name);
        }
    }
    closedir(dir);
}

int filestat_match(char* filename)
{
    if (name_isset && !dfs_search(filename, name_val, 0, 0)) return 0;

    struct timeval tv;
    struct timezone tz;
    gettimeofday(&tv, &tz);

    long tvsc = tv.tv_sec;

    if (mtime_isset)
    {
        long fmt = file_stat.st_mtime, offset = mtime_val * secperday;
        if (mtime_val >= 0)
        {
            if (!(fmt <= (tvsc - offset))) return 0;
        }
        else
        {
            if (!(fmt > (tvsc + offset)) && (fmt <= tvsc)) return 0;
        }
    }

    if (ctime_isset)
    {
        long fct = file_stat.st_ctime, offset = ctime_val * secperday;
        if (ctime_val >= 0)
        {
            if (!(fct <= (tvsc - offset))) return 0;
        }
        else
        {
            if (!(fct > (tvsc + offset)) && (fct <= tvsc)) return 0;
        }
    }

    return 1;
}

int dfs_search(char* filename, char* pattern, int i, int j)
{
    if (filename[i] == 0 && pattern[j] == 0) return 1;

    if (pattern[j] == 0) return 0;
    if (pattern[j] == '?') return dfs_search(filename, pattern, i + 1, j + 1);
    else
    {
        if (pattern[j] == '*')
        {
            int len = strlen(filename);
            for (int k = i; k <= len; k++)
                if (dfs_search(filename, pattern, k, j + 1)) return 1;
            return 0;
        }
        else
        {
            if (pattern[j] == filename[i]) return dfs_search(filename, pattern, i + 1, j + 1);
            else return 0;
        }
    }
    return 0;
}
