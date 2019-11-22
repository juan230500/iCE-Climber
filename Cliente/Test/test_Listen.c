#include <stdio.h>
#include "../Sockets/sockets.c"

int main() {
    while (1){
        printf(listenSocket(8090));
    }
    return 0;
}