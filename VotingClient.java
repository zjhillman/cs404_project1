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

            while (true) {
                // set output stream 
                outStream = socket.getOutputStream();
                PrintWriter output = new PrintWriter(new OutputStreamWriter(outStream));

                // set input stream
                inStream = socket.getInputStream();
                BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
                if (input.ready())
                    System.out.println("input ready");

                // set user input
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

                // send a message
                System.out.print("Please enter a message for the server: ");
                String message = userInput.readLine();;
                output.print(message + "\n");
                output.flush();

                if (message.equalsIgnoreCase("exit")) {
                    output.close();
                    input.close();
                    break;
                }

                // wait for response
                String response = "";
                response = input.readLine();
                System.out.println(response + "\n");
            } // end while

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end main
} // end class