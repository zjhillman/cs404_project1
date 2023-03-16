import java.net.*;
import java.io.*;

public class VotingServer {
    public static void main (String args[]) {
        int port = 12320;
        boolean acceptingRequests = true;

        if (args.length == 1)
            port = Integer.parseInt(args[0]);
        
        try {
            int yesCount, noCount, dontCareCount;

            //establish server
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(1800000); // 30 minutes or 1,800,000 milliseconds
            System.out.println("Established server at "+InetAddress.getLocalHost().toString());

            while (acceptingRequests) {
                // accept connection
                Socket socket = server.accept();
                System.out.println("Connection established at "+socket.getInetAddress().toString()+"\n");

                // set input stream
                InputStream inStream = socket.getInputStream();
                BufferedReader clientIn = new BufferedReader(new InputStreamReader(inStream));

                // set output stream
                OutputStream outStream = socket.getOutputStream();
                PrintWriter clientOut = new PrintWriter(new OutputStreamWriter(outStream));

                Thread thread = new Thread(new VotingThread(socket, clientIn, clientOut));
                thread.start();

                
            } // end while

            System.out.println("Goodbye!");
            server.close();
        } catch (SocketException e) {
            System.out.println("30 minutes have elapsed, voting has ended."); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end main
} // end class