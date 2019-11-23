// Client side C/C++ program to demonstrate Socket programming
#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#include <netinet/tcp.h>
#include "../globals.h"
#include <stdlib.h>
#include "../Sockets/cJSON.h"

/**
 * Metodo para establecer un canal de comunicacion con el server
 * @param PORT puerto del server
 * @param IP ipdel server
 * @return estado de la operación
 */
int connectSocket(int PORT,char* IP)
{
    int sock = 0, valread;
    struct sockaddr_in serv_addr;
    char *hello = "Hello from client";
    char buffer[1024] = {0};
    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
        printf("\n Socket creation error \n");
        return -1;
    }

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(PORT);

    // Convert IPv4 and IPv6 addresses from text to binary form
    if(inet_pton(AF_INET,IP, &serv_addr.sin_addr)<=0)
    {
        printf("\nInvalid address/ Address not supported \n");
        return -1;
    }

    if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0)
    {
        printf("\nConnection Failed \n");
        return -1;
    }
    socketInt=sock;
    return 0;
}
/**
 * Envía una string a traves de un canal existente
 * @param string
 */
void writeSocket(char* string){
    char *newstr = malloc(strlen(string) + 2);
    strcpy(newstr, string);
    strcat(newstr, "\n");
    printf("[CLIENT] \"%s\"\n",newstr);
    send(socketInt,newstr,strlen(newstr),0);
    free(newstr);
}
/**
 * Termina la conexión con una palabra clave
 */
void closeSocket(){
    writeSocket("close");
}

/**
 * Lee una string entrante en un canal existente
 * @return string entrante
 */
char* readSocket(){
    char* buffer = calloc(1024, sizeof(char));
    read( socketInt , buffer, 1024);
    printf("[SERVER] \"%s\"\n",buffer );
    return buffer;
}

/**
 * Envia una solicitud de registro y devuelve el ID indicado
 * @param ID id asignado por el usuario
 * @return id asignado por el servidor
 */
int sendLoginRequest(int ID){
    cJSON *monitor = cJSON_CreateObject();
    cJSON_AddNumberToObject(monitor, "ID", ID);
    char* string = cJSON_PrintUnformatted(monitor);
    cJSON_Delete(monitor);

    writeSocket(string);

    char* response=readSocket();

    cJSON *monitor_json = cJSON_Parse(response);
    const cJSON *val = cJSON_GetObjectItemCaseSensitive(monitor_json, "Respuesta");
    //printf("%d\n",val->valueint);
    return  val->valueint;
}

/**
 * Envia un json para empezar el juego
 */
void sendStart(){
    cJSON *monitor = cJSON_CreateObject();
    cJSON_AddStringToObject(monitor, "Evento", "start");
    char* string = cJSON_PrintUnformatted(monitor);
    cJSON_Delete(monitor);

    writeSocket(string);
}
/**
 * Envia un json para notificar un movimiento
 * @param ID
 * @param PosX
 * @param PosY
 */
void sendMove(int ID,int PosX,int PosY){
    cJSON *monitor = cJSON_CreateObject();
    cJSON_AddStringToObject(monitor, "Evento", "move");
    cJSON_AddNumberToObject(monitor, "ID", ID);
    cJSON_AddNumberToObject(monitor, "PosX", PosX);
    cJSON_AddNumberToObject(monitor, "PosY", PosY);
    char* string = cJSON_PrintUnformatted(monitor);
    cJSON_Delete(monitor);
    writeSocket(string);
}
/**
 * Envia un json para notificar un cambio de nivel
 * @param top
 */
void sendTop(int top){
    cJSON *monitor = cJSON_CreateObject();
    cJSON_AddStringToObject(monitor, "Evento", "top");
    cJSON_AddNumberToObject(monitor, "Nivel", top);
    char* string = cJSON_PrintUnformatted(monitor);
    cJSON_Delete(monitor);
    writeSocket(string);
}
/**
 * Envia un json para notificar un bloque roto
 * @param nivel
 * @param PosX
 * @param ID jugador
 */
void sendBlock(int nivel,int PosX,int ID){
    cJSON *monitor = cJSON_CreateObject();
    cJSON_AddNumberToObject(monitor, "ID", ID);
    cJSON_AddStringToObject(monitor, "Evento", "block");
    cJSON_AddNumberToObject(monitor, "Nivel", nivel);
    cJSON_AddNumberToObject(monitor, "PosX", PosX);
    char* string = cJSON_PrintUnformatted(monitor);
    cJSON_Delete(monitor);
    writeSocket(string);
}
/**
 * Envia un json para notificar un cambio de vida
 * @param ID jugador
 * @param lives vidas
 */
