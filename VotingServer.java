import java.net.*;
import java.io.*;

public class VotingServer {
    private static int yesCount = 0,
                       noCount = 0,
                       dontCareCount = 0,
                       totalCount = 0;

    public static synchronized void castYesVote () {
        ++totalCount;
        ++yesCount;
    }

    public static synchronized void castNoVote () {
        ++totalCount;
        ++noCount;
    }

    public static synchronized void castDontCareVote () {
        ++totalCount;
        ++dontCareCount;
    }

    public static int getYesCount () {
        return yesCount;
    }

    public static int getNoCount () {
        return noCount;
    }

    public static int getDontCareCount () {
        return dontCareCount;
    }

    public static synchronized int getTallyCount () {
        return totalCount;
    }

    public static void main (String args[]) {
        int port = 12320;
        boolean acceptingRequests = true;

        if (args.length == 1)
            port = Integer.parseInt(args[0]);
        
        try {
            // establish server
            ServerSocket server = new ServerSocket(port);
            //server.setSoTimeout(1800000); // 30 minutes or 1,800,000 milliseconds
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

                // create thread to handle clients
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