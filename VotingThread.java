import java.net.*;
import java.io.*;

public class VotingThread implements Runnable{
    Socket socket;
    BufferedReader inputFromClient;
    PrintWriter outputToClient;

    // string menu
    String welcome0 = "Welcome [user],\n";
    String welcome1 = "Today's topic is\n";
    String welcome2 = "    'Do you believe homework should be abolished?'\n";
    String welcome3 = "Would you like to cast a vote?\n";
    String welcome4 = "[1] I would like to cast a vote\n";
    String welcome5 = "[2] I do not wish to vote today\n";
    String greeting = welcome0 + welcome1 + welcome2 + welcome3 + welcome4 + welcome5 + "\n";

    String option0 = "[1] Yes\n";
    String option1 = "[2] No\n";
    String option2 = "[3] Don't Care\n";
    String voteOptions = welcome1 + welcome2 + option0 + option1 + option2 + "\n";

    String post0 = "Thanks for participating in today's poll!";
    String post1 = "Please choose one of the following:";
    String post2 = "[1] See 'Yes' results\n";
    String post3 = "[2] See 'No' results\n";
    String post4 = "[3] See 'Don't Care' results\n";
    String post5 = "[.] Exit\n";
    String posty = post0 + post1 + post2 + post3 + post4 + post5 + "\n";



    VotingThread(Socket socket, BufferedReader br, PrintWriter pw) {
        this.socket = socket;
        this.inputFromClient = br;
        this.outputToClient = pw;
    }

    public void run() {
        try {
            // send greeting
            outputToClient.print(greeting + "\n");
            outputToClient.flush();

            // wait for response
            String msg = inputFromClient.readLine();
            System.out.println("[client][greeting_response] " + msg);

            switch (Integer.parseInt(msg)) {
                case 1:
                    System.out.println("yes vote");
                    break;
                case 2:
                    System.out.println("no vote");
                    break; 
                default:
                    System.out.println("Invalid option");
                    return;
            }
            

            

            while (true) {

                // convert message to String
                String message = "";
                message = inputFromClient.readLine();

                // exit if client says it exits
                if (message.equalsIgnoreCase(".")) {
                    // finish
                    inputFromClient.close();
                    outputToClient.close();
                    socket.close();
                    System.out.println("client disconnected.\n");
                    break;
                }

                // print message
                System.out.println("I heard '"+message +"'\n");

                // echo message to client
                outputToClient.print(message +"\n");
                outputToClient.flush();
            } // end while
        } catch (Exception e) {
            e.printStackTrace();
        } // end try/catch
    } // end run
} // end class