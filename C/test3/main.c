#include <stdio.h>
int prodct(int a,int b){
    int c = a*b;
    return c;
}
int main() {
    int x,y,p;
    printf("�������һ��������");
    scanf("%d",&x);
    printf("������ڶ���������");
    scanf("%d",&y);
    p = prodct(x,y);
    printf("���������ĳ˻�Ϊ��%d\n",p);
    printf("Hello World!\n");
    return 0;
}
int someFunc(char a[0],char b[0],int c1){
    b[0] = a[0];
    int c = c1+1;
    return 1;
}
