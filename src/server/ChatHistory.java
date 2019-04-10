package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatHistory implements Serializable {
    private List<Message> history = new ArrayList<>(Config.HISTORY_LENGTH);

    public void addMessage(Message msg){
        if (this.history.size() >= Config.HISTORY_LENGTH){
            this.history.remove(0);
        }
        this.history.add(msg);
    }

    public List<Message> getHistory() {
        return history;
    }
}
