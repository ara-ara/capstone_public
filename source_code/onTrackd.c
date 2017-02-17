//////////////////////////////////
//
// Purpose:
// Server to handle requests and 
// database operations for OnTrack
//
// Implementation:
// Socket based server to receive 
// requests from OnTrack. Listens
// on some port and when it gets a
// connection it forks and runs either
// an update or a retrieval. These are
// functions that perform the actual 
// interactions with the database and
// then send the information back to
// the client. Uses mysql for the database
// and connects using API commands 
// defined in my_global.h and mysql.h
//
// Version 1.0
// Haleigh Robbins
// 10/17/16
//
///////////////////////////////////

// Basic includes
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

// Includes for sockets and internet connections
#include <netdb.h>
#include <netinet/in.h>

// Utility includes
#include <signal.h>

// User defined includes
#include "onTrackd.h"
#include "process.h"

// Signal handler for zombie children
struct sigaction sigchild_action = {
    .sa_handler = SIG_DFL,
    .sa_flags = SA_NOCLDWAIT
};

///////////////////////////////////////////////////////////
// Main's purpose is to create a socket and listen
// on some port for requests from the onTrack client.
// When it receives a request it forks the program
// and passes the request off to the forked process. 
// The forked process will simply call update or retrieve
// and then exit. These calls will be what actually 
// act on the database.
int main (int argc, char **argv) {

    // All the items needed to connect via sockets
    int sockfd, newsockfd, portno, clilen;
    char buffer[BUFSIZE];
    struct sockaddr_in serv_addr, cli_addr;
    int n, pid;

    // Initialize the server socket
    sockfd = socket(AF_INET, SOCK_STREAM, 0);

    // Check for errors on initialization
    if (sockfd < 0) {
        perror("ERROR opening socket");
        exit(1);
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));
    portno = PORT_NO;

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(portno);

    // Bind the host to the socket
    if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
        perror("ERROR on binding");
        exit(1);
    }

    // Start the signal handler
    sigaction(SIGCHLD, &sigchild_action, NULL);

    // Start listening for clients
    listen(sockfd, 5);
    clilen = sizeof(cli_addr);

    // Loop and accept new requests forking to child processes each time
    while (1) {

        // Get the client socket information
        newsockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &clilen);
        
        if (newsockfd < 0) {
            perror("ERROR on accept");
            exit(1);
        }

        // Create the child process and perform actions
        pid = fork();

        if (pid < 0) {
            perror("ERROR on fork");
            exit(1);
        }
    
        if (pid == 0) {

            // Don't need the parent info anymore
            close(sockfd);

            // Do processing 
            process(newsockfd);

            // End the child process as its work is finished
            close(newsockfd);
            exit(0);
        }

        else 
            close(newsockfd);
    }
}
