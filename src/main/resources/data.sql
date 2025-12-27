-- data.sql - Données initiales pour EventPlatform
-- À placer dans : src/main/resources/data.sql

-- NOTE: Ce fichier est OPTIONNEL
-- Les tables sont créées automatiquement par Hibernate
-- Utilisez ce fichier seulement si vous voulez des données de démo

-- IMPORTANT: Commentez ces lignes après la première exécution
-- ou ajoutez dans application.properties :
-- spring.sql.init.mode=never

-- Exemple d'insertion d'un utilisateur admin (mot de passe: admin123)
-- Le mot de passe doit être hashé avec BCrypt
-- INSERT INTO users (username, password) VALUES 
-- ('admin', '$2a$10$HASHED_PASSWORD_HERE');

-- INSERT INTO user_roles (user_id, role) VALUES 
-- (1, 'ADMIN');

-- Exemple d'insertion d'un événement
-- INSERT INTO events (title, description, event_date, location, total_tickets, ticket_price, organizer_id)
-- VALUES ('Concert de Jazz', 'Soirée jazz exceptionnelle', '2024-12-31 20:00:00', 'Théâtre Mohammed V', 200, 150.00, 1);

-- ========================================
-- SI VOUS VOULEZ DES DONNÉES DE TEST
-- ========================================

-- 1. Créer les utilisateurs via l'interface web (recommandé)
-- 2. OU décommenter et adapter les lignes suivantes :

/*
-- Admin (username: admin, password: admin123)
INSERT INTO users (username, password) VALUES 
('admin', '$2a$10$N.zmdr9A.qXEfYYqQjdQGuCRbaYhjXOeLjKN.UyXQNxmqYQlLPLOi');

INSERT INTO user_roles (user_id, role) VALUES (1, 'ADMIN');

-- Participant (username: user1, password: user123)
INSERT INTO users (username, password) VALUES 
('user1', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWLN7xWGAQLRm');

INSERT INTO user_roles (user_id, role) VALUES (2, 'PARTICIPANT');

-- Événements de test
INSERT INTO events (title, description, event_date, location, total_tickets, ticket_price, organizer_id)
VALUES 
('Concert de Jazz', 'Soirée exceptionnelle avec des artistes renommés', '2024-12-31 20:00:00', 'Théâtre Mohammed V, Rabat', 200, 150.00, 1),
('Festival de Musique', 'Festival de musique en plein air', '2024-12-25 18:00:00', 'Parc de la Ligue Arabe, Casablanca', 500, 200.00, 1),
('Conférence Tech', 'Conférence sur les nouvelles technologies', '2024-12-20 09:00:00', 'Centre de Conférences, Marrakech', 100, 50.00, 1);
*/