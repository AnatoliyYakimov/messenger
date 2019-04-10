package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import javax.swing.Timer;

class ClientThread extends Thread {
    private Socket socket;
    private Message msg;
    private String login;
    private int outPacks = 0, inPacks = 0;
    private boolean flag = true;
    private Timer timer;
    private ObjectInputStream iStream;
    private ObjectOutputStream oStream;

    ClientThread(Socket socket){
        this.socket = socket;
        this.start();
    }

    private boolean pingUser(){
        inPacks = 0;
        outPacks = 0;
        flag = true;
        this.timer = new Timer(Config.DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (inPacks == outPacks) {
                        oStream.writeObject(new Ping());
                        outPacks++;
                    }
                    else throw new SocketException();
                }
                 catch (SocketException e1){
                     System.out.println(login + " disconnected");
                     broadcast(Server.getUserList(), new Message("Server-Bot", login + " disconnected."));
                     Server.getUserList().deleteUser(login);
                     //Передаём сообщение чат-ботом
                     flag = false;
                     timer.stop();
                 }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
        timer.start();
        while (flag) {
            try {
                this.msg = (Message) iStream.readObject();
                handleMsg();
            }
            catch (ClassNotFoundException|IOException e){
                e.printStackTrace();
            }

        }
        return false;
    }

    void handleMsg(){
        if (this.msg.getMessage() != null) {
            if (this.msg instanceof Ping) {
                inPacks++;
            } else if (this.msg.getMessage().equals(Config.HELLO_MESSAGE)) {
                try {
                    for (Message msg : Server.getChatHistory().getHistory()) {
                        oStream.writeObject(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.broadcast(Server.getUserList(), new Message("Server-Bot", "The user " + login + " has been connected"));
            } else {
                System.out.println("[" + this.login + "]: " + this.msg.getMessage());
                Server.getChatHistory().addMessage(this.msg);
            }
            Server.getUserList().addUser(login, socket, oStream, iStream);
            this.msg.setUsers(Server.getUserList().getUsers());
        }
    }


    public void run(){
        try{
            oStream = new ObjectOutputStream( this.socket.getOutputStream() );
            iStream = new ObjectInputStream( this.socket.getInputStream());
            this.msg = (Message) iStream.readObject();
            this.login = this.msg.getLogin();

            handleMsg();

            pingUser();

            super.interrupt();
        }
        catch(ClassNotFoundException|IOException e){
            e.printStackTrace();
        }
    }

    private void broadcast(UserList users, Message msg){
        users.getClientsList().forEach((client) -> {
            try {
                client.getOutputStream().writeObject(msg);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });

    }
}
