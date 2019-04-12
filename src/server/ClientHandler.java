package server;

import server.entities.Client;
import server.entities.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class ClientHandler implements Runnable {
    private Client client;
    private Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            client = new Client(socket);
        } catch (IOException e) {
            System.err.println("Can`t get input stream, closing connection");
            try {
                socket.close();
            } catch (IOException e1) {
                System.err.println(e1.toString());
            }
        }
    }

    @Override
    public void run() {
        System.out.println("RUNNING");
        if (!client.getSocket().isClosed()) {
            MessageListeners listeners = new MessageListeners();
            listeners.addListener(new SystemListener(client));
            listeners.addListener(msg -> {
                if (msg.getType().equals(Message.Type.TEXT)) {
                    System.out.println(msg.getLogin() + ":>> " + msg.getMessage());
                }
            });

            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.execute(new Thread(new Reciever(client, listeners)));
            listeners.addListener(new Postman());
            Pinger pinger = new Pinger(client);
            listeners.addListener(pinger);
            executor.execute(new Thread(pinger));
        }
    }


}
