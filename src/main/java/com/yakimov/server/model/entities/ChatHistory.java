package com.yakimov.server.model.entities;

import com.yakimov.server.utility.Config;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ChatHistory implements Serializable {
    private ConcurrentLinkedDeque<Message> history = new ConcurrentLinkedDeque<>();

    public synchronized void addMessage(Message msg) {
        if (this.history.size() >= Config.HISTORY_LENGTH) {
            this.history.remove();
        }
        this.history.add(msg);
    }

    public Queue<Message> getHistory() {
        return history;
    }
}
