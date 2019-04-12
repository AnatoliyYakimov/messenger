package server;

import server.entities.Client;
import server.entities.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Reciever implements Runnable {
    private ObjectInputStream inputStream;
    private MessageListeners listeners;

    Reciever(Client client, MessageListeners listeners) {
        inputStream = client.getInputStream();
        this.listeners = listeners;
    }


    @Override
    public void run() {
        try {
            while (true) {
                Message msg = (Message) inputStream.readObject();
                Server.getChatHistory().addMessage(msg);
                listeners.notifyListeners(msg);
            }
        } catch (IOException | ClassNotFoundException e) {
        }
    }
}
