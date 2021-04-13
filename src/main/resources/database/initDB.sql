CREATE TABLE IF NOT EXISTS users
(
    login         VARCHAR(50) PRIMARY KEY,
    full_name     VARCHAR(50) NOT NULL,
    date_of_birth DATE        NOT NULL,
    gender        VARCHAR(50) NOT NULL
);