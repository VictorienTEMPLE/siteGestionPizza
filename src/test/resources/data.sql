DROP TABLE IF EXISTS ingredient;
CREATE TABLE ingredient(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    quantiteStock INTEGER NOT NULL,
    enStock BIT NOT NULL
);

INSERT INTO ingredient (nom, quantiteStock, enStock) VALUES
('Tomates',12,true),
('Fromage',11,true);