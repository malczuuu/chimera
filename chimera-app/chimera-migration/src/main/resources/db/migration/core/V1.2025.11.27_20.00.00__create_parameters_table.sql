CREATE
    SEQUENCE seq_parameters START 1;

CREATE
    TABLE
        parameters(
            id BIGINT PRIMARY KEY DEFAULT nextval('seq_parameters'),
            code VARCHAR(256) NOT NULL,
            value VARCHAR(2048),
            created_by VARCHAR(256),
            created_date TIMESTAMP WITH TIME ZONE,
            last_modified_by VARCHAR(256),
            last_modified_date TIMESTAMP WITH TIME ZONE,
            version BIGINT,
            dtype VARCHAR(1) NOT NULL
        );

CREATE
    UNIQUE INDEX idx_parameters_code ON
    parameters(code);
