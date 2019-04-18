package com.yakimov.server.model.entities;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class UserList {
    private ObservableMap<String, Client> onlineUsers = FXCollections.observableMap(new ConcurrentHashMap<>());

    public UserList() {
    }

    public void addUser(Client client) {
        onlineUsers.put(client.getLogin(), client);
    }

    public Set<String> getUsernames() {
        return onlineUsers.keySet();
    }

    public Collection<Client> getOnlineUsers() {
        return onlineUsers.values();
    }

    public boolean deleteUser(String login) {
        return onlineUsers.remove(login) != null;
    }

    public Client findClient(String login) {
        return onlineUsers.getOrDefault(login, null);
    }

    public void addListener(MapChangeListener<String, Client> listener) {
        onlineUsers.addListener(listener);
    }

    public void removeListener(MapChangeListener<String, Client> listener) {
        onlineUsers.removeListener(listener);
    }
}
