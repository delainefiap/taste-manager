INSERT INTO user_types (name) VALUES
('CLIENTE'),
('RESTAURANTE');

INSERT INTO users (name, email, login, password, created_at, user_type_id, address) VALUES
('João Silva', 'joao.silva@email.com', 'joaosilva', 'senha123', NOW(), 1, 'Rua A, 123'),
('Maria Oliveira', 'maria.oliveira@email.com', 'mariaoliveira', 'senha456', NOW(), 2, 'Rua B, 456'),
('Carlos Souza', 'carlos.souza@email.com', 'carlossouza', 'senha789',  NOW(), 1, 'Rua C, 789');

