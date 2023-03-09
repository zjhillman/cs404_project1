import java.net.*;
import java.io.*;

public class VotingServer {
    public static void main (String args[]) {
        int port = 12320;

        if (args.length == 1)
            port = Integer.parseInt(args[0]);
        
        try {
            int yesCount, noCount, dontCareCount;

            //establish server
            ServerSocket server = new ServerSocket(port);
            System.out.println("Established server at "+InetAddress.getLocalHost().toString());

            // accept connection
            Socket socket = server.accept();
            System.out.println("Connection established.");

            while(true) {
                // set input stream
                InputStream inStream = socket.getInputStream();
                BufferedReader input = new BufferedReader(new InputStreamReader(inStream));

                // set output stream
                OutputStream outStream = socket.getOutputStream();
                PrintWriter output = new PrintWriter(new OutputStreamWriter(outStream));

                // convert message to String
                String message = "";
                message = input.readLine();
                System.out.println("I heard '"+message +"'\n");

                if (message.equalsIgnoreCase("exit")) {
                    // finish
                    input.close();
                    output.close();
                    socket.close();
                    break;
                }

                // echo message to client
                output.print(message +"\n");
                output.flush();
            } // end while

            System.out.println("Goodbye!");
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end main
} // end class