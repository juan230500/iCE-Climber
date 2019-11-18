#include <stdio.h>
#include "Socket/sockets.c"

int main() {
    printf("Hello, World!\n");
    sendSocket("dsadsa123",8080,"localhost");
    //printf(listenSocket());
    return 0;
}