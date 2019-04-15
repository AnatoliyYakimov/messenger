package server.model;

import server.model.entities.Client;
import server.model.entities.Message;
import utility.Config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*
    Производит общение с одним конкретным клиентом
 */
class ClientHandler implements Runnable {
    private Client client;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean pingRecieved = true;

    ClientHandler(Socket socket) {
        try {
            client = new Client(socket);
            outputStream = client.getOutputStream();
            inputStream = client.getInputStream();
        } catch (IOException e) {
            System.err.println("Can`t get input stream, closing connection");
            try {
                socket.close();
            } catch (IOException e1) {
                System.err.println(e1.toString());
            }
        }
    }

    private void startPingTimer() {
        IModel.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (pingRecieved) {
                        pingRecieved = false;
                        outputStream.writeObject(new Message(null, null, Message.Type.PING));
                        Thread.sleep(Config.DELAY);
                    }
                    throw new IOException("Ping failed: " + client.getLogin());
                } catch (IOException | InterruptedException e) {
                    System.err.println(e.getMessage());
                    OnlineManager.disconnectUser(client);
                }
            }
        });
    }

    private void getHeader() throws IOException, ClassNotFoundException {
        Message header = (Message) inputStream.readObject();
        if (header.getType().equals(Message.Type.HEADER)) {
            client.setLogin(header.getLogin());
            OnlineManager.addOnlineUser(client);
            PostingService.sendChatHistory(client);
            PostingService.broadcast(new Message(PostingService.bot, client.getLogin() + " has joined the server!", Message.Type.SYSTEM));
        } else throw new ClassNotFoundException("Incorrect header");
    }

    private void handleMessage(Message msg) {
        switch (msg.getType()) {
            case PING:
                pingRecieved = true;
                break;
            case TEXT:
                PostingService.broadcast(msg);
                break;
            case SYSTEM:
        }
    }

    @Override
    public void run() {
        try {
            getHeader();
            startPingTimer();
            while (client.getSocket().isConnected()) {
                Message msg = (Message) client.getInputStream().readObject();
                handleMessage(msg);
            }
        } catch (IOException e) {
            System.err.println("Error while sending message to " + client.getLogin() + " closing connection...");
            OnlineManager.disconnectUser(client);
        } catch (ClassNotFoundException e) {
            System.err.println("Classnotfoundexception in ClientHandler");
        }
    }


}
