
#include "validate.h"

#include <unistd.h>
#include <string.h>
#include <my_global.h>
#include <mysql.h>

int validate(int sock, char** args) {

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

    // Build our query here
    char query[BUFSIZE];
    char email[BUFSIZE], pass[BUFSIZE];
    bzero(query, BUFSIZE);
    bzero(email, BUFSIZE);
    bzero(pass, BUFSIZE);

    /*
    for (int i = 0; args[i] != NULL; ++i) {
        if (strncmp(args[i], "email", 5) == 0)
            sprintf(email, "%s", args[i + 1]);
        if (strncmp(args[i], "password", 8) == 0)
            sprintf(pass, "%s", args[i + 1]);
    }*/

    sprintf(query, "select user_id from user where email='%s' and password='%s'",
            args[2], args[4]);

    // Perform the query
    mysql_query(con, query);

    // retrieve the result
    MYSQL_RES *result = mysql_store_result(con);
    /*if (result == NULL) {
        perror("ERROR getting result");
        exit(1);
    }*/

    int num_fields = mysql_num_fields(result);
    MYSQL_ROW row;
    MYSQL_FIELD *field;

    // Remove the result from the row
    row = mysql_fetch_row(result);

    // If it equals 1, then the email pass combo is valid, otherwise it
    // fails.
    int n = 0;
    if (result != NULL) {
        write(sock, row[0], strlen(row[0]));
    }

    else {
        write(sock, failed, strlen(failed));
    }

    // Clean up
    mysql_free_result(result);
    mysql_close(con);

    return 0;
}
