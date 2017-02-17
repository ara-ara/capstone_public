////////////////////////////////////////
//
// Purpose:
//  Handles deletion of information
//  from the database should the 
//  user decide to remove a 
//  routine or delete their 
//  account.
//
//  Queries look like this
//  del:table_name:unique_field:data
//
////////////////////////////////////////


#include "del.h"

#include <unistd.h>
#include <string.h>
#include <my_global.h>
#include <mysql.h>

int del(int sock, char **args) {

    // Set up the connection to the sql server
    MYSQL *con = mysql_init(NULL);
    if (con == NULL) {
        perror("Error initializing mysql");
        exit(1);
    }

    // Connect
    if (mysql_real_connect(con, "localhost", "root", "onTrack",
                "mydb", 0, NULL, 0) == NULL) {
        perror("ERROR connecting to the database");
        exit(1);
    }

    // Build the query
    char query[BUFSIZE];
    bzero(query, BUFSIZE);

    sprintf(query, "delete from %s where %s='%s'", args[1], 
            args[2], args[3]);

    mysql_query(con, query);

    return 0;
}
