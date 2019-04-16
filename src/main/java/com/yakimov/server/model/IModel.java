package com.yakimov.server.model;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Интерфейс для модели. Инициализирует компоненты модели и предоставляет методы для взаиводействия с ними
 */
public class IModel {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    private IModel() {
        execute(new ConnectionManager());
    }

    static public void main(String[] args) {
        new IModel();
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
}
