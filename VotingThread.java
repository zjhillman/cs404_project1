import java.net.*;
import java.io.*;
import java.util.*;

public class VotingThread implements Runnable{
    Socket socket;
    BufferedReader inputFromClient;
    PrintWriter outputToClient;
   

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

    public int greet() {
        String welcome0 = "Welcome [user],\n";
        String welcome1 = "Today's topic is\n";
        String welcome2 = "    'Do you believe homework should be abolished?'\n";
        String welcome3 = "Would you like to cast a vote?\n";
        String welcome4 = "[1] I would like to cast a vote\n";
        String welcome5 = "[2] I do not wish to vote today\n";
        String greeting = welcome0 + welcome1 + welcome2 + welcome3 + welcome4 + welcome5 + "\n";
        List<String> options = new ArrayList<String>();
        options.add("1");
        options.add("2");
        options.add(".");

        // send greeting
        outputToClient.print(greeting + "\n");
        outputToClient.flush();

        // get response
        try {
            String response = inputFromClient.readLine();

            while (!options.contains(response)) {
                outputToClient.print("Invalid response, your options are\n" + welcome4 + welcome5 + "\n");

                response = inputFromClient.readLine();
            }

            return Integer.parseInt(response);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void printBallot() {
        String intro = "Today's topic is\n";
        String topic = "    'Do you believe homework should be abolished?'\n";
        String vote1 = "[1] Yes\n";
        String vote2 = "[2] No\n";
        String vote3 = "[3] Don't Care\n";
        String poll = intro + topic + vote1 + vote2 + vote3 + "\n";

        // send poll
        outputToClient.print(poll + "\n");
        outputToClient.flush();
    }



    public void run() {
        try {
            int choice;
            choice = greet();
            
            System.out.println("[client][greeting_response] " + response);

            switch (Integer.parseInt(response)) {
                case 1:
                    printBallot();
                    break;
                case 2:
                    System.out.println("client does not wish to vote");
                    break; 
                default:
                    System.out.println("client entered an invalid option");
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