void sendLife(int ID,int lives){
    cJSON *monitor = cJSON_CreateObject();
    cJSON_AddStringToObject(monitor, "Evento", "life");
    cJSON_AddNumberToObject(monitor, "ID", ID);
    cJSON_AddNumberToObject(monitor, "Vidas", lives);
    char* string = cJSON_PrintUnformatted(monitor);
    cJSON_Delete(monitor);
    writeSocket(string);
}
/**
 * Envia un json para notificar final de la partida
 */
void sendEnd(){
    cJSON *monitor = cJSON_CreateObject();
    cJSON_AddStringToObject(monitor, "Evento", "end");
    char* string = cJSON_PrintUnformatted(monitor);
    cJSON_Delete(monitor);
    writeSocket(string);
}
/**
 * ENvia un json para notificar destrucción de obstáculo
 * @param ID
 * @param IDe
 */
void sendDestroy(int ID,int IDe){
    cJSON *monitor = cJSON_CreateObject();
    cJSON_AddStringToObject(monitor, "Evento", "destroy");
    cJSON_AddNumberToObject(monitor, "ID", ID);
    cJSON_AddNumberToObject(monitor, "IDe", IDe);
    char* string = cJSON_PrintUnformatted(monitor);
    cJSON_Delete(monitor);
    writeSocket(string);
}

void startAction(){
    printf("START ACTION\n");
}
void moveAction(int ID,int PosX,int PosY){
    printf("MOVE ACTION %d %d %d\n", ID, PosX, PosY);
}
void topAction(int nivel){
    printf("TOP ACTION %d\n",nivel);
}
void blockAction(int nivel,int PosX,int ID){
    printf("BLOCK ACTION %d %d %d\n",nivel,PosX,ID);
}
void enemyAction(int nivel,int PosX,int IDe,char* name){
    printf("ENEMY ACTION %d %d %d %s\n",nivel,PosX,IDe,name);
}
void lifeAction(int ID,int lives){
    printf("LIFE ACTION %d %d\n", ID, lives);
}
void destroyAction(int ID,int IDe){
    printf("DESTROY ACTION %d %d\n", ID, IDe);
}
void endAction(){
    printf("END ACTION\n");
}

/**
 * Escucha por algún evento del servidor, lo clasifica y parsea los datos dados
 */
void listenGeneralEvent(){
    char* str=readSocket();
    cJSON *monitor_json = cJSON_Parse(str);
    const cJSON *eventJson = cJSON_GetObjectItemCaseSensitive(monitor_json, "Evento");
    char* event=eventJson->valuestring;
    //printf("$$$%s\n",event);
    if (strcmp(event,"start")==0){
        startAction();
    }
    else if (strcmp(event,"move")==0){
        int ID=cJSON_GetObjectItemCaseSensitive(monitor_json, "ID")->valueint;
        int PosX=cJSON_GetObjectItemCaseSensitive(monitor_json, "PosX")->valueint;
        int PosY=cJSON_GetObjectItemCaseSensitive(monitor_json, "PosY")->valueint;
        moveAction(ID,PosX,PosY);
    }
    else if (strcmp(event,"top")==0){
        int nivel=cJSON_GetObjectItemCaseSensitive(monitor_json, "Nivel")->valueint;
        topAction(nivel);
    }
    else if (strcmp(event,"block")==0){
        int ID=cJSON_GetObjectItemCaseSensitive(monitor_json, "ID")->valueint;
        int nivel=cJSON_GetObjectItemCaseSensitive(monitor_json, "Nivel")->valueint;
        int PosX=cJSON_GetObjectItemCaseSensitive(monitor_json, "PosX")->valueint;
        blockAction(nivel,PosX,ID);
    }
    else if (strcmp(event,"enemy")==0){
        int nivel=cJSON_GetObjectItemCaseSensitive(monitor_json, "Nivel")->valueint;
        int PosX=cJSON_GetObjectItemCaseSensitive(monitor_json, "PosX")->valueint;
        int IDe=cJSON_GetObjectItemCaseSensitive(monitor_json, "IDe")->valueint;
        char* name=cJSON_GetObjectItemCaseSensitive(monitor_json, "Nombre")->valuestring;
        enemyAction(nivel,PosX,IDe,name);
    }
    else if (strcmp(event,"life")==0){
        int ID=cJSON_GetObjectItemCaseSensitive(monitor_json, "ID")->valueint;
        int lives=cJSON_GetObjectItemCaseSensitive(monitor_json, "Vidas")->valueint;
        lifeAction(ID,lives);
    }
    else if (strcmp(event,"destroy")==0){
        int ID=cJSON_GetObjectItemCaseSensitive(monitor_json, "ID")->valueint;
        int IDe=cJSON_GetObjectItemCaseSensitive(monitor_json, "IDe")->valueint;
        destroyAction(ID,IDe);
    }
    else if (strcmp(event,"end")==0){
        endAction();
    }
    cJSON_Delete(monitor_json);
}



