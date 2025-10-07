-- Script SQL pour les tests d'intégration
-- Données de test pour l'AuthController

-- Insertion des utilisateurs de test
INSERT INTO USERS (id, email, first_name, last_name, password, admin, created_at, updated_at)
VALUES 
    (1, 'yoga@studio.com', 'Admin', 'Admin', '$2a$12$1aX8Ldlhto/wk2ds5ju9UOBbqlbtWpOKayi6pqTOVgzfuSYFo55EC', true, NOW(), NOW()),
    (2, 'edouard.plantevin@email.com', 'Edouard', 'Plantevin', '$2a$12$1aX8Ldlhto/wk2ds5ju9UOBbqlbtWpOKayi6pqTOVgzfuSYFo55EC', false, NOW(), NOW()),
    (3, 'juliette.plantevin@email.com', 'Juliette', 'Plantevin', '$2a$12$1aX8Ldlhto/wk2ds5ju9UOBbqlbtWpOKayi6pqTOVgzfuSYFo55EC', false, NOW(), NOW());

-- Insertion des professeurs de test
INSERT INTO TEACHERS (id, first_name, last_name, created_at, updated_at)
VALUES 
    (1, 'teacher_firstname_1', 'teacher_lastname_1', NOW(), NOW()),
    (2, 'teacher_firstname_2', 'teacher_lastname_2', NOW(), NOW()),
    (3, 'teacher_firstname_3', 'teacher_lastname_3', NOW(), NOW());

-- Insertion des sessions de test
INSERT INTO SESSIONS (id, name, description, date, teacher_id, created_at, updated_at)
VALUES 
    (1, 'Yoga Vinyasa Débutant', 'Séance de yoga dynamique parfaite pour débuter.', '2024-02-15 09:00:00', 1, NOW(), NOW()),
    (2, 'Hatha Yoga Relaxant', 'Séance de yoga doux et méditatif.', '2024-02-16 18:30:00', 2, NOW(), NOW()),
    (3, 'Yin Yoga & Méditation', 'Séance de Yin Yoga avec postures passives.', '2024-02-18 19:00:00', 3, NOW(), NOW());

-- Insertion des participations
INSERT INTO PARTICIPATE (user_id, session_id)
VALUES 
    (2, 1), (2, 2), (2, 3),
    (3, 1), (3, 2);
