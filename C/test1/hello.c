#include <stdio.h>
#include <io.h>
#include <fcntl.h>
#include <sys/types.h>
#include<conio.h>
#include<stdlib.h>
#include<malloc.h>
#include <time.h>
#include <stdio.h>
#include <unistd.h>


int main(){
    fork()
    char c1,c2;
    int fd1 = open("foo.txt", O_RDONLY);
    int fd2 = open("foo.txt", O_RDONLY);
    pid_t pid;
    pid = fork();
    if (!fork()) {
        read(fd1, &c1, 1);
        printf("c_1 = %c",c1);
        read(fd2, &c2, 1);
        printf("c_2 = %c",c2);
        }else{
         pid_t pid;
         if((pid == wait(NULL)) > 0){
             read(fd1, &c1, 1);
             printf("c_3 = %c",c1);
             }
         }
}