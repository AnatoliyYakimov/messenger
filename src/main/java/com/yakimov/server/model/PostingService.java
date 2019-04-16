package com.yakimov.server.model;

import com.yakimov.server.model.entities.Client;
import com.yakimov.server.model.entities.Message;

import java.io.IOException;

/*
    Набор статических функций для отправки соообщений
 */
class PostingService {
    final static String bot = "Chat-bot";

    /*
        Отправляет сообщение всем пользователям, находящимся онлайн и записывает его в историю сообщений
     */
    static void broadcast(Message msg) {
        OnlineManager.addMessageInHistory(msg);
        var clients = OnlineManager.getClients();
        for (Client c : clients) {
            try {
                c.getOutputStream().writeObject(msg);
            } catch (IOException e) {
                System.err.println("Cant send message to user: " + c.getLogin());
                OnlineManager.disconnectUser(c);
            }
        }
    }


    static void sendChatHistory(Client client) throws IOException {
        for (Message msg : OnlineManager.getChatHistory()) {
            client.getOutputStream().writeObject(msg);
        }
    }
}


