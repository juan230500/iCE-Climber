#include <stdio.h>
#include "Sockets/sockets2.c"

int main() {
    connectSocket(portServer,ipServer);
    usleep(1000*1000*1);
    writeSocket("hiiii");
    writeSocket("Hiiii2");
    writeSocket("Hiiii3");
    closeSocket();
    /*int val=sendLoginRequest(1,portClient,"127.0.0.1");
    printf("Response: %d",val);
    if (val){
        listenSocket(portClient);
    }*/
    /*for (int i = 0; i < 1; ++i) {
        sendMove(0,2,3);
        listenEvent();
        //usleep(1000*20);
    }*/

    //printf(listenSocket());
    //sendSocket("b\tc",portServer,ipServer);
    return 0;
}