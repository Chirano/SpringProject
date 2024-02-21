-- Brands
INSERT INTO brand (id, name, country) VALUES (1, 'Toyota', 'Japan');
INSERT INTO brand (id, name, country) VALUES (2, 'BMW', 'Germany');
INSERT INTO brand (id, name, country) VALUES (3, 'Ford', 'United States');
INSERT INTO brand (id, name, country) VALUES (4, 'Honda', 'Japan');
INSERT INTO brand (id, name, country) VALUES (5, 'Mercedes-Benz', 'Germany');
INSERT INTO brand (id, name, country) VALUES (6, 'Chevrolet', 'United States');
INSERT INTO brand (id, name, country) VALUES (7, 'Volkswagen', 'Germany');
INSERT INTO brand (id, name, country) VALUES (8, 'Hyundai', 'South Korea');
INSERT INTO brand (id, name, country) VALUES (9, 'Audi', 'Germany');
INSERT INTO brand (id, name, country) VALUES (10, 'Nissan', 'Japan');

-- Sellers
INSERT INTO seller (id, name, email, phone_number) VALUES (1, 'Mary', 'mary@example.com', '999111000' );
INSERT INTO seller (id, name, email, phone_number) VALUES (2, 'John', 'john@example.com', '999111222' );
INSERT INTO seller (id, name, email, phone_number) VALUES (3, 'Paul', 'paul@example.com', '999111333' );
INSERT INTO seller (id, name, email, phone_number) VALUES (4, 'Emily', 'emily@example.com', '955123567');
INSERT INTO seller (id, name, email, phone_number) VALUES (5, 'June', 'john@example.com', '9559876543');
INSERT INTO seller (id, name, email, phone_number) VALUES (6, 'Sarah', 'sarah@example.com', '9557778888');
INSERT INTO seller (id, name, email, phone_number) VALUES (7, 'Michael', 'michael@example.com', '9551112222');
INSERT INTO seller (id, name, email, phone_number) VALUES (8, 'Jessica', 'jessica@example.com', '9554445555');
INSERT INTO seller (id, name, email, phone_number) VALUES (9, 'Daniel', 'daniel@example.com', '9553336666');
INSERT INTO seller (id, name, email, phone_number) VALUES (10, 'Amy', 'amy@example.com', '9552227777');


-- Toyota
INSERT INTO model (id, name, brand_id) VALUES (1, 'Camry', 1);
INSERT INTO model (id, name, brand_id) VALUES (2, 'Corolla', 1);
INSERT INTO model (id, name, brand_id) VALUES (3, 'Rav4', 1);

-- BMW
INSERT INTO model (id, name, brand_id) VALUES (4, '3 Series', 2);
INSERT INTO model (id, name, brand_id) VALUES (5, '5 Series', 2);
INSERT INTO model (id, name, brand_id) VALUES (6, 'X5', 2);

-- Ford
INSERT INTO model (id, name, brand_id) VALUES (7, 'F-150', 3);
INSERT INTO model (id, name, brand_id) VALUES (8, 'Mustang', 3);
INSERT INTO model (id, name, brand_id) VALUES (9, 'Escape', 3);

-- Honda
INSERT INTO model (id, name, brand_id) VALUES (10, 'Civic', 4);
INSERT INTO model (id, name, brand_id) VALUES (11, 'Accord', 4);
INSERT INTO model (id, name, brand_id) VALUES (12, 'CR-V', 4);

-- Mercedes-Benz
INSERT INTO model (id, name, brand_id) VALUES (13, 'C-Class', 5);
INSERT INTO model (id, name, brand_id) VALUES (14, 'E-Class', 5);
INSERT INTO model (id, name, brand_id) VALUES (15, 'GLC', 5);

-- Chevrolet
INSERT INTO model (id, name, brand_id) VALUES (16, 'Silverado', 6);
INSERT INTO model (id, name, brand_id) VALUES (17, 'Equinox', 6);
INSERT INTO model (id, name, brand_id) VALUES (18, 'Camaro', 6);

