#include <stdio.h>
#include "../Sockets/sockets.c"

int main() {
    for (int i=0;i<10;i++){
        sendSocket("Hi from client",8090,"127.0.0.1");
        usleep(1000);
    }
    return 0;
}