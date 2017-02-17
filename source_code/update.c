////////////////////////////
//
// Perform update operations
// on the database.
//
////////////////////////////

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include "update.h"

// Add our sql includes
#include <my_global.h>
#include <mysql.h>


// Performs update operations on the sql database
int update(int sock, char **args) {
    
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

    // Build the query
    char query[BUFSIZE], UID[BUFSIZE];
    bzero(query, BUFSIZE);
    bzero(UID, BUFSIZE);

    // We want to grab the user_id since this is used throughout the database
    // as a foreign key
    sprintf(query, "select user_id from user where email='%s'", args[1]);
    mysql_query(con, query);

    // Get the result
    MYSQL_RES *result = mysql_store_result(con);
    if (result == NULL) {
        perror("ERROR getting result");
        exit(1);
    }

    // We retrieve the user_id from the result 
    // And can use this for update operations
    int num_fields = mysql_num_fields(result);
    MYSQL_ROW row;
    MYSQL_FIELD *field;

    row = mysql_fetch_row(result);

    // If the row returns null that means the item doesn't exist
    if (row == NULL) {
        
    }
    sprintf(UID, "%s", row[0]);
    fprintf(stderr, "%s", UID);

    // Clean up
    mysql_free_result(result);
    mysql_close(con);

    return 0;
}
