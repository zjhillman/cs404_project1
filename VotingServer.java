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
            server.setSoTimeout(60000); // 60 seconds
            System.out.println("Established server at "+InetAddress.getLocalHost().toString());

            while (true) {
                // accept connection
                Socket socket = server.accept();
                System.out.println("Connection established.");

                // set input stream
                InputStream inStream = socket.getInputStream();
                BufferedReader clientIn = new BufferedReader(new InputStreamReader(inStream));

                // set output stream
                OutputStream outStream = socket.getOutputStream();
                PrintWriter clientOut = new PrintWriter(new OutputStreamWriter(outStream));

                Thread thread = new Thread(new VotingThread(socket, clientIn, clientOut));
                thread.start();

                
            } // end while

            //System.out.println("Goodbye!");
            //server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end main
} // end class