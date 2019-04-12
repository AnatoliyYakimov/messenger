package server;

import server.entities.Client;
import server.entities.Message;

public class SystemListener implements MessageListener {
    Client client;

    SystemListener(Client client) {
        this.client = client;
    }

    @Override
    public void recievedMessage(Message msg) {
        if (msg.getType().equals(Message.Type.LOGIN)) {
            client.setLogin(msg.getLogin());
            Server.getUserList().addUser(msg.getLogin(), client);
        }
    }
}
