package com.yakimov.server.model;

import com.yakimov.server.utility.Config;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
    Мониторит сетевой порт и для каждого нового подключения создаёт ClientHandler
 */
public class ConnectionManager implements Runnable {


    ConnectionManager() {
        System.out.println("Starting connection manager...");
    }


    @Override
    public void run() {
        try {
            System.out.println("Connection manager started!");

            ServerSocket socketListener = new ServerSocket(Config.PORT, 0, InetAddress.getByName("localhost"));
            while (true) {
                Socket client = null;
                while (client == null) {
                    client = socketListener.accept();
                }
                ModelInterface.execute(new ClientHandler(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
