package ro.esk.training.ratelimiting;

import static ro.esk.training.ratelimiting.HttpStatusCode.SC_OK;
import static ro.esk.training.ratelimiting.HttpStatusCode.SC_TOO_MANY_REQUESTS;

public class ApiResponse {

    private int httpCode;
    private String weatherInfo;

    private long timestamp;



    public static ApiResponse successful(String message) {
        ApiResponse apiResponse = new ApiResponse(SC_OK);
        apiResponse.setWeatherInfo(message);
        return apiResponse;
    }

    public static ApiResponse tooManyRequests() {
        return new ApiResponse(SC_TOO_MANY_REQUESTS);
    }


    private ApiResponse(HttpStatusCode httpStatusCode) {
        this.timestamp = System.currentTimeMillis();
        this.httpCode = httpStatusCode.getCode();
    }


    public int getHttpCode() {
        return httpCode;
    }

    public String getWeatherInfo() {
        return weatherInfo;
    }


    private void setWeatherInfo(String weatherInfo) {
        this.weatherInfo = weatherInfo;
    }


    @Override
    public String toString() {
        return "ApiResponse{" +
                "httpCode=" + httpCode +
                ", weatherInfo='" + weatherInfo + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
