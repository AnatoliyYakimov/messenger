package com.yakimov.server.model;


import com.yakimov.server.model.entities.Client;
import com.yakimov.server.model.entities.Message;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Интерфейс для модели. Инициализирует компоненты модели и предоставляет методы для взаиводействия с ними
 */
public class ModelInterface {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public ModelInterface() {
        execute(new ConnectionManager());
    }

    static void execute(Runnable thread) {
        executorService.execute(thread);
    }

    public Set<String> getUserList() {
        return OnlineManager.getUsernames();
    }

    public void disconnectUser(String login) {
        OnlineManager.disconnectUser(login);
    }

    public void addUsersListener(MapChangeListener<String, Client> listener) {
        OnlineManager.addUsersListener(listener);
    }

    public void addMessageListener(ChangeListener<Message> listener) {
        OnlineManager.addMessageListener(listener);
    }

    public void removeUsersListener(MapChangeListener<String, Client> listener) {
        OnlineManager.removeUsersListener(listener);
    }

    public String handleCommand(String command) {
        return CommandHandler.handleCommand(command);
    }


}
