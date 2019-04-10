package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private static UserList userList = new UserList();
    private static ChatHistory chatHistory = new ChatHistory();

    static public void main(String[] args){
        try {
            ServerSocket socketListener = new ServerSocket(Config.PORT, 0, InetAddress.getByName("localhost"));
            while(true){
                Socket client = null;
                while (client == null){
                    client = socketListener.accept();
                }
                new ClientThread(client);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public Server() {

    }

    synchronized public static ChatHistory getChatHistory() {
        return chatHistory;
    }

    synchronized public static UserList getUserList() {
        return userList;
    }
}
