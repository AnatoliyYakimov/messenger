package client;

import server.entities.Config;
import server.entities.Message;
import utility.ClientUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread implements Runnable {
    String login;

    ClientThread(String l) {
        login = l;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), Config.PORT);
            ObjectOutputStream outputStream = new ObjectOutputStream((socket.getOutputStream()));
            outputStream.writeObject(new Message(login, null, Message.Type.LOGIN));
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject(new Message(login, "Hello world!", Message.Type.TEXT));
            while (true) {
                Message msg = (Message) inputStream.readObject();
                if (msg.getType().equals(Message.Type.PING)) {
                    outputStream.writeObject(new Message(login, null, Message.Type.PING));
                } else {
                    if (this.login.equals("Оруссум_1"))
                        System.out.println(this.login + "Recieved: \" " + ClientUtil.formatMessage(msg));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Can`t connect to server");
            e.printStackTrace();
        }
    }
}
