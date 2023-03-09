import java.net.*;

public class VotingServer {
    public static void main(String args[]) {
        if (args.length != 1) {
            System.out.println("This program requires 1 command line argument(s)");
        } else {
            try {
                int port = Integer.parseInt(args[0]);
                int yesCount, noCount, dontCareCount;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // end else
    } // end main
} // end class