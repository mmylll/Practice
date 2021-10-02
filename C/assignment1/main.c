#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>
#define TOOL_LIB_PATH "./tool.so"
// declare function pointer type
typedef int(*CAC_FUNC)(int,int);
int main(){
        void  *handle;
        char *error;
        CAC_FUNC cac_func = NULL;
        // load dynamic library
        handle = dlopen(TOOL_LIB_PATH,RTLD_LAZY);
        if(!handle){
            printf("handle is null!\n");
                    return -1;
                }

        char* szerror = dlerror();
        if(szerror != NULL){
                printf("error message:%s\n",szerror);
                dlclose(handle);
                return -1;
            }
        // look up symbol "add"
        cac_func =(CAC_FUNC)dlsym(handle,"add");
        if(cac_func != NULL){
                int num = cac_func(5,5);
                printf("add result:%d\n",num);
                                        num = cac_func(10, 50);
                                        printf("add result:%d\n",num);
            }
                    cac_func = (CAC_FUNC)dlsym(handle, "sub");
        if (cac_func != NULL) {
            int num = cac_func(10, 3);
                printf("sub result:%d\n",num);
                        }
         //close the library
        dlclose(handle);
        cac_func = NULL;
        return 0;
    }
