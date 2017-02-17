/////////////////////////////////////////
//
// Retrieve.c
//
// Purpose:
//  Universal process to retrieve 
//  requested information from the
//  database and send that information
//  back to the client.
//
/////////////////////////////////////////

#include "retrieve.h"

#include <unistd.h>
#include <string.h>
#include <my_global.h>
#include <mysql.h>

int retrieve(int sock, char **args) {
        
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
    char query[BUFSIZE];
    bzero(query, BUFSIZE);

    // Queries look like this 
    // get:view_name:attribute_to_test_against:data
    // so for something like user
    // get:view_user:email:someone@something
    // or for event
    // get:view_event:email:someone@thing:event_name:trackmeet
    sprintf(query, "select * from %s where %s='%s'", 
            args[1], args[2], args[3]);

    // This part will add on any extra pieces that need to be compared against 
    // to get the correct view for the user
    int i = 4;
    while (args[i] != NULL) {
        strcat(query, " and ");
        strcat(query, args[i]);
        strcat(query, "='");
        strcat(query, args[i + 1]);
        strcat(query, "'");
        i += 2;
    }

    // Perform the query
    mysql_query(con, query);

    // Store the result
    MYSQL_RES *result = mysql_store_result(con);
    if (result == NULL) {
        perror("ERROR getting result");
        exit(1);
    }

    int num_fields = mysql_num_fields(result);
    MYSQL_ROW row;
    MYSQL_FIELD *field;

    // Repurpose the query array to hold the response to the client
    bzero(query, BUFSIZE);

    // Create the response
    int flag = 0;
    while ((row = mysql_fetch_row(result))) {

        // Get an array of the column headers
        field = mysql_fetch_fields(result);
        for (int i = 0; i < num_fields; ++i) {
            if (flag)
                strcat(query, ":");

            // For each item, add the field name followed by
            // a colon, followed by the row data for that field
            strcat(query, field[i].name);
            strcat(query, ":");
            strcat(query, row[i]);

            if (!flag)
                flag = 1;
        }

        int n = write(sock, query, strlen(query));
        if (n < 0) {
            perror("ERROR writing to socket");
            exit(1);
        }
    }

    // Clean up
    mysql_free_result(result);
    mysql_close(con);

    return 0;    

}
