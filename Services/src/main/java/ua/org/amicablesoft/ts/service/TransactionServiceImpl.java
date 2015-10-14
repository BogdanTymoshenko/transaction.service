package ua.org.amicablesoft.ts.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.org.amicablesoft.ts.core.dal.TransactionStorage;
import ua.org.amicablesoft.ts.core.dal.error.DalException;
import ua.org.amicablesoft.ts.core.service.TransactionService;
import ua.org.amicablesoft.ts.core.model.Transaction;
import ua.org.amicablesoft.ts.service.error.InvalidTransactionError;

import java.util.List;

/**
 * Created by bogdan on 10/14/15.
 */
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionStorage transactionStorage;

    @Override
    public void save(Transaction transaction) {
        if (transaction.getId() == null)
            throw new InvalidTransactionError();

        try {
            transactionStorage.save(transaction);
        } catch (DalException ex) {
            throw new Error(ex);
        }
    }

    @Override
    public Transaction findById(long transactionId) {
        try {
            return transactionStorage.findById(transactionId);
        } catch (DalException ex) {
            throw new Error(ex);
        }
    }

    @Override
    public List<Long> findTyType(String transactionType) {
        try {
            return transactionStorage.findByType(transactionType);
        } catch (DalException ex) {
            throw new Error(ex);
        }
    }

    @Override
    public double computeAmountSumById(long transactionId) {
        try {
            return computeAmountTransaction(transactionStorage.findById(transactionId));
        } catch (DalException ex) {
            throw new Error(ex);
        }
    }

    private double computeAmountTransaction(Transaction t) throws DalException {
        double amount = 0;
        amount += t.getAmount();
        for (Transaction child : transactionStorage.findByParentId(t.getId()))
            amount += computeAmountTransaction(child);

        return amount;
    }
}
