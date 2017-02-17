///////////////////////////////////////////////////////
//
// Handle account creation
//
// format is as follows
// "create:email:data:password:data:organization:data"
//
// Other fields HAVE to be attached after these items
//
// Those are the required fields to make a new account
// Still need to calculate user_id number though
//
///////////////////////////////////////////////////////

#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include "create.h"

#include <my_global.h>
#include <mysql.h>

int createAccount(int sock, char **args) {
    
    // Set up the connection to the SQL server
    MYSQL *con = mysql_init(NULL);
    if (con == NULL) {
        perror("ERROR initializing mysql");
        exit(1);
    }

    // Connect
    if (mysql_real_connect(con, "localhost", "root", "onTrack", 
                "mydb", 0, NULL, 0) == NULL) {
        perror("ERROR connecting to the database");
        exit(1);
    }

    // Build the query here
    
    /* i need to check */
    char query[BUFSIZE];
    bzero(query, BUFSIZE);

    sprintf(query, "select count(*) from user where email='%s'", args[1]);
    mysql_query(con, query);

    // get the result
    MYSQL_RES *result = mysql_store_result(con);
    if (result == NULL) {
        perror("ERROR getting result");
        exit(1);
    }

    MYSQL_ROW row;
    row = mysql_fetch_row(result);

    // Check if the account exists already
    if (strcmp(row[0], "1") == 0) {

        // return a message saying the email exists already
        int n = 0;
        char reply[BUFSIZE];
        sprintf(reply, "Failed:email");
        write(sock, reply, strlen(reply));
        return 0;
    }

    // Assuming the account does not already exist
    // Calculate the user_id number
    mysql_query(con, "select count(*) from user");
    result = mysql_store_result(con);
    if (result == NULL) {
        perror("ERROR getting result");
        exit(1);
    }

    row = mysql_fetch_row(result);
    
    // Now we can use this info to insert the needed items into an account
    bzero(query, BUFSIZE);
    //sprintf(query, "insert into user values(%d, 'removeme', '%s', null, null, '%s', null, '%s', null, null, null, null)", 
      //      atoi(row[0]) + 1, args[4], args[2], args[6]);

    sprintf(query, "insert into user (email, password, organization) values ('%s', '%s', '%s')", args[2], args[4], args[6]);

    mysql_query(con, query);

    const char *err = mysql_error(con);
    fprintf(stderr, "%s", err);

    // Now we need to add any additional items via update queries
    for (int i = 7; args[i] != NULL;) {
        bzero(query, BUFSIZE);
        sprintf(query, "update user set %s = '%s' where email='%s'", 
                args[i], args[i + 1], args[2]);
        mysql_query(con, query);
        const char *err = mysql_error(con);
        fprintf(stderr, "%s", err);
        i += 2;
    }

    // Get the user_id number to send back to the client
    bzero(query, BUFSIZE);
    sprintf(query, "select user_id from user where email='%s'", args[2]);
    mysql_query(con, query);
    result = mysql_store_result(con);
    row = mysql_fetch_row(result);

    // Send a response to let the client know the account was created
    bzero(query, BUFSIZE);
    sprintf(query, "Created:user_id:%s", row[0]);
    int n = write(sock, query, strlen(query));
    if (n < 0) {
        perror("ERROR failed to write to socket");
        exit(1);
    }

    // Clean up
    mysql_free_result(result);
    mysql_close(con);
   
    return 0;
}
