package ua.org.amicablesoft.ts.dal;

import org.springframework.beans.factory.annotation.Autowired;
import ua.org.amicablesoft.ts.core.dal.error.DalException;
import ua.org.amicablesoft.ts.core.model.Transaction;
import ua.org.amicablesoft.ts.core.dal.TransactionStorage;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by bogdan on 10/14/15.
 */
public class TransactionStorageImpl implements TransactionStorage {

    @Autowired
    DataSource dataSource;

    @Override
    public void save(Transaction t) throws DalException {
        try (Connection conn = connection();
             CallableStatement call = conn.prepareCall("{call ts__save(?,?,?,?)}")) {
            call.setLong(1, t.getId());
            if (t.getParentId() != null)
                call.setLong(2, t.getParentId());
            else
                call.setNull(2, Types.INTEGER);
            call.setString(3, t.getType());
            call.setDouble(4, t.getAmount());
            call.execute();
        }
        catch (SQLException ex) {
            throw new DalException(ex);
        }
    }

    @Override
    public Transaction findById(long id) throws DalException {
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT *" +
                     "  FROM TRANSACTION_TABLE " +
                     " WHERE transaction_id = ?")) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createTransaction(rs);
                }

                throw new DalException("Not found");
            }
        }
        catch (SQLException ex) {
            throw new DalException(ex);
        }
    }

    @Override
    public List<Transaction> findByParentId(long id) throws DalException {
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT *" +
                     "  FROM TRANSACTION_TABLE " +
                     " WHERE parent_id = ?")) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                List<Transaction> result = new LinkedList<>();
                while (rs.next()) {
                    result.add(createTransaction(rs));
                }

                return result;
            }
        }
        catch (SQLException ex) {
            throw new DalException(ex);
        }
    }

    @Override
    public List<Long> findByType(String transactionType) throws DalException {
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT transaction_id" +
                     "  FROM TRANSACTION_TABLE " +
                     " WHERE t_type = ?")) {

            ps.setString(1, transactionType);

            try (ResultSet rs = ps.executeQuery()) {
                List<Long> result = new LinkedList<>();
                while (rs.next())
                    result.add(rs.getLong("transaction_id"));

                return result;
            }
        }
        catch (SQLException ex) {
            throw new DalException(ex);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    private Connection connection() throws SQLException {
        return dataSource.getConnection();
    }

    private Transaction createTransaction(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setId(rs.getLong("transaction_id"));
        long parentId = rs.getLong("parent_id");
        if (!rs.wasNull())
            t.setParentId(parentId);
        t.setType(rs.getString("t_type"));
        t.setAmount(rs.getDouble("amount"));
        return t;
    }
}
