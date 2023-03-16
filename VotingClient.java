import java.net.*;
import java.io.*;

public class VotingClient {
    private static InetAddress hostName;
    private static int hostPort = 12320;
    private static Socket socket;
    private static BufferedReader inputFromServer;
    private static PrintWriter outputToServer;
    private static BufferedReader inputFromUser;
    private static String exitCmd = ".";


    private static void shutdown() {
        try {
            outputToServer.close();
            inputFromServer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumeric(String str) {
        if (str == null) 
            return false;

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


    public static void main (String args[]) {
        try {
            hostName = InetAddress.getByName("localhost");

            // parse arguments
            if (args.length == 1)
                hostName = InetAddress.getByName(args[0]);
            if (args.length == 2) {
                hostName = InetAddress.getByName(args[0]);
                hostPort = Integer.parseInt(args[1]);
            }

            socket = null;
            OutputStream outStream = null;
            InputStream inStream = null;

            // establish connection to server
            socket = new Socket(hostName, hostPort);
            System.out.println("Established connection to "+socket.getInetAddress().toString());
            
            // set output stream 
            outStream = socket.getOutputStream();
            outputToServer = new PrintWriter(new OutputStreamWriter(outStream));

            // set input stream
            inStream = socket.getInputStream();
            inputFromServer = new BufferedReader(new InputStreamReader(inStream));

            // set user input
            inputFromUser = new BufferedReader(new InputStreamReader(System.in));

            // continously wait for a message and send a response
            while (true) {
                // wait for message
                System.out.println("Waiting for response...\n");
                String serverMessage;
                while ((serverMessage = inputFromServer.readLine()) != null && !serverMessage.equals("")) {
                    System.out.println(serverMessage);
                }

                // get response from user
                String response = inputFromUser.readLine();
                System.out.println("entered "+response);
                while ((!isNumeric(response)) && !response.equals(exitCmd)) {
                    System.out.print("Invalid option, enter a number: ");
                    response = inputFromUser.readLine();
                }
                
                // send response to server
                outputToServer.print(response + "\n");
                outputToServer.flush();
                
                if (response.equals(exitCmd)) {
                    shutdown();
                    return;
                }
            } // end while
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end main
} // end class