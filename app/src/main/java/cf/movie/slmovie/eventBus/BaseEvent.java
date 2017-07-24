package cf.movie.slmovie.eventBus;

/**
 * Created by 包俊 on 2017/7/21.
 */

public class BaseEvent {
    private boolean status;
    private String message;

    public BaseEvent(boolean status, String message) {
        this.setStatus(status);
        this.setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
