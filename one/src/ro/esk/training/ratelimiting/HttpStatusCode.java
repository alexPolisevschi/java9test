package ro.esk.training.ratelimiting;

public enum HttpStatusCode {

    SC_OK(200),
    SC_TOO_MANY_REQUESTS(429);


    private int code;

    HttpStatusCode(int code) {
        this.code = code;
    }


    public int getCode() {
        return code;
    }
}
