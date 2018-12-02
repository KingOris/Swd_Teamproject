
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
    private static final int PORT = 23555;
    public Client(String host){
        super("Client");
        this.host = host;        // set server to which this client connects
        input.setEditable(true);
        Handler handler = new Handler();
        input.addActionListener(handler);
        add(input, BorderLayout.NORTH);
        add(new JScrollPane(result), BorderLayout.CENTER);
    }
    public void runClient(){
        try{
            result.append("Attempting connection\n");
            client = new Socket(InetAddress.getByName(host), PORT);//set up output stream for objects
            result.append("Connected to: " + client.getInetAddress().getHostName());
            getStreams();
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
}

