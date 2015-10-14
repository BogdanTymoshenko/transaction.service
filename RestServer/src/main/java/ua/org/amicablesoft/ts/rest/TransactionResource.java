package ua.org.amicablesoft.ts.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.org.amicablesoft.ts.core.service.TransactionService;
import ua.org.amicablesoft.ts.core.model.Transaction;
import ua.org.amicablesoft.ts.rest.data.TransactionAmountSum;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by bogdan on 10/14/15.
 */
@Component
@Path("/transactionservice")
public class TransactionResource {

    @Autowired
    TransactionService transactionService;

    @GET
    @Produces("application/json")
    @Path("/sum/{transaction_id}")
    public TransactionAmountSum computeAmountSumById(@PathParam("transaction_id") long transactionId) {
        double sum = transactionService.computeAmountSumById(transactionId);
        return new TransactionAmountSum(sum);
    }

    @GET
    @Produces("application/json")
    @Path("/types/{type}")
    public List<Long> findTransactionsByType(@PathParam("type")String type) {
        return transactionService.findTyType(type);
    }

    @GET
    @Produces("application/json")
    @Path("/transaction/{transaction_id}")
    public Transaction findTransactionById(@PathParam("transaction_id")long transactionId) {
        return transactionService.findById(transactionId);
    }

    @PUT
    @Consumes("application/json")
    @Path("/transaction/{transaction_id}")
    public Response saveTransaction(@PathParam("transaction_id")long transactionId, Transaction transaction) {
        transaction.setId(transactionId);
        transactionService.save(transaction);
        return Response.status(Response.Status.OK).build();
    }
}
