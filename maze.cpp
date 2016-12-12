#include <cstdlib>
#include <cstdio>
#include <iostream>
using namespace std;

#define mazerange 9

int outsetx=0,outsety=0,terminalx=6,terminaly=6;
int maze[mazerange][mazerange]={0};
int dx[4]={0,1,0,-1},dy[4]={1,0,-1,0};
string pauses;

void printmaze(int presentx,int presenty)
{
	printf("Trying...\n");
	for(int i=0; i<mazerange; i++)
		for(int j=0; j<mazerange; j++)
		{
			if (i==presentx&&j==presenty) printf("o\t");
			else if (maze[i][j]) printf("*\t");	else printf("#\t");
			if (j==mazerange-1) printf("\n");
		}
}

void dfs(int x,int y)
{
	if (x==terminalx && y==terminaly)
	{
		printmaze(x,y);
		printf("Terminal Reached!\n");	
		exit(0);
	}

	for(int step=0; step<4; step++)
		if ((x+dx[step]>=0)&&(x+dx[step]<mazerange)&&(y+dy[step]>=0)&&(y+dy[step]<mazerange)&&!maze[x+dx[step]][y+dy[step]])
		{
			x=x+dx[step]; y=y+dy[step]; maze[x][y]=1;
			printmaze(x,y); getline(cin,pauses); dfs(x,y);
			maze[x][y]=0; x=x-dx[step]; y=y-dy[step];
		}
}

int main()
{
	printf("Searching possible way out..\n");
	maze[outsetx][outsety]=1;
	printmaze(outsetx,outsety);
	printf("\n");
	dfs(outsetx,outsety);
	return 0;
}

