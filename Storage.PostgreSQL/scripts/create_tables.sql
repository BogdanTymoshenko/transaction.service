CREATE TABLE TRANSACTION_TABLE (
    transaction_id  BIGINT NOT NULL,
    parent_id       BIGINT,
    t_type          TEXT,
    amount          FLOAT
);

ALTER TABLE TRANSACTION_TABLE ADD CONSTRAINT TRANSACTION_TABLE__PK PRIMARY KEY(transaction_id);
