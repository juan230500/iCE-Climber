#include <stdio.h>
#include "../Sockets/sockets2.c"

int main() {
    connectSocket(portServer, ipServer);
    usleep(1000 * 10);
    int ID = sendLoginRequest(0);
    if (ID != -1) {
        usleep(1000 * 1000 * 2);
        sendStart();
        //sendStart();
    }
    closeSocket();
}