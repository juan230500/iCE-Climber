// Client side C/C++ program to demonstrate Socket programming 
#include <stdio.h> 
#include <sys/socket.h> 
#include <arpa/inet.h> 
#include <unistd.h> 
#include <string.h> 
#define PORT 8080

int main(int argc, char const *argv[]) 
{ 
	int sock = 0, valread; 
	struct sockaddr_in serv_addr; 
	char hello[] = "{\"Evento\":\"life\",\"Nivel\":2,\"ID\":0, \"IP\":\"127.0.0.1\", \"Puerto\":8081}"; 
	char buffer[1024] = {0}; 
	for (int i=0;i<1;i++){
		if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) 
		{ 
			printf("\n Socket creation error \n"); 
			return -1; 
		} 
		serv_addr.sin_family = AF_INET; 
		serv_addr.sin_port = htons(PORT); 
		
		// Convert IPv4 and IPv6 addresses from text to binary form 
		if(inet_pton(AF_INET, "127.0.0.1", &serv_addr.sin_addr)<=0) 
		{ 
			printf("\nInvalid address/ Address not supported \n"); 
			return -1; 
		} 
		if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) 
		{ 
			printf("\nConnection Failed \n"); 
			return -1; 
		} 
        for (int i = 0; i <5 ; i++){
		send(sock , hello , strlen(hello) , 0 ); 
		printf("Sent: \"%s\"\n",hello);
		valread = read( sock , buffer, 1024); 
		printf("%s %d\n",buffer,i ); 
		//usleep(1000*1000);
        }
	}
	
	return 0; 
} 

