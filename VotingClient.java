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

            // get message from server
            String line = "";
            while ((line = inputFromServer.readLine()) != null && line.equals("")) {
                System.out.println(line);
            }

            // get user input
            String userInput = "";
            userInput = inputFromUser.readLine();
            System.out.println("entered "+userInput);
            while (!isNumeric(userInput)) {
                System.out.print("Invalid option, enter one of the options above");
                userInput = inputFromUser.readLine();
            }

            // send message to server
            outputToServer.print(userInput + "\n");
            outputToServer.flush();

            while (true) {
                
            } // end while
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end main
} // end class