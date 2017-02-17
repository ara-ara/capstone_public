#########################
#
# Must use these flags to
# compile for MariaDB
#
# for Amazon server version
# -I/usr/include/mysql
# -L/usr/lib64 -lmysqlclient
#
#########################

all: onTrackd

onTrackd: onTrackd.c process.c retrieve.c update.c del.c validate.c create.c onTrackd.h retrieve.h update.h del.h process.h validate.h create.h
	gcc -I/usr/include/mysql onTrackd.c retrieve.c update.c del.c process.c validate.c create.c -L/usr/lib64 -lmysqlclient -o onTrackd

clean:
	rm -f onTrackd
