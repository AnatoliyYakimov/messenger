package client;

import server.Config;
import server.Message;
import server.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TestClient extends Thread {
    String login,
            msg;
    Socket socket;

    public TestClient(String login, String msg){
        this.login = login;
        this.msg = Config.HELLO_MESSAGE;
    }

    public void run(){
        try{
            sleep(1000);
            System.out.println(login + " Started");
            socket = new Socket("localhost", 3000);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject(new Message(login, msg));
            int i = 5;
            while(i-- > 0) {
                Message ms = ((Message) inputStream.readObject());
                System.out.println(login + " accepted: " + ms.getMessage() + " from " + ms.getLogin());
                if (ms instanceof Ping){
                    outputStream.writeObject(new Ping());
                }
            }
            interrupt();
        }
        catch(IOException|ClassNotFoundException|InterruptedException e) {
            e.printStackTrace();
        }
    }
}