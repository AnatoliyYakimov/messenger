package client;

import server.Config;
import server.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ArrayBlockingQueue;

public class Postman extends Thread {
    ClientGUI client;
    ObjectOutputStream oos;
    ArrayBlockingQueue<Message> msgQueue;

    Postman(ClientGUI client) throws IOException {
        this.client = client;
        oos = new ObjectOutputStream(client.getController().socket.getOutputStream());
        msgQueue = new ArrayBlockingQueue<>(10);
        msgQueue.add(new Message(client.getLogin(), Config.HELLO_MESSAGE));
        this.start();
    }

    @Override
    public void run() {
        while (true){
            if(msgQueue.size() > 0)
                try {
                    oos.writeObject(msgQueue.remove());
                }
                catch (IOException e){
                    System.err.println(e);
                }
        }
    }

    void sendMessage(Message msg){
        msgQueue.add(msg);
    }
}
