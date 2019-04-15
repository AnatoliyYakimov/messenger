package client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TestClient {
    static public void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(100);
       /*for(int i = 1; i <= 100; i++)
        int i = 1;
            executorService.scheduleWithFixedDelay(new ClientThread("ОРУССУМ_" + i), 1, 1, TimeUnit.SECONDS);
        */
        ClientFactory factory = new ClientFactory(4, 2000);
        executorService.execute(factory);
    }
}
