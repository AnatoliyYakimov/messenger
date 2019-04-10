package client;

import server.Message;
import server.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;


public class MessageListener extends Thread{
    ClientGUI client;
    ObjectInputStream ois;

    MessageListener(ClientGUI client) throws IOException {
        this.client = client;
        ois = new ObjectInputStream(client.getController().socket.getInputStream());
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message msg = (Message)ois.readObject();
                if (msg instanceof Ping){
                    client.getPostman().sendMessage(msg);
                }
                else{
                    client.getController().renderMessage(msg);
                }
            } catch (IOException e) {
                System.err.println(e);
            } catch (ClassNotFoundException e) {
                System.err.println(e);
            }
        }
    }
}
