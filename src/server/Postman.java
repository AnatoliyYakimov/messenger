package server;

import server.entities.Message;

public class Postman implements MessageListener {

    @Override
    public void recievedMessage(Message msg) {
        if (msg.getType().equals(Message.Type.TEXT))
            Server.mailer.putMessageIntoMailbox(msg);
    }
}
