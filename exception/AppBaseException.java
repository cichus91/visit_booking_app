
package pl.spjava.gabinet.exception;


import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.PUT;


@ApplicationException(rollback=true)
abstract public class AppBaseException extends Exception {
      
    static final public String KEY_TX_RETRY_ROLLBACK = "error.tx.retry.rollback";

    public static final String KEY_OPTIMISTIC_LOCK_EX = "error.optimistic.lock.exception";

    public static final String KEY_OBJECT_NOT_FOUND_EX = "error.object.not.fount.exception";

    public static final String KEY_NO_STATE_IN_EJB = "error.no.object.in.ejb.state.exception";
    
    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AppBaseException(String message) {
        super(message);
    }
    
}
