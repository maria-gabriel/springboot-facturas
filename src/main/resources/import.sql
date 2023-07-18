/* INSERT VALUES CLIENTES */
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (1, 'Mary', 'Biel', 'mariz.magr@gmail.com', '2023-07-06', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (2, 'John', 'Doe', 'doe@gmail.com', '2023-07-09', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (3, 'Rubius', 'Omg', 'omg@gmail.com', '2023-07-09', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (4, 'Pepe', 'Problemas', 'trouble@gmail.com', '2023-07-09', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (5, 'Juxi', 'Jox', 'juxi@gmail.com', '2023-07-10', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (6, 'Rivers', 'You', 'you@gmail.com', '2023-07-10', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (7, 'Arely', 'Samantha', 'arely@gmail.com', '2023-07-10', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (8, 'Betsy', 'Romero', 'raton@gmail.com', '2023-07-10', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (9, 'Cris', 'Yair', 'yair@gmail.com', '2023-07-10', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (10, 'Alain', 'Rivers', 'rios@gmail.com', '2023-07-10', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (11, 'Jane', 'Rivers', 'riv_ers@gmail.com', '2023-07-10', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (12, 'Carlos', 'Trujillo', 'jcht@gmail.com', '2023-07-10', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (13, 'Jenny', 'Olivia', 'yolis@gmail.com', '2023-07-10', '');
INSERT INTO clientes (id, nombre, apellido, email, created_at, foto) VALUES (14, 'Dante', 'Gabriel', 'dante@gmail.com', '2023-07-10', '');

/* INSERT VALUES PRODUCTOS */
INSERT INTO productos (nombre, precio, created_at) VALUES ('Pantalla Panasonic', 6000, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES ('Camara Sony', 4500, '2023-07-10');
INSERT INTO productos (nombre, precio, created_at) VALUES ('Xbox 360', 3900, '2023-07-10');
INSERT INTO productos (nombre, precio, created_at) VALUES ('One Plus Nord 2', 7000, '2023-07-10');
INSERT INTO productos (nombre, precio, created_at) VALUES ('Huawei Band 6', 400, '2023-07-10');
INSERT INTO productos (nombre, precio, created_at) VALUES ('Audifonos Haylou', 600, '2023-07-10');

/* INSERT VALUES FACTURAS */
INSERT INTO facturas (descripcion, observacion, cliente_id, created_at) VALUES ('Equipos de oficina', null, 1, '2023-07-10');

/* INSERT VALUES FACTURAS ITEMS */
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (2, 1, 5);