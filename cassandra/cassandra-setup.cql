CREATE KEYSPACE IF NOT EXISTS coinbase
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE coinbase;

CREATE TABLE IF NOT EXISTS coinbase_prices (
    sequence BIGINT,
    product_id TEXT,
    price FLOAT,
    low_24h FLOAT,
    high_24h FLOAT,
    time TIMESTAMP, 
    PRIMARY KEY (product_id, time)
) WITH CLUSTERING ORDER BY (time DESC);
