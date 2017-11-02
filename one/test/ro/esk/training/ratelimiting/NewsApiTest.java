package ro.esk.training.ratelimiting;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ro.esk.training.ratelimiting.HttpStatusCode.SC_OK;

public class NewsApiTest {

    private NewsApi newsApi;

    private List<Integer> responseCodes = new ArrayList<>();

    @Before
    public void setUp() {
        newsApi = new NewsApi();
        responseCodes = new ArrayList<>();
    }


    @Test
    public void tenConsecutiveRequests_fiveGetProcessed() {
        for (int i = 0; i < 10; i++) {
            int responseCode = newsApi.getWeather().getHttpCode();
            responseCodes.add(responseCode);
        }

        int acceptedResponsesCount = (int) responseCodes.stream().filter(x -> x == SC_OK.getCode()).count();
        assertThat("Expected only a limited amount of requests to pass", acceptedResponsesCount, is(5));
    }


    @Test
    public void tenRequestsWithOneSecondDelay_allRequestsAccepted() throws InterruptedException {
        for (int i =0; i < 10; i++) {
            int responseCode = newsApi.getWeather().getHttpCode();
            responseCodes.add(responseCode);
            sleep(1000);
        }

        int acceptedResponsesCount = (int) responseCodes.stream().map(x -> x = SC_OK.getCode()).count();
        assertThat("Expected all requests to pass", acceptedResponsesCount, is(10));
    }


    @Test
    public void requestsFromTwoThreads_onlyFivePass() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10; i++) {
            Future future = executorService.submit(getWorker());
            responseCodes.add((int) future.get());
        }
        executorService.shutdown();

        int acceptedResponsesCount = (int) responseCodes.stream().filter(x -> x == SC_OK.getCode()).count();
        assertThat("Expected only a limited amount of requests to pass", acceptedResponsesCount, is(5));
    }

    private Callable<Integer> getWorker() {
        return () -> {
            System.out.println(Thread.currentThread().getName());
            return newsApi.getWeather().getHttpCode();
        };
    }
}
