#include <stdio.h>
#include "Sockets/submit.c"

int main() {
    int val=sendLoginRequest(1,portClient,"127.0.0.1");
    printf("Response: %d",val);
    if (val){
        listenSocket(portClient);
    }
    //printf(listenSocket());
    //sendSocket("b\tc",portServer,ipServer);
    return 0;
}