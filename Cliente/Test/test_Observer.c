#include <stdio.h>
#include "../Sockets/sockets2.c"

int main() {
    connectSocket(portServer, ipServer);
    usleep(1000 * 10);
    int ID = sendLoginRequest(2);
    if (ID != -1) {
        for (int i=0;i<9;i++){
            listenGeneralEvent();
        }
        //sendStart();
    }
    closeSocket();
}