INSERT INTO ingredient (nom, quantite_en_stock, en_stock) VALUES
('Tomate',12,true),
('Fromage',11,true);

INSERT INTO pizza (nom, actif) VALUES
('Margarita', true);

INSERT INTO pizza_tarif (pizza_id, taille, tarif) VALUES
(1, 'PETITE', 7.0);

INSERT INTO pizza_ingredient (pizza_id, ingredient_id) VALUES
(1,1),
(1,2);




