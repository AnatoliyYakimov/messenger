package com.yakimov.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class ClientFactory implements Runnable {
    private int n;
    private int DELAY;

    ClientFactory(int n, int DELAY) {
        this.DELAY = DELAY;
        this.n = n;
    }

    @Override
    public void run() {
        //Локальный мем, не придавай значения
        String[] args = {"Im ОРУССУМ", "Я пришёл есть мусор", "И писать сообщения в чат"};
        ExecutorService ex = Executors.newFixedThreadPool(n);
        for (int i = 1; i < n + 1; i++) {
            ex.execute(new ClientThread("Оруссум_" + i, args));
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
