#include<stdio.h>
int generation(int a,int b){
    int c = (a>>16)<<16;
    int d = ((b<<16)>>16);
    if(d >= 0xffff0000){
        d = d - 0xffff0000;
    }
    return c+d;
}
int main() {
    int a,b;
    printf("请输入第一个十六进制的数：");
    scanf("%x",&a);
    printf("请输入第二个十六进制的数：");
    scanf("%x",&b);
    int c = generation(a,b);
    printf("%x\n",c);
    return 0;
}