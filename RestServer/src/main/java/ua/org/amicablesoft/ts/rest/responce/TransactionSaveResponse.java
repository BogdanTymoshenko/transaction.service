package ua.org.amicablesoft.ts.rest.responce;

/**
 * Created by bogdan on 10/15/15.
 */
public class TransactionSaveResponse {
    private Status status;

    public TransactionSaveResponse(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
