package ua.org.amicablesoft.ts.core.dal;

import ua.org.amicablesoft.ts.core.dal.error.DalException;
import ua.org.amicablesoft.ts.core.model.Transaction;

import java.util.List;

/**
 * Created by bogdan on 10/14/15.
 */
public interface TransactionStorage {
    void save(Transaction transaction) throws DalException;
    Transaction findById(long id) throws DalException;
    List<Transaction> findByParentId(long id) throws DalException;
    List<Long> findByType(String transactionType) throws DalException;
}
