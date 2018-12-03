import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;  // Server portion of a client/server stream-socket connection
import java.net.Socket;      // Server portion of a client/server stream-socket connection
import java.util.ArrayList;
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
    private ArrayList<MultiSocket.Connect2Other> connect2Others;
    MultiSocket.Connect2Other connct2;
    private boolean turn;

    public Server(){
        super("Server");

        connect2Others = new ArrayList<>();
        turn = false;
        serverSockets = new MultiSocket[100];
        executorService = Executors.newFixedThreadPool(100);

        enterField = new JTextField();  // create the enterField
        add(new JScrollPane(enterField), BorderLayout.NORTH);
       // enterField.setEnabled(true);  // set the enderField is not able to edit

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

    private void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() // updates displayArea
                    {
                        displayArea.append(messageToDisplay); // append message
                    } // end method run
                } // end anonymous inner class
        ); // end call to SwingUtilities.invokeLater
    } // end method displayMessage

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
                     String message = "Connection To " + count + " successful";
                     sendData(message); // send connection successful message

                     try {
                         message = (String) input.readObject();
                         displayMessage(message);
                         //connect2Others.add(new Connect2Other(Integer.parseInt(message.substring(10,11)),count));
                         connect2Others.add(new Connect2Other(Integer.parseInt(message.substring(10,11)),count));
                         connct2 = new Connect2Other(Integer.parseInt(message.substring(10,11)),Integer.parseInt(message.substring(11,12)));
                         sendData(message.substring(9,10));
                     } catch (ClassNotFoundException e) {
                         e.printStackTrace();
                     }

                     do // process messages sent from client
                     {

                         try // read message and display it
                         {
                             message = (String) input.readObject(); // read new message
                            // for(int i = 0; i < connect2Others.size(); i++){
                                // if(connect2Others.get(i).getUserID() == connct2.getSocketID()){
                                     serverSockets[0].sendData(message);
                           //      }
                             //}
                             displayMessage("\n" + count + message); // display message
                         } // end try
                         catch (ClassNotFoundException classNotFoundException) {
                             displayMessage("\nUnknown object type received");
                         }

                     } while (!message.equals("CLIENT>>> TERMINATE"));
                 } catch (EOFException eofException) {
                     displayArea.append("\nServer" + count + " terminated connection");
                 } finally {
                     closeConection();
                     nClientActive--;
                     displayMessage("" + nClientActive + " Alive");
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
            output.writeObject(message);  // the line88 - line89 are the working as send the object to the client
            output.flush();
            displayMessage( message);
        }catch (IOException ioException) {
            displayMessage("\nError writing object");
        }
    }

    private class Connect2Other {
        private int userID;
        private int socketID;

        public Connect2Other(int a, int b) {
            userID = a;
            socketID = b;
        }

        public int getUserID(){
            return userID;
        }

        public int getSocketID(){
            return socketID;
        }
    }
        private void waitForConnection() throws IOException {

            displayMessage("Waiting for connection" + count + "\n");
            connection = server.accept(); // allow server to accept connection
            displayMessage("Connection " + count + " received from: " +
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

}
