package com.yakimov.server.model;

import com.yakimov.server.model.entities.ChatHistory;
import com.yakimov.server.model.entities.Client;
import com.yakimov.server.model.entities.Message;
import com.yakimov.server.model.entities.UserList;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;

import java.util.Collection;
import java.util.List;
import java.util.Set;


/*
    Набор статических функций для доступа и взаимодействия с общими ресурсами:
    - Список пользователей, находящихся онлайн
    - История сообщений
 */
class OnlineManager {
    private static UserList userList = new UserList();
    private static ChatHistory chatHistory = new ChatHistory();
    private static SimpleObjectProperty<Message> lastMsg = new SimpleObjectProperty<>();

    synchronized static void addOnlineUser(Client client) {
        synchronized (OnlineManager.class) {
            userList.addUser(client);
        }
    }

    synchronized static boolean disconnectUser(String login) {
        return (disconnectUser(userList.findClient(login)));
    }

    synchronized static boolean disconnectUser(Client client) {
        if (client != null && !client.getSocket().isClosed()) {
            if (userList.deleteUser(client.getLogin())) {
                client.close();
                PostingService.broadcast(new Message(PostingService.bot, client.getLogin() + " disconnected", Message.Type.TEXT));
                System.out.println(client.getLogin() + " disconnected.");
                return true;
            }

        }
        return false;
    }

    synchronized static Collection<Client> getClients() {
        return userList.getOnlineUsers();
    }

    synchronized static Set<String> getUsernames() {
        return userList.getUsernames();
    }

    synchronized static List<Message> getChatHistory() {
        return chatHistory.getHistory();
    }

    synchronized static void addMessageInHistory(Message msg) {
        chatHistory.addMessage(msg);
        lastMsg.set(msg);
    }

    public static void addUsersListener(MapChangeListener<String, Client> listener) {
        userList.addListener(listener);
    }

    public static void removeUsersListener(MapChangeListener<String, Client> listener) {
        userList.removeListener(listener);
    }

    static void addMessageListener(ChangeListener<Message> listener) {
        lastMsg.addListener(listener);
    }

}
