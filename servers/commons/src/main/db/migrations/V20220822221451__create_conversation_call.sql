CREATE TABLE IF NOT EXISTS conversation_call(
    id SERIAL PRIMARY KEY,
    phone varchar(256) NOT NULL,
    did varchar(256) NOT NULL,
    crt_id varchar(256) NOT NULL,
    timestamp_created TIMESTAMP NOT NULL,
    timestamp_updated TIMESTAMP NOT NULL
)
