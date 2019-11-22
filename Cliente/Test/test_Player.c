#include <stdio.h>
#include "../Sockets/sockets2.c"

int main() {
    connectSocket(portServer, ipServer);
    //usleep(1000 * 10);
    int ID = sendLoginRequest(0);
    if (ID != -1) {
        //usleep(1000 * 1000 * 2);
        for (int i=0;i<1;i++){
            sendStart();
            sendMove(1,2,4);
            sendTop(5);
            sendBlock(5,10,1);
            sendLife(1,3);
            sendDestroy(1,50);
            sendEnd();
        }
        listenGeneralEvent();
        //sendStart();
    }
    closeSocket();
}