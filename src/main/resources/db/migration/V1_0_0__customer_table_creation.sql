
CREATE TABLE customer (
id SERIAL PRIMARY KEY,
cpf_cnpj VARCHAR(14) NOT NULL,
nome VARCHAR(255) NOT NULL,
address VARCHAR(255) NOT NULL,
phone VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL)