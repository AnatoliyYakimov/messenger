package server.model.entities;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class UserList {
    //Для производительности поиска можно заменить на ConcurrentHashMap
    private ConcurrentHashMap<String, Client> onlineUsers = new ConcurrentHashMap<>();

    public UserList() {
    }

    public void addUser(Client client) {
        onlineUsers.putIfAbsent(client.getLogin(), client);
    }

    public Set<String> getUsernames() {
        return onlineUsers.keySet();
    }

    public Collection<Client> getOnlineUsers() {
        return onlineUsers.values();
    }

    public void deleteUser(String login) {
        onlineUsers.remove(login);
    }

    public Client findClient(String login) {
        return onlineUsers.getOrDefault(login, null);
    }
}
