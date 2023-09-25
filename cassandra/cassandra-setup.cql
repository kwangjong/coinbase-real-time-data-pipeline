REATE KEYSPACE IF NOT EXISTS coinbase
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE coinbase;

CREATE TABLE IF NOT EXISTS coinbase_prices (
    sequence BIGINT PRIMARY KEY,
    product_id TEXT,
    price TEXT,
    low_24h DOUBLE,
    high_24h DOUBLE,
    time TIMESTAMP
);
WITH CLUSTERING ORDER BY (time DESC);
