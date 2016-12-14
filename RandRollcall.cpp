#include <windows.h>
#include <iostream>
#include <cstring>
#include <cstdio>
#include <cstdlib>
#include <ctime>
#include <cmath>
#include <string>
using namespace std;

int r1,r2,t1,t2,n1,n2,nround=1,roundtemp,turn=0,turntemp,numtemp,nclass,nnum,temp=1,people,n,p=0,k=1;
int a[100],num[100],presentnum[10];
FILE*file_record,*file_data;  string s;  bool judgeok,numok;

void inputclass(int *class0)
{
	do
	{
		cout<<"Rollcall Class?  [6,11]    ";  cin>>s;  judgeok=0;
		if ((s.size()==1)&&(s[0]=='6'))  
			{
			  *class0=6;  judgeok=1; file_data=fopen("./Files/data6.txt","r+");  
			  file_record=fopen("./Files/record6.txt","a");people=58;
			}
			else if((s.size()==2)&&(s[0]=='1')&&(s[1]=='1'))  
				{
				  *class0=11;  judgeok=1;file_data=fopen("./Files/data11.txt","r+");
				  file_record=fopen("./Files/record11.txt","a");people=52;
				}
				   else cout<<"        Input Error! "<<endl;
	}
	while(!judgeok);
}


void inputnum(int *num0)
{
	do
	{
		cout<<"Needed Number?   [1..9]    "; cin>>s;  judgeok=0;
		if ((s.size()==1)&&(s[0]>='0')&&(s[0]<='9'))  {*num0=s[0]-48;  judgeok=1;}
		  else cout<<"        Input Error! "<<endl;
	}
	while(!judgeok);
}	



int main()
{
	int *class0=&nclass,*num0=&nnum;  srand((unsigned)time(NULL)); inputclass(class0); inputnum(num0);
	memset(num,0,sizeof(num));  memset(presentnum,0,sizeof(presentnum));
	while(fscanf(file_data,"%d%d%d",&roundtemp,&turntemp,&numtemp)==3)  
	{   
		turn=turntemp; if(roundtemp==nround+1) {nround++; memset(num,0,sizeof(num));} num[numtemp]=1;
	}
	if(nround>99) {cout<<"END!"; return 0;}    cout<<endl<<endl<<endl;
	cout<<"Rollcall Class="<<nclass<<"       Needed Number="<<nnum<<endl;
	for(n=0; n<=38; ++n) cout<<"*";  cout<<endl;
	while(temp<=nnum)
	{
		if(turn==people)  {nround++; turn=0; memset(num,0,sizeof(num));} 
		k=0; for(n=1; n<=people; ++n)  if (num[n]==0) a[++k]=n;
		n=a[rand()%k+1];  n=a[rand()%k+1];  n=a[rand()%k+1];
		numok=1;  for(int i=1; i<=10; ++i)  if (n==presentnum[i]) numok=0;
		if(numok)
		{
			num[n]=1; turn++; r1=nround/10; r2=nround%10; t1=turn/10; t2=turn%10; n1=n/10; n2=n%10;
			fprintf(file_data,"%d%d %d%d %d%d\n",r1,r2,t1,t2,n1,n2); temp++;  presentnum[++p]=n;
			fprintf(file_record,"Round %d%d    Turn%d%d: No.%d%d\n",r1,r2,t1,t2,n1,n2);   Sleep(267); 
			cout<<"Round "<<r1<<r2<<"    Turn"<<t1<<t2<<": No."<<n1<<n2<<endl;
		}
	}
    for(n=0; n<=36; ++n) fprintf(file_record,"*");   fprintf(file_record,"\n"); 
    fclose(file_record);  fclose(file_data);  system("pause");  return 0;
}
