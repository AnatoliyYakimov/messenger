package server;

import server.entities.Client;
import server.entities.Config;
import server.entities.Message;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Pinger implements Runnable, MessageListener {
    Client client;
    ObjectOutputStream outputStream;
    private boolean pingRecieved = true;
    private Timer timer;

    public Pinger(Client client) {
        this.client = client;
        outputStream = client.getOutputStream();
    }

    @Override
    public void run() {
        timer = new Timer(Config.DELAY, (event) -> {
            try {
                if (pingRecieved) {
                    pingRecieved = false;
                    outputStream.writeObject(new Message(null, null, Message.Type.PING));

                } else {
                    throw new IOException("Ping failed");
                }
            } catch (IOException e) {
                System.err.println("Unable to ping " + client.getLogin());
                timer.stop();
                Server.getUserList().deleteUser(client.getLogin());
                Server.mailer.putMessageIntoMailbox(new Message("Server", client.getLogin() + " disconnected", Message.Type.TEXT));
                client.close();
            }
        });
        timer.start();
    }

    @Override
    public void recievedMessage(Message msg) {
        if (msg.getType().equals(Message.Type.PING))
            this.pingRecieved = true;
    }
}
