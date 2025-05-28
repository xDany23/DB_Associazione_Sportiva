package db_ass.data;

import java.io.Serial;

public final class DAOException extends RuntimeException{
    
    @Serial
    private static final long serialVersionUID = 1L;

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

}
