////////////////////////////////////////////////////////////
//
// The main for the child
// process created by onTrackd
// essentially. Basically handles
// inital operations and then 
// passes off requests to specific
// functions
//
// Needs to do:
//  1. Get username and password for validation purposes
//  2. Recieve requests in a loop until receiving an "end"
//  3. Send the request off to either get, put, or del
//
// To implement:
//  1. Validation on login
//
////////////////////////////////////////////////////////////

#include "process.h"
#include "validate.h"
#include "update.h"
#include "retrieve.h"
#include "del.h"
#include "create.h"

// Utilities
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

// MySQL headers
//#include <my_global.h>
//#include <mysql.h>

char** parse(char *);

int process(int sock) {

    int n = 0;
    char buffer[BUFSIZE];

    // Now that we have the basic information, we can get the request
    bzero(buffer, BUFSIZE);
    n = read(sock, buffer, BUFSIZE - 1);
    if (n < 0) {
        perror("ERROR on socket read");
        exit(1);
    }

    // Lets test the parser
    char **args = parse(buffer);

    if (strcmp(args[0], "val") == 0)
        validate(sock, args);

    if (strcmp(args[0], "get") == 0)
        retrieve(sock, args);

    if (strcmp(args[0], "put") == 0)
        update(sock, args);

    if (strcmp(args[0], "del") == 0)
        del(sock, args);

    if (strcmp(args[0], "create") == 0)
        createAccount(sock, args);

    bzero(buffer, BUFSIZE);
    n = read(sock, buffer, BUFSIZE - 1);
    if (n < 0) {
        perror("ERROR on socket read");
        exit(1);
    }

    return 0;
}


// Parses the initial request to find out
// what actions need to be taken
// Returns an array of character arrays, each
// with one word in it. Requests are delimited by ':'
char** parse(char *buffer) {

    // Find the size of the buffer so we can iterate
    // through it
    size_t size = strlen(buffer);

    // Count the number of strings to be extracted
    // using the delimiter
    int count = 1;
    for (size_t i = 0; i < size; ++i) {
        if (buffer[i] == ':')
            count = count + 1;
    }

    // Declare our argv style pointer array
    // Allocate the needed memory to store the 
    // character pointers
    char** args;
    args = malloc((count + 1) * sizeof(char*));  // memory for the char pointers
    
    // Variables to hold current arg we are writing to, the length of the
    // str that needs to be allocated, and the tmp buffer that will hold the
    // characters until they can be sprintf to the newly allocated arg
    int current = 0;
    int len = 0;
    char buf[BUFSIZE];
    bzero(buf, BUFSIZE);

    // Iterate through the received buffer, copy characters to tmp buf
    // and then allocate a new char array of the appropriate length
    // whenever we have a parsed word
    // Result should be an argv style str list that can be returned
    for (size_t i = 0; i <= size; ++i) {

        // We have a word.
        // Allocate the arg with an appropriately sized char array
        // Copy the tmp array into this new arg
        // move to next arg for next word, and reset length and tmp buf
        if (buffer[i] == ':' || buffer[i] == '\0') {
            args[current] = malloc((len + 1) * sizeof(char)); // mem for str
            bzero(args[current], (len + 1));
            sprintf(args[current], "%s", buf);
            args[current][len] = '\0';
            current = current + 1;
            len = 0;
            bzero(buf, BUFSIZE);
        }

        // Not a word, just add the character to the tmp buffer and increment len
        else {
            buf[len] = buffer[i];
            len = len + 1;
        }
    }

    // Make the array null terminated so it can be iterated through
    args[current] = NULL;
    return args;
}



