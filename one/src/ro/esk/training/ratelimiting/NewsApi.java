package ro.esk.training.ratelimiting;

import java.util.ArrayList;
import java.util.List;

public class NewsApi {

    private static final int MAX_REQUESTS_PER_SECOND = 5;


    private List<Long> accessRecords;

    public NewsApi() {
        accessRecords = new ArrayList<>();
    }


    public synchronized ApiResponse getWeather() {
        Long currentTimestamp = System.currentTimeMillis();

        if (accessRecords.size() < MAX_REQUESTS_PER_SECOND) {
            accessRecords.add(currentTimestamp);
            return ApiResponse.successful(getWeatherFromWeatherStation());
        }

        if (moreThanOneSecondPassed(accessRecords.get(0), currentTimestamp)) {
            accessRecords.remove(0);
            accessRecords.add(currentTimestamp);
            return ApiResponse.successful(getWeatherFromWeatherStation());
        }

        return ApiResponse.tooManyRequests();
    }


    private boolean moreThanOneSecondPassed(long firstTimestamp, long secondTimestamp) {
        return secondTimestamp - firstTimestamp > 1000;
    }

    private String getWeatherFromWeatherStation() {
        return "The weather is excellent!";
    }
}
