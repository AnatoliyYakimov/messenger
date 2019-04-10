package client;

import server.Config;
import server.Message;
import server.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

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
            sleep(new Random().nextInt(3000));
            System.out.println(login + " Started");
            socket = new Socket("localhost", 3000);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject(new Message(login, msg));
            int i = 1;
            while(true) {
                Message ms = ((Message) inputStream.readObject());
                if (ms instanceof Ping){
                    outputStream.writeObject(new Ping());
                }
                System.out.println(login + " accepted: " + ms.getMessage() + " from " + ms.getLogin());
                if (i < 5) {
                    sleep(100);
                    outputStream.writeObject((new Message(login, "Test message" + i++)));
                }
                else {
                    sleep(5000);
                    socket.close();
                    interrupt();
                }
            }
        }
        catch(IOException|ClassNotFoundException|InterruptedException e) {
            System.err.println("Connection to server lost, exiting");
        }
    }
}