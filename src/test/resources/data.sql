INSERT INTO ingredient (nom, quantite_en_stock, en_stock) VALUES
('Tomate',12,true),
('Fromage',11,true);


INSERT INTO pizza_tarif (pizza_id, taille, tarif) VALUES
(1, "petite", 7.0),
(1, "moyenne", 12.0),
(1, "grande", 17.5);

INSERT INTO pizza_ingredient (id_pizza, id_ingredient) VALUES
(1,1),
(1,2),
(2,1),
(2,2);

INSERT INTO pizza (nom, tarif, ingredient, actif) VALUES
('Margherita',12,true),
('Fromage',11,true);

