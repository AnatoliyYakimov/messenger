package server.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserList {
    private Map<String, Client> onlineUsers = new HashMap<>();

    public UserList(){ }

    public synchronized void addUser(String login, Client client) {
        if (!this.onlineUsers.containsKey(login)) {
            onlineUsers.put(login, client);
        }
    }

    public synchronized void deleteUser(String login) {
        onlineUsers.remove(login);
    }

    public synchronized String[] getUsers() {
        return onlineUsers.keySet().toArray(new String[0]);
    }

    public synchronized ArrayList<Client> getClientsList() {
        ArrayList<Client> clients = new ArrayList<>(onlineUsers.size());

        for(Map.Entry<String, Client> entry : onlineUsers.entrySet()){
            clients.add(entry.getValue());
        }
        return clients;
    }
}
