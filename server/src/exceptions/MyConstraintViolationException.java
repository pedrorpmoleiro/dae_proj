package exceptions;

import java.io.Serializable;

public class MyConstraintViolationException extends Exception implements Serializable {
    public MyConstraintViolationException(String message) {
        super(message);
    }

    public MyConstraintViolationException() {
    }
}
