INSERT INTO usuarios (username, email, password, rol, created_at)
VALUES (
    'admin',
    'admin@tandiltrails.com',
    '$2a$10$.ITSTnzY0cVpnVaB5CygPugHR1XU2bQgY4HIcuVrCvlRbieqIFlNa',
    'ROLE_ADMIN',
    NOW()
);

INSERT INTO estados_sendero (nombre) VALUES ('Habilitado');
INSERT INTO estados_sendero (nombre) VALUES ('Deshabilitado');
INSERT INTO estados_sendero (nombre) VALUES ('En mantenimiento');
INSERT INTO estados_sendero (nombre) VALUES ('Pendiente');