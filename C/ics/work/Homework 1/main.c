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
    printf("�������һ��ʮ�����Ƶ�����");
    scanf("%x",&a);
    printf("������ڶ���ʮ�����Ƶ�����");
    scanf("%x",&b);
    int c = generation(a,b);
    printf("%x\n",c);
    return 0;
}