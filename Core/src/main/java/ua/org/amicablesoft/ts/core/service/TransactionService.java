package ua.org.amicablesoft.ts.core.service;

import ua.org.amicablesoft.ts.core.model.Transaction;

import java.util.List;

/**
 * Created by bogdan on 10/14/15.
 */
public interface TransactionService {
    void save(Transaction transaction);
    Transaction findById(long transactionId);
    List<Long> findTyType(String transactionType);
    double computeAmountSumById(long transactionId);
}
