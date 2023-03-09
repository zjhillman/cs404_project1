import java.net.*;
import java.io.*;

public class VotingClient {
    public static void main (String args[]) {
        InetAddress hostName;
        int hostPort = 12320;

        try {
            hostName = InetAddress.getByName("localhost");

            // parse arguments
            if (args.length == 1)
                hostName = InetAddress.getByName(args[0]);
            if (args.length == 2) {
                hostName = InetAddress.getByName(args[0]);
                hostPort = Integer.parseInt(args[1]);
            }

            Socket socket = null;
            OutputStream outStream = null;
            InputStream inStream = null;


            // establish connection to server
            socket = new Socket(hostName, hostPort);
            System.out.println("Established connection to "+socket.getInetAddress().toString());

            // set output stream 
            outStream = socket.getOutputStream();
            PrintWriter output = new PrintWriter(new OutputStreamWriter(outStream));

            // set input stream
            inStream = socket.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));

            output.print("hello");

            // set user input
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            
            // send a message
            System.out.print("Please enter a message for the server: ");
            String message = userInput.readLine();


            output.close();
            input.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end main
} // end class