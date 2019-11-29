package exceptions;

import java.io.Serializable;

public class MyEntityNotFoundException extends Exception implements Serializable {
    public MyEntityNotFoundException(String message) {
        super(message);
    }

    public MyEntityNotFoundException() {
    }
}
