#include <stdio.h>

int main()
{
   int a = 0;
   int b = 0;
   int max = 0;
   int min = 0;
   int temp = 0;
   printf("请输入第一个数：");
   scanf("%d",&a);
   printf("请输入第一个数：");
   scanf("%d",&b);
   if(b > a){
   temp = a;
   a = b;
   b = temp;
   }
   for (int i = 2; i < b+1; i++)
   {
      if(a % i == 0&&b % i == 0){
        min = i;
        max = min * (a / i) * (b / i);
      }
   }
   if(min == 0){
      printf("这两个数的最大公因数为：1");
      printf("\n");
      max = a*b;
      printf("这两个数的最小公倍数为：");
      printf("%d",max);
      printf("\n");
   }else
   {
      printf("这两个数的最大公因数为：");
      printf("%d", min);
      printf("\n");
      printf("这两个数的最小公倍数为：");
      printf("%d", max);
      printf("\n");
   }
   return 0;
}