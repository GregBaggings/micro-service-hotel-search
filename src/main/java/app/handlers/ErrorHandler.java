package app.handlers;

/**
 * Created by Gergely_Agnecz on 7/28/2017.
 */
public class ErrorHandler {
    private String error;

    public ErrorHandler(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
