CREATE TABLE  os_metric
(
    id          uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    description text             NOT NULL,
    name        text             NOT NULL,
    value       double precision NOT NULL,
    created_at  timestamp        NOT NULL
);