package client;

import server.model.entities.Message;
import server.model.entities.Ping;
import utility.ClientUtil;
import utility.Config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread implements Runnable {
    private String login;
    private String[] args;

    ClientThread(String l) {
        login = l;
    }

    ClientThread(String l, String[] a) {
        this(l);
        args = a;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), Config.PORT);
            ObjectOutputStream outputStream = new ObjectOutputStream((socket.getOutputStream()));
            outputStream.writeObject(new Message(login, null, Message.Type.HEADER));
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject(new Message(login, "Hello world!", Message.Type.TEXT));
            int i = 0, k = 0;
            while (true) {
                Message msg = (Message) inputStream.readObject();
                if (msg.getType().equals(Message.Type.PING)) {
                    outputStream.writeObject(new Ping());
                    //System.out.println("Got ping " + ++k);
                } else {
                    if (this.login.equals("Оруссум_1"))
                        System.out.println(this.login + "Recieved: \" " + ClientUtil.formatMessage(msg));
                }
                if (i < args.length)
                    outputStream.writeObject(new Message(login, args[i++], Message.Type.TEXT));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Can`t connect to server");
            e.printStackTrace();
        }
    }
}
