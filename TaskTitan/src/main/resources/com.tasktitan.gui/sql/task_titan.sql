CREATE DATABASE tasktitan_app;
USE tasktitan_app;

CREATE TABLE tasks (
    id INT PRIMARY KEY,
    task_type VARCHAR(20),

    title VARCHAR(255),
    description TEXT,

    priority VARCHAR(20),
    status VARCHAR(20),

    deadline_day INT,
    deadline_month INT,
    deadline_year INT,

    assigned_member VARCHAR(100),

    technical_specification TEXT,

    code_part TEXT,
    error_message TEXT,
    fix_proposition TEXT
);