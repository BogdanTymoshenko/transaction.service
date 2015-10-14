CREATE OR REPLACE FUNCTION ts__save(
    transactionId BIGINT,
    tParentId     BIGINT,
    tType         TEXT,
    tAmount       FLOAT
)
RETURNS VOID AS $$
BEGIN
  BEGIN
    INSERT INTO TRANSACTION_TABLE (
      transaction_id,
      parent_id,
      t_type,
      amount)
    VALUES (
      transactionId,
      tParentId,
      tType,
      tAmount);
  EXCEPTION WHEN unique_violation THEN
      UPDATE TRANSACTION_TABLE SET
        parent_id = tParentId,
        t_type = tType,
        amount = tAmount
      WHERE transaction_id = transactionId;
  END;
END $$
LANGUAGE plpgsql;
