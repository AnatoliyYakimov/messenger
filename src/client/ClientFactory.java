package client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class ClientFactory implements Runnable {
    int n;
    int DELAY;

    ClientFactory(int n, int DELAY) {
        this.DELAY = DELAY;
        this.n = n;
    }

    @Override
    public void run() {
        ExecutorService ex = Executors.newFixedThreadPool(n);
        for (int i = 1; i < n + 1; i++) {
            ex.execute(new ClientThread("Оруссум_" + i));
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
