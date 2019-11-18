#include <stdio.h>
#include "../Socket/sockets.c"

int main() {
    for (int i=0;i<100;i++){
        sendSocket("0"+i,8090,"127.0.0.1");
        usleep(100);
    }
    return 0;
}