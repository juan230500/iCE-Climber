#include <stdio.h>
#include "../Sockets/sockets2.c"

int main() {
    connectSocket(portServer, ipServer);
    usleep(1000 * 10);
    int ID = sendLoginRequest(2);
    if (ID != -1) {
        while (1){
            readSocket();
        }
        //sendStart();
    }
    closeSocket();
}