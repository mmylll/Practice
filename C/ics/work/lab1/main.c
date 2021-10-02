#include <stdio.h>

int main() {

    printf("%d\n",~(~5|~6));
    printf("%x\n",(unsigned char)(0xffffffff>>24));
    printf("%x\n",0x00000056);
    printf("%x\n",0x100000000);
    printf("%x\n",((0x87654321>>0)&((0x7fffffff)>>0<<1)));
    printf("%x\n",(0x87654321>>0)&((1<<(32+(~0+1)))+(~1+1)));
    printf("%x\n",(0x87654321 >> 4) & 0xffffffff);
    printf("%x\n",(0xf7654321 >> 20));
    printf("%x\n",~(((1 << 32) >> 4) << 1));
    printf("%x\n",1<<31);
    printf("%x\n",!1);
    printf("%x\n",0xffffffff>>32);
    printf("%d\n",15+1>>1);

    printf("%x\n",!(0));

    return 0;
}
