package server;

import server.entities.Message;

import java.io.IOException;
import java.util.ArrayList;

public class PostingService implements Runnable {
    private ArrayList<Message> mailbox = new ArrayList<>();

    synchronized void putMessageIntoMailbox(Message msg) {
        synchronized (mailbox) {
            mailbox.add(msg);
        }
    }

    @Override
    public void run() {
        while (true) {
            synchronized (mailbox) {
                if (mailbox.size() > 0) {
                    var clients = Server.getUserList().getClientsList();
                    //mailbox.sort((msg1, msg2) ->  Long.compare(msg1.getTime().getTime(),msg2.getTime().getTime()));
                    mailbox.forEach((msg) -> {
                        clients.forEach((client) -> {
                            try {
                                client.getOutputStream().writeObject(msg);
                            } catch (IOException e) {
                                //System.err.println("Error while sending messages " + e);
                            }
                        });
                    });
                    mailbox.clear();
                }
            }
        }
    }
}
