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

            while(!false) {
                // accept connection
                Socket socket = server.accept();

                // set input stream
                InputStream inStream = socket.getInputStream();
                BufferedReader input = new BufferedReader(new InputStreamReader(inStream));

                // set output stream
                OutputStream outStream = socket.getOutputStream();
                PrintWriter output = new PrintWriter(new OutputStreamWriter(outStream));

                // convert message to String
                String message = "";
                message = input.readLine();
                System.out.println(message);

                // echo message to client
                output.print("I heard "+message);

                // finish
                input.close();
                output.close();
                socket.close();
                if (message.equalsIgnoreCase("exit"))
                    break;
            }

            System.out.println("Goodbye!");
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end main
} // end class