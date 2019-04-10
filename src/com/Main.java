package com;

import client.TestClient;
import server.Server;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args){
        Executor ex = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            ex.execute(new TestClient("User" + (i + 1), "Test msg"));
        }
        Server server = new Server();
    }
}
