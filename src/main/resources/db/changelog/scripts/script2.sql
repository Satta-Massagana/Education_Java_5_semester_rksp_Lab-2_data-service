CREATE TABLE UserEvent (
    id          BIGSERIAL PRIMARY KEY,
    event_type  TEXT NOT NULL,
    event_time  TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_userevent_time ON UserEvent(event_time);
CREATE INDEX idx_userevent_type ON UserEvent(event_type);
