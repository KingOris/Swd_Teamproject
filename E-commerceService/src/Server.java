import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;  // Server portion of a client/server stream-socket connection
import java.net.Socket;      // Server portion of a client/server stream-socket connection
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends JFrame {
    private static final int PORT = 23000;
    private JTextField enterField;
    private JTextArea displayArea;
    private ExecutorService executorService;
    private ServerSocket server;
    private MultiSocket[] serverSockets;
    private int count = 0;
    private int nClientActive = 1;

    public Server(){
        super("Server");

        serverSockets = new MultiSocket[100];
        executorService = Executors.newFixedThreadPool(100);

        enterField = new JTextField();  // create the enterField
        add(new JScrollPane(enterField));
        enterField.setEnabled(true);  // set the enderField is not able to edit

        displayArea = new JTextArea(); // create displayArea
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        setSize(500,600);
        setVisible(true);
    }
    public void runServer(){
        try
        {
            server = new ServerSocket(PORT, 100);   //this is used to create the sockets
            while (true){
            try {
                serverSockets[count] = new MultiSocket(count);
                serverSockets[count].waitForConnection();
                nClientActive ++;

                executorService.execute(serverSockets[count]);
            } catch (EOFException eofException) {
                displayArea.append("\nServer terminated connect");
            }finally {
                count++;
            }
            }
        }catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private class MultiSocket implements Runnable{
        private ObjectInputStream input;
        private ObjectOutputStream output;
        private Socket connection;
        private int count;
        private boolean start;
        public MultiSocket(int count){
            this.count = count;
            start = false;
        }

        @Override
        public void run() {
             try {
                 start = true;
                 try {
                     getStreams();
                     String message = "Connection To" + count + " successful";
                     sendData(message); // send connection successful message

                     do // process messages sent from client
                     {
                         try // read message and display it
                         {
                             message = (String) input.readObject(); // read new message
                             displayArea.append("\n" + count + message); // display message
                         } // end try
                         catch (ClassNotFoundException classNotFoundException) {
                             displayArea.append("\nUnknown object type received");
                         }

                     } while (!message.equals("CLIENT>>> TERMINATE"));
                 } catch (EOFException eofException) {
                     displayArea.append("\nServer" + count + " terminated connection");
                 } finally {
                     closeConection();
                     nClientActive--;
                     displayArea.append("" + nClientActive + " Alive");
                 }
             }catch (IOException ioException){
                 ioException.printStackTrace();
             }
        }

    public void getStreams()throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream()); //set up output stream for objects
        output.flush();  //flush output buffer to send header information
        input = new ObjectInputStream(connection.getInputStream()); // set up the input stream for objects
        displayArea.append("\nGot I/O streams\n");
    }
    public void sendData(String message){
        try{
            output.writeObject("SERVER>>> " + count + message);  // the line88 - line89 are the working as send the object to the client
            output.flush();
            displayArea.append("SERVER>>> " + count + message);
        }catch (IOException ioException) {
            displayArea.append("\nError writing object");
        }
    }

        private void waitForConnection() throws IOException {

            displayArea.append("Waiting for connection" + count + "\n");
            connection = server.accept(); // allow server to accept connection
            displayArea.append("Connection " + count + " received from: " +
                    connection.getInetAddress().getHostName());
        }

    public void closeConection(){
        displayArea.append("\nTerminating connect\n");
        start = false;
        try {
            connection.close(); // close the socket
            input.close(); // close the input stream
            output.close(); // close the socket
        } catch (IOException ioException) {
            ioException.printStackTrace();  //printStackTrace class is used to help the developer to understand where the actual problem occurred
        }
    }
}
    public static void main(String[] args) {
        Server application = new Server(); // create server
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.runServer(); // run server application
    }
}
