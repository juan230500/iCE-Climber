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

void writeSocket(char* string){
    char *newstr = malloc(strlen(string) + 2);
    strcpy(newstr, string);
    strcat(newstr, "\n");
    printf("[CLIENT] \"%s\"\n",newstr);
    send(socketInt,newstr,strlen(newstr),0);
    free(newstr);
}

void closeSocket(){
    writeSocket("close");
}

char* readSocket(){
    char* buffer = calloc(1024, sizeof(char));
    read( socketInt , buffer, 1024);
    printf("[SERVER] \"%s\"\n",buffer );
    return buffer;
}

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

void sendStart(){
    cJSON *monitor = cJSON_CreateObject();
    cJSON_AddStringToObject(monitor, "Evento", "start");
    char* string = cJSON_PrintUnformatted(monitor);
    cJSON_Delete(monitor);

    writeSocket(string);
}

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

