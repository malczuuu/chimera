CREATE
    SEQUENCE seq_weather START 1;

CREATE
    TABLE
        weather(
            id BIGINT PRIMARY KEY DEFAULT nextval('seq_weather'),
            city VARCHAR(256),
            temperature DOUBLE PRECISION,
            description VARCHAR(256),
            labels VARCHAR(1024),
            TIMESTAMP TIMESTAMP WITH TIME ZONE,
            created_by VARCHAR(256),
            created_date TIMESTAMP WITH TIME ZONE,
            last_modified_by VARCHAR(256),
            last_modified_date TIMESTAMP WITH TIME ZONE,
            version BIGINT,
            dtype VARCHAR(1) NOT NULL
        );
