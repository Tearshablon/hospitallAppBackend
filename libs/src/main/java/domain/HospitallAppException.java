package domain;

public class HospitallAppException extends RuntimeException {
    public HospitallAppException() {
    }

    public HospitallAppException(String message) {
        super(message);
    }

    public HospitallAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public HospitallAppException(Throwable cause) {
        super(cause);
    }

    public HospitallAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
