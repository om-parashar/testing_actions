CREATE TABLE IF NOT EXISTS conversation_caller_context(
    id SERIAL PRIMARY KEY,
    call_id INT NOT NULL REFERENCES conversation_call(id),
    context json,
    timestamp_created TIMESTAMP NOT NULL,
    timestamp_updated TIMESTAMP NOT NULL
)
