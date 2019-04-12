package server;

import server.entities.ChatHistory;
import server.entities.Config;
import server.entities.UserList;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    static PostingService mailer = new PostingService();
    private static UserList userList = new UserList();
    private static ChatHistory chatHistory = new ChatHistory();

    static public void main(String[] args){
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            ServerSocket socketListener = new ServerSocket(Config.PORT, 0, InetAddress.getByName("localhost"));
            executorService.execute(mailer);
            while(true){
                Socket client = null;
                while (client == null){
                    client = socketListener.accept();
                }
                executorService.execute(new ClientHandler(client));
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
