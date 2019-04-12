package server.entities;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;

public class ChatHistory implements Serializable {
    private ArrayBlockingQueue<Message> history = new ArrayBlockingQueue<>(Config.HISTORY_LENGTH);

    public synchronized void addMessage(Message msg) {
        if (this.history.size() >= Config.HISTORY_LENGTH){
            this.history.remove();
        }
        this.history.add(msg);
    }

    public ArrayBlockingQueue<Message> getHistory() {
        return history;
    }
}
