package com.yakimov.server.model;

import com.yakimov.server.model.entities.ChatHistory;
import com.yakimov.server.model.entities.Client;
import com.yakimov.server.model.entities.Message;
import com.yakimov.server.model.entities.UserList;

import java.util.Collection;
import java.util.Queue;
import java.util.Set;


/*
    Набор статических функций для доступа и взаимодействия с общими ресурсами:
    - Список пользователей, находящихся онлайн
    - История сообщений
 */
class OnlineManager {
    private static UserList userList = new UserList();
    private static ChatHistory chatHistory = new ChatHistory();

    synchronized static void addOnlineUser(Client client) {
        synchronized (OnlineManager.class) {
            userList.addUser(client);
        }
    }

    synchronized static void disconnectUser(String login) {
        disconnectUser(userList.findClient(login));
    }

    synchronized static void disconnectUser(Client client) {
        if (client != null && !client.getSocket().isClosed()) {
            userList.deleteUser(client.getLogin());
            client.close();
            PostingService.broadcast(new Message(PostingService.bot, client.getLogin() + " disconnected", Message.Type.TEXT));
            System.out.println(client.getLogin() + " disconnected.");
        }
    }

    synchronized static Collection<Client> getClients() {
        return userList.getOnlineUsers();
    }

    synchronized static Set<String> getUsernames() {
        return userList.getUsernames();
    }

    synchronized static Queue<Message> getChatHistory() {
        return chatHistory.getHistory();
    }

    synchronized static void addMessageInHistory(Message msg) {
        chatHistory.addMessage(msg);
    }
}
