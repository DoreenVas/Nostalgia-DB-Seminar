package Resources;

import com.sun.javaws.exceptions.ErrorCodeResponseException;

public class DBConnectionException extends Exception {

    public DBConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBConnectionException(String message) {
        super(message);
    }

    public DBConnectionException(Throwable cause) {
        super(cause);
    }

    public DBConnectionException() {
        super();
    }
}
