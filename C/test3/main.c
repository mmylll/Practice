#include <stdio.h>
int prodct(int a,int b){
    int c = a*b;
    return c;
}
int main() {
    int x,y,p;
    printf("请输入第一个整数：");
    scanf("%d",&x);
    printf("请输入第二个整数：");
    scanf("%d",&y);
    p = prodct(x,y);
    printf("这两个数的乘积为：%d\n",p);
    printf("Hello World!\n");
    return 0;
}
int someFunc(char a[0],char b[0],int c1){
    b[0] = a[0];
    int c = c1+1;
    return 1;
}
