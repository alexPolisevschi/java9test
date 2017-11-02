package ro.esk.training.ratelimiting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        NewsApi newsApi = new NewsApi();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 100; i++) {
            executorService.submit( () -> {
                ApiResponse apiResponse = newsApi.getWeather();
                try {
                    sleep(180);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " :" + apiResponse);
            });
        }
        executorService.shutdown();
    }

}
