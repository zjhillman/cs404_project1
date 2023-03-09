import java.net.*;

public class VotingServer {
    public static void main(String args[]) {
        int port = 12320;

        if (args.length == 1)
            port = Integer.parseInt(args[0]);
        
        try {
            int yesCount, noCount, dontCareCount;

            //establish server
            ServerSocket server = new ServerSocket(port);
            System.out.println("Established server at "+InetAddress.getLocalHost().toString());

            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end main
} // end class