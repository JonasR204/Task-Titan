CREATE DATABASE IF NOT EXISTS tasktitan_app;
USE tasktitan_app;

CREATE TABLE members (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL,
                         role VARCHAR(100) NOT NULL
);

CREATE TABLE task(
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     task_type VARCHAR(20) NOT NULL,
                     title VARCHAR(255) NOT NULL,
                     description TEXT,
                     priority VARCHAR(20) NOT NULL,
                     status VARCHAR(20) NOT NULL,
                     deadline DATE,
                     assigned_member VARCHAR(100),

                     technical_specification TEXT,

                     code_part TEXT,
                     error_message TEXT,
                     fix_proposition TEXT
);

ALTER TABLE task
    ADD FOREIGN KEY(assigned_member_id) REFERENCES members(id);