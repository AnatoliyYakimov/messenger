package server;

import server.entities.Message;

import java.util.ArrayList;
import java.util.List;


interface MessageListener {
    void recievedMessage(Message msg);
}

class MessageListeners {
    private List<MessageListener> listeners = new ArrayList<>();

    void addListener(MessageListener ml) {
        listeners.add(ml);
    }

    void removeListener(MessageListener ml) {
        listeners.remove(ml);
    }

    void notifyListeners(Message msg) {
        listeners.forEach((l) -> l.recievedMessage(msg));
    }
}
