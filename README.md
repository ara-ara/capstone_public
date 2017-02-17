# OnTrack 
OnTrack is a fitness app for Android with a specific focus on benefitting track and field athletes. It was developed as a part of a Capstone project at Kent State University in 2016. The developers are Andy Aceto, Becca Leppelmeier, Haleigh Robbins, and Tim Strawbridge. 

## Requirements 
    * Android Studio, or some IDE for compiling Android applications
    * An Android emulator (one is provided by Android Studio)
    * A Unix based machine, preferably Linux. Not tested on other platforms
    * MySQL server to run the database
    * MySQL C connector for API calls that the server uses
    * A static host for the server so that the app can call to the database
    * A C compiler, preferably Clang, but it currently works with GCC as well

## How to Use 
This repository does not provide links to pre-compiled libraries. You will need to compile the source code as follows:
   1. The server source code is stored in the folder labeled source_code. There is a Makefile provided, however it may need to be altered as the location of the MySQL libraries on your system may differ from the locations we installed at. Just make sure to probably link and include the MySQL libraries in the Makefile to ensure it compiles without error. Additionally, change the compiler as you see fit (only tested with Clang and GCC so use other compilers at your own risk).
   2. Install MySQL server. Source code can be found [here](https://github.com/mysql/mysql-server), though I would recommend using your distribution's package manager to install MySQL so as to avoid dependency complications.
   3. Install the MySQL C connector. This may be available via your distribution's package manager, however, the source code can be found [here](https://dev.mysql.com/downloads/connector/c/). You can also find RPM packages here to ease in installation on some systems. 
   4. Run the db_updated.sql to create the database on the MySQL server. This is as simple as running `source /path_to_db_updated.sql` inside MySQL server. 
   5. Install Android Studio or some other Android IDE to compile the application with. Installation files and information can be found [here](https://developer.android.com/studio/index.html). Once installed, add the OnTrack application code to the IDE, it is stored in the folder labeled OnTrack. After adding the source simply click the build icon on the menu bar to compile the code and build a Andoird application file (.apk) that can then be used to install the app on either an emulator or actual Android device. 

## Some important notes
There are some pieces of this application that are hardcoded at this time. Please be sure to adjust these as necessary before trying to build the app for yourself. IP addresses are hardcoded on the client side. The server side has the database name, user, and password hardcoded. The app will absolutely fail if you don't adjust these. Also, while highly unlikely, you may need to change the port number for the server if you have a conflict on your machine. Finally, the ssh key onTrack.pem and the dns information will be irrelevant. These are specific to our AWS server instance that was used to host the server. You will need your **own host** to have client/server communication.

Enjoy!
