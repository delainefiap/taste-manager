--user_types
INSERT INTO user_types (name) VALUES
('CLIENTE'),
('RESTAURANTE');

--users
INSERT INTO users (name, email, login, password, created_at, user_type_id, address) VALUES
('João Silva', 'joao.silva@email.com', 'joaosilva', 'senha123', NOW(), 1, 'Rua A, 123'),
('Maria Oliveira', 'maria.oliveira@email.com', 'mariaoliveira', 'senha456', NOW(), 2, 'Rua B, 456'),
('Carlos Souza', 'carlos.souza@email.com', 'carlossouza', 'senha789',  NOW(), 1, 'Rua C, 789'),
('Delaine Silva', 'delaine@email.com', 'delaine', 'senha456', NOW(), 2, 'Rua D, 111');
;

--restaurants
INSERT INTO restaurant (name, address, type_kitchen, opening_hours, owner_id) VALUES
('Restaurante Saboroso', 'Rua Principal, 100', 'Brasileira', '08:00-22:00', 2),
('Restaurante da Dede', 'Rua Dede, 100', 'Lanches', '08:00-12:00', 4);