-- Volkswagen
INSERT INTO model (id, name, brand_id) VALUES (19, 'Golf', 7);
INSERT INTO model (id, name, brand_id) VALUES (20, 'Passat', 7);
INSERT INTO model (id, name, brand_id) VALUES (21, 'Tiguan', 7);

-- Hyundai
INSERT INTO model (id, name, brand_id) VALUES (22, 'Elantra', 8);
INSERT INTO model (id, name, brand_id) VALUES (23, 'Tucson', 8);
INSERT INTO model (id, name, brand_id) VALUES (24, 'Santa Fe', 8);

-- Audi
INSERT INTO model (id, name, brand_id) VALUES (25, 'A3', 9);
INSERT INTO model (id, name, brand_id) VALUES (26, 'A4', 9);
INSERT INTO model (id, name, brand_id) VALUES (27, 'Q5', 9);

-- Nissan
INSERT INTO model (id, name, brand_id) VALUES (28, 'Altima', 10);
INSERT INTO model (id, name, brand_id) VALUES (29, 'Sentra', 10);
INSERT INTO model (id, name, brand_id) VALUES (30, 'Rogue', 10);


-- Vehicles
INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('1HGCM82633A123456', 25000, 2023, 0, 1, 2, 'red', 'gas', 'auto', 'Available');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('2T1BR32E33C123457', 18000, 2018, 75, 2, 3, 'blue', 'gas', 'auto', 'Sold');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('JM1BL1KF5A1123458', 20000, 2019, 100, 3, 4, 'black', 'diesel', 'manual', 'Available');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('WBA3A5G56EN123459', 30000, 2020, 80, 4, 5, 'white', 'gas', 'auto', 'Inspection');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('1FTSW21R78EA123460', 28000, 2017, 70, 5, 6, 'silver', 'diesel', 'auto', 'Available');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('5J6RE4H37BL123461', 22000, 2021, 100, 6, 7, 'green', 'gas', 'manual', 'Sold');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('WDBSK75F35F123462', 35000, 2018, 130, 7, 8, 'red', 'diesel', 'auto', 'Reserved');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('KMHD84LF3LU123463', 19000, 2022, 5000, 8, 9, 'blue', 'gas', 'manual', 'Available');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('WAUMFAFL8AN123464', 26000, 2016, 60, 9, 10, 'black', 'diesel', 'auto', 'Sold');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('JN8AZ2NF1C123465', 24000, 2023, 0, 10, 1, 'white', 'gas', 'auto', 'Reserved');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('5YJSA1E48HF123466', 60000, 2020, 80, 1, 2, 'silver', 'gas', 'auto', 'Available');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('1G1ZT528X1F123467', 15000, 2017, 155, 2, 3, 'red', 'diesel', 'manual', 'Inspection');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('WA1VAAF74FD123468', 28000, 2018, 120, 3, 4, 'blue', 'gas', 'auto', 'Available');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('KMHCT4AE6HU123469', 20000, 2021, 75, 4, 5, 'black', 'diesel', 'manual', 'Sold');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('1FA6P8CF3JF123470', 32000, 2019, 120, 5, 6, 'white', 'gas', 'auto', 'Sold');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('1C4RDJAG6FC123471', 25000, 2023, 0, 6, 7, 'silver', 'diesel', 'auto', 'Reserved');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('1GNKVJKD8FJ123472', 34000, 2020, 100, 7, 8, 'red', 'gas', 'manual', 'Available');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('5NPD84LF9LH123473', 17000, 2018, 140, 8, 9, 'blue', 'diesel', 'auto', 'Sold');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('2LMHJ5FK5EB123474', 30000, 2022, 80, 9, 10, 'black', 'gas', 'auto', 'Available');

INSERT INTO vehicle (vin, price, release_year, kilometers, model_id, seller_id, color, fuel, gear, status)
VALUES ('JTMZFREV9HD123475', 23000, 2017, 100, 10, 1, 'white', 'diesel', 'manual', 'Available');
