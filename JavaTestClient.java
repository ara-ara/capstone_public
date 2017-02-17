//
// Java Client implementation to test communication
// with server.
//
// Andreas Aceto
// 10/21/2016
//

import java.io.*;
import java.net.*;

public class JavaTestClient {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
		
            System.err.println(
                "Usage: java JavaTestClient <host name> <port number>");
				
            System.exit(1);
			
        }

        String hostName = args[0];
		
        int portNumber = Integer.parseInt(args[1]);
	
	// --------------------------------------------------------------
	// Try to establish connection with socket and initialize buffers
	// for communication
	// --------------------------------------------------------------
	    
        try (
		
            Socket onTrkSock = new Socket(hostName, portNumber);
			
            PrintWriter out =
                new PrintWriter(onTrkSock.getOutputStream(), true);
				
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(onTrkSock.getInputStream()));
					
            BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in))
					
        ) {
		
            String testInput;
			
            while ((testInput = stdIn.readLine()) != null) {
			
                out.println(testInput);
				
                System.out.println("Response: " + in.readLine());
				
            }
        } catch (UnknownHostException e) {
		
            System.err.println("Don't know about host " + hostName);
			
            System.exit(1);
			
        } catch (IOException e) {
		
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
				
            System.exit(1);
			
        } 
    }
}
