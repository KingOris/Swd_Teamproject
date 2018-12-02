
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;  //client portion of a stream-socket conncetion between client and server
import java.net.Socket;

public class Client extends JFrame {
    private JTextField input = new JTextField();
    private JTextArea result = new JTextArea();
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private String message = "";
    private String host;
    private Socket client;

    private static final int PORT = 23000;
    public Client(String host){
        super("Client");
        this.host = host;        // set server to which this client connects
        input.setEditable(true);
        Handler handler = new Handler();
        input.addActionListener(handler);
        add(input, BorderLayout.NORTH);
        add(new JScrollPane(result), BorderLayout.CENTER);
        setVisible(true);
    }
    public void runClient(){
        try{
            result.append("Attempting connection\n");
            client = new Socket(InetAddress.getByName(host), PORT);//set up output stream for objects
            result.append("Connected to: " + client.getInetAddress().getHostName());
            getStreams();
            MainPage_GUI mainPage_gui = new MainPage_GUI();
            mainPage_gui.showMain(-1);


            do
            {
                try
                {
                    message = (String) inputStream.readObject(); // read new message
                    result.append("\n" + message); // display message
                } catch (ClassNotFoundException classNotFoundException) {
                    result.append("\nUnknown object type received");
                }

            } while (!message.equals("SERVER>>> TERMINATE"));
        }catch (EOFException Exception){
            result.append("\nClient terminated connection");
        }catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {
            result.append("\nClosing connection");
            input.setEnabled(false);
            result.setEnabled(false);
            try {
                client.close(); //close the output stream
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }
    public class Handler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try // try to send object to server
            {
                outputStream.writeObject(e.getActionCommand());
                outputStream.flush(); // flush data to outputStream
                result.append("\nCLIENT>>> " + e.getActionCommand());
            } catch (IOException ioException) {
                result.append("\nError writing object");
            }
            input.setText("");
        }
    }

    private void getStreams() throws IOException {
        outputStream = new ObjectOutputStream(client.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(client.getInputStream());

        result.append("\nGot I/O streams\n");
    }

    public static void main(String[] args) {
        Client application; // declare client application

        // if no command line args
        if (args.length == 0)
            application = new Client("127.0.0.1"); // connect to localhost
        else
            application = new Client(args[0]); // use args to connect

        application.setSize(600,600);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.runClient(); // run client application
    }
}

