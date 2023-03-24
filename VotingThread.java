import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.Format;

public class VotingThread implements Runnable{
    private final boolean DEBUG = false;
    private final String EXIT_COMMAND = ".";
    Socket socket;
    BufferedReader inputFromClient;
    PrintWriter outputToClient;
    boolean running = false;

    VotingThread(Socket socket, BufferedReader br, PrintWriter pw) {
        this.socket = socket;
        this.inputFromClient = br;
        this.outputToClient = pw;
    }

    public void greet() {
        String welcome0 = "Welcome [user],\n";
        String welcome1 = "Today's topic is\n";
        String welcome2 = "    'Do you believe homework should be abolished?'\n";
        String welcome3 = "Would you like to cast a vote?\n";
        String option1 = "[1] I would like to cast a vote\n";
        String option2 = "[2] I do not wish to vote today\n";
        String option3 = "["+EXIT_COMMAND+"] Exit\n";
        String greeting = welcome0 + welcome1 + welcome2 + welcome3 + option1 + option2 + option3;
        List<String> options = new ArrayList<String>();
        options.add("1");
        options.add("2");
        options.add(EXIT_COMMAND);

        // send greeting
        outputToClient.print(greeting + "\n");
        outputToClient.flush();

        // get response
        try {
            String response = inputFromClient.readLine();
            if (DEBUG) System.out.println("I heard " + response);

            // If an incorrect option was select, loop until a valid response was received
            while (!options.contains(response)) {
                String error = "Invalid response, your options are\n" + option1 + option2;
                outputToClient.print(error + "\n");
                outputToClient.flush();

                response = inputFromClient.readLine();
            }

            // if exit command, exit
            if (response.equals(EXIT_COMMAND)) {
                shutdown();
                return;
            }

            // execute based on command
            switch (response) {
                case "1":
                    poll();
                    break;
                case "2":
                    System.out.println("client does not wish to vote\n");
                    break; 
                default:
                    System.out.println("error getting response from " + welcome3);
                    return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void poll() {
        String header = "Today's topic is\n";
        String title = "    'Do you believe homework should be abolished?'\n";
        String option1 = "[1] Yes\n";
        String option2 = "[2] No\n";
        String option3 = "[3] Don't Care\n";
        String option4 = "["+EXIT_COMMAND+"] Exit\n";
        String poll = header + title + option1 + option2 + option3 + option4;
        List<String> options = new ArrayList<String>();
        options.add("1");
        options.add("2");
        options.add("3");
        options.add(EXIT_COMMAND);

        // send poll
        outputToClient.print(poll + "\n");
        outputToClient.flush();

        try {
            // get response
            String response = inputFromClient.readLine();

            // if invalid command, loop until a valid response is received
            while (!options.contains(response)) {
                outputToClient.print("Invalid response, your options are\n" + option1 + option2 + option3 +option4 + "\n");
                outputToClient.flush();

                response = inputFromClient.readLine();
            }

            // if exit command
            if (response.equals(EXIT_COMMAND)) {
                shutdown();
                return;
            }
            
            //execute based on command
            switch (response) {
                case "1":
                    VotingServer.castYesVote();
                    System.out.println("client has cast a ballot\n");
                    break;
                case "2":
                    VotingServer.castNoVote();
                    System.out.println("client has cast a ballot\n");
                    break;
                case "3":
                    VotingServer.castDontCareVote();
                    System.out.println("client has cast a ballot\n");
                    break;
                default:
                System.out.println("error getting response from "+header+title);
                    break;
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void results() {
        Format format = new SimpleDateFormat("HH:mm:ss");
        String time = format.format(new Date());

        String header1 = "Total votes as of "+time+" is "+String.valueOf(VotingServer.getTallyCount())+"\n";
        String header2 = "Please choose one of the following:\n";
        String option1 = "[1] See 'Yes' results\n";
        String option2 = "[2] See 'No' results\n";
        String option3 = "[3] See 'Don't Care' results\n";
        String option4 = "["+EXIT_COMMAND+"] Exit\n";
        String posty = header1 + header2 + option1 + option2 + option3 + option4;
        List<String> options = new ArrayList<String>();
        options.add("1");
        options.add("2");
        options.add("3");
        options.add(EXIT_COMMAND);

        // send instructions
        outputToClient.print(posty + "\n");
        outputToClient.flush();

        try {
             // get response
            String response = inputFromClient.readLine();

            while (!options.contains(response)) {
                outputToClient.print("Invalid response, your options are\n" + option1 + option2 + option3 + option4 + "\n");
                outputToClient.flush();

                response = inputFromClient.readLine();
            }

            if (response.equals(EXIT_COMMAND)) {
                shutdown();
                return;
            }
                
            // send result
            switch (response) {
                case "1":
                    sendVote("Yes", VotingServer.getYesCount());
                    break;
                case "2":
                    sendVote("No", VotingServer.getNoCount());
                    break;
                case "3":
                    sendVote("Don't Care", VotingServer.getDontCareCount());
                    break;
                default:
                    System.out.println("error getting response at "+header1);
                    return;
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void sendVote(String voteType, int result) {
        String message = voteType;
        message += " votes: " + String.valueOf(result) + "\n";
        message += "[Enter] Return to menu\n";

        // send message
        outputToClient.print(message + "\n");
        outputToClient.flush();

        // wait for response, throw away response
        try {
            inputFromClient.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            System.out.println("client has disconnected");
            inputFromClient.close();
            outputToClient.close();
            socket.close();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public void run() {
        try {
            running = true;
            greet();

            while (running) {
                results();
            } // end while
        } catch (Exception e) {
            e.printStackTrace();
        } // end try/catch
    } // end run
} // end class