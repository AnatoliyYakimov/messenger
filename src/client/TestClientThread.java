package client;

import java.io.IOException;

public class TestClientThread extends Thread {
    String login;
    TestClientThread(String login){
        this.login = login;
        this.start();
    }

    @Override
    public void run() {
        try {
            ClientGUI client = new ClientGUI(login);
            client.setVisible(true);
        }catch (IOException e){
            System.err.println(e);
        }
    }
}
