import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientRun implements ActionListener {

    public static void main(String[] args) {
        Client application;
        if (args.length == 0)
            application = new Client("127.0.0.1"); // connect to localhost
        else
            application = new Client(args[0]); // use args to connect
        Login_GUI log = new Login_GUI(true);
        log.setVisible(true);
        log.setSize(400, 400);
        log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        log.setResizable(false);


        log.userlog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                log.setVisible(false);
                System.out.println(log.getUserId());
                application.getInt(log.getUserId(),log.getSellerId());
            }
        });



        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.runClient(); // run client application
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
