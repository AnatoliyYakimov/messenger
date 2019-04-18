package com.yakimov.server.model.entities;

import com.yakimov.server.utility.Config;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ChatHistory {
    private ObservableList<Message> history = FXCollections.observableArrayList(new ConcurrentLinkedDeque<>());

    public synchronized void addMessage(Message msg) {
        if (this.history.size() >= Config.HISTORY_LENGTH) {
            this.history.remove(0);
        }
        this.history.add(msg);
    }

    public List<Message> getHistory() {
        return history;
    }

    public void addMessageListener(ListChangeListener<Message> listener) {
        history.addListener(listener);
    }
}
