package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserList {
    private Map<String, Client> onlineUsers = new HashMap<>();

    public UserList(){ }

    public boolean addUser(String login, Socket socket, ObjectOutputStream oos, ObjectInputStream ois){
        if (!this.onlineUsers.containsKey(login)) {
            onlineUsers.put(login, new Client(socket, ois, oos));
            return true;
        }
        return false;
    }
    public void deleteUser(String login){
        onlineUsers.remove(login);
    }

    public String[] getUsers(){
        return onlineUsers.keySet().toArray(new String[0]);
    }

    public ArrayList<Client> getClientsList(){
        ArrayList<Client> clients = new ArrayList<>(onlineUsers.size());

        for(Map.Entry<String, Client> entry : onlineUsers.entrySet()){
            clients.add(entry.getValue());
        }
        return clients;
    }
}
