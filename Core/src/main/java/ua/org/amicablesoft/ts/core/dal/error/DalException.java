package ua.org.amicablesoft.ts.core.dal.error;

/**
 * Created by bogdan on 10/14/15.
 */
public class DalException extends Exception {
    public DalException(Exception ex) {
        super(ex);
    }

    public DalException(String message) {
        super(message);
    }
}
