CREATE TABLE IF NOT EXISTS conversation_call_handling(
    id SERIAL PRIMARY KEY,
    call_id INT NOT NULL REFERENCES conversation_call(id),
    response_type varchar(256),
    response_value varchar(256),
    context json,
    timestamp_created TIMESTAMP NOT NULL,
    timestamp_updated TIMESTAMP NOT NULL
)
