import java.net.*;
import java.io.*;

public class VotingThread implements Runnable{
    Socket socket;
    BufferedReader clientInput;
    PrintWriter clientOutput;

    VotingThread(Socket socket, BufferedReader br, PrintWriter pw) {
        this.socket = socket;
        this.clientInput = br;
        this.clientOutput = pw;
    }

    public void run() {
        try {
            while (true) {
                // convert message to String
                String message = "";
                message = clientInput.readLine();
                System.out.println("I heard '"+message +"'\n");

                if (message.equalsIgnoreCase("exit")) {
                    // finish
                    clientInput.close();
                    clientOutput.close();
                    socket.close();
                    System.out.println("client disconnected.\n");
                    break;
                }

                // echo message to client
                clientOutput.print(message +"\n");
                clientOutput.flush();
            } // end while
        } catch (Exception e) {
            e.printStackTrace();
        } // end try/catch
    } // end run
} // end class