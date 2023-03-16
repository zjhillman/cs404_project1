import java.net.*;
import java.io.*;
import java.util.*;

public class VotingThread implements Runnable{
    Socket socket;
    BufferedReader inputFromClient;
    PrintWriter outputToClient;

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
        String option1 = "[1] I would like to cast a vote\n";
        String option2 = "[2] I do not wish to vote today\n";
        String greeting = welcome0 + welcome1 + welcome2 + welcome3 + option1 + option2 + "\n";
        List<String> options = new ArrayList<String>();
        options.add("1");
        options.add("2");
        options.add(".");

        // send greeting
        outputToClient.print(greeting + " \n");
        outputToClient.flush();

        // get response
        try {
            String response = inputFromClient.readLine();
            System.out.println("I heard " + response);

            while (!options.contains(response)) {
                outputToClient.print("Invalid response, your options are\n" + option1 + option2 + " \n");

                response = inputFromClient.readLine();
                System.out.println("I heard '" + response + "'");
            }

            if (response.equals("."))
                return 0;
            

            return Integer.parseInt(response);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int poll() {
        String header = "Today's topic is\n";
        String title = "    'Do you believe homework should be abolished?'\n";
        String option1 = "[1] Yes\n";
        String option2 = "[2] No\n";
        String option3 = "[3] Don't Care\n";
        String poll = header + title + option1 + option2 + option3 + "\n";
        List<String> options = new ArrayList<String>();
        options.add("1");
        options.add("2");
        options.add("3");
        options.add(".");

        // send poll
        outputToClient.print(poll + " \n");
        outputToClient.flush();

        // get response
        try {
            String response = inputFromClient.readLine();
            System.out.println("I heard " + response);

            while (!options.contains(response)) {
                outputToClient.print("Invalid response, your options are\n" + option1 + option2 + " \n");

                response = inputFromClient.readLine();
            }

            if (response.equals("."))
                return 0;
            

            return Integer.parseInt(response);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int results() {
        String header = "Please choose one of the following:\n";
        String option1 = "[1] See 'Yes' results\n";
        String option2 = "[2] See 'No' results\n";
        String option3 = "[3] See 'Don't Care' results\n";
        String option4 = "[.] Exit\n";
        String posty = header + option1 + option2 + option3 + option4 + "\n";
        List<String> options = new ArrayList<String>();
        options.add("1");
        options.add("2");
        options.add("3");
        options.add(".");

        // send instructions
        outputToClient.print(posty + " \n");
        outputToClient.flush();

        // get response
        try {
            String response = inputFromClient.readLine();
            System.out.println("I heard " + response);

            while (!options.contains(response)) {
                outputToClient.print("Invalid response, your options are\n" + option1 + option2 + " \n");

                response = inputFromClient.readLine();
            }

            if (response.equals("."))
                return 0;
            

            return Integer.parseInt(response);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void run() {
        try {
            int choice;
            choice = greet();

            switch (choice) {
                case 0:
                    System.out.println("client disconnected");
                    return;
                case 1:
                    choice = poll();
                    System.out.println("client participated in the poll");
                    break;
                case 2:
                    System.out.println("client does not wish to vote");
                    break; 
                default:
                    choice = -1;
                    System.out.println("client entered an invalid option");
                    return;
            }
            
            while (true) {
                choice = results();

                switch (choice) {
                    case 0:
                        System.out.println("client disconnected");
                        return;
                    case 1:
                        System.out.println("client requests to view yes votes");
                        break;
                    case 2:
                        System.out.println("client requests to view no votes");
                        break;
                    case 3:
                        System.out.println("client requests to view don't care votes");
                        break;
                    default:
                        System.out.println("client entered an invalid option");
                        return;
                }
            } // end while
        } catch (Exception e) {
            e.printStackTrace();
        } // end try/catch
    } // end run
} // end class