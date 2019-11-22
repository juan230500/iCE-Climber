#include <stdio.h>
#include "../Sockets/cJSON.h"
#include "../Sockets/sockets.c"
#include "../globals.h"


int sendLoginRequest(int ID,int port,char* ip){
    cJSON *monitor = cJSON_CreateObject();
    cJSON_AddNumberToObject(monitor, "ID", ID);
    cJSON_AddNumberToObject(monitor, "Puerto", port);
    cJSON_AddStringToObject(monitor, "IP", ip);
    char* string = cJSON_PrintUnformatted(monitor);
    cJSON_Delete(monitor);

    sendSocket(string,portServer,ipServer);

    char* response=listenSocket(portClient);

    cJSON *monitor_json = cJSON_Parse(response);
    const cJSON *val = cJSON_GetObjectItemCaseSensitive(monitor_json, "Respuesta");
    //printf("%d\n",val->valueint);
    return  val->valueint;
}

void a(){
    printf("$");
}