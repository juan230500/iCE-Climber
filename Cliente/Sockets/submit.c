#include <stdio.h>
#include "../Sockets/cJSON.h"
#include "../Sockets/sockets.c"
#include "../globals.h"
void listenEvent(){
    char* string=listenSocket(portClient);
    cJSON *monitor_json = cJSON_Parse(string);
    const cJSON *eventJson = cJSON_GetObjectItemCaseSensitive(monitor_json, "Evento");
    char* event=eventJson->valuestring;
    if (strcmp(event,"move")==0){
        printf("MOVE ACTION\n");
        const cJSON *IDJson = cJSON_GetObjectItemCaseSensitive(monitor_json, "ID");
        const cJSON *XJson = cJSON_GetObjectItemCaseSensitive(monitor_json, "PosX");
        const cJSON *YJson = cJSON_GetObjectItemCaseSensitive(monitor_json, "PosY");
        moveAction(IDJson->valueint,XJson->valueint,YJson->valueint);
    }
}

void moveAction(int ID,int PosX,int PosY){

}

void sendMove(int ID,int PosX,int PosY){
    cJSON *monitor = cJSON_CreateObject();
    cJSON_AddStringToObject(monitor, "Evento", "move");
    cJSON_AddNumberToObject(monitor, "ID", ID);
    cJSON_AddNumberToObject(monitor, "PosX", PosX);
    cJSON_AddNumberToObject(monitor, "PosY", PosY);
    char* string = cJSON_PrintUnformatted(monitor);
    cJSON_Delete(monitor);

    sendSocket(string,portServer,ipServer);
}

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