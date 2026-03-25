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

-- Senderos
INSERT INTO senderos (nombre, descripcion, dificultad, estado_id, ruta, longitud)
VALUES (
    'Cerro El Centinela',
    'Sendero clásico de Tandil con vistas panorámicas de la ciudad y la sierra.',
    'MODERADO',
    1,
    ST_GeomFromText('LINESTRING(-59.1133 -37.3221, -59.1150 -37.3240, -59.1170 -37.3260, -59.1190 -37.3280)', 4326),
    ST_Length(ST_GeomFromText('LINESTRING(-59.1133 -37.3221, -59.1150 -37.3240, -59.1170 -37.3260, -59.1190 -37.3280)', 4326)::geography)
);

INSERT INTO senderos (nombre, descripcion, dificultad, estado_id, ruta, longitud)
VALUES (
    'Lago del Fuerte',
    'Recorrido tranquilo alrededor del lago, ideal para familias y principiantes.',
    'FACIL',
    1,
    ST_GeomFromText('LINESTRING(-59.1200 -37.3350, -59.1220 -37.3360, -59.1240 -37.3355, -59.1230 -37.3340)', 4326),
    ST_Length(ST_GeomFromText('LINESTRING(-59.1200 -37.3350, -59.1220 -37.3360, -59.1240 -37.3355, -59.1230 -37.3340)', 4326)::geography)
);

INSERT INTO senderos (nombre, descripcion, dificultad, estado_id, ruta, longitud)
VALUES (
    'Cerro La Movediza',
    'Sendero exigente hacia el cerro histórico de Tandil, con pendientes pronunciadas.',
    'DIFICIL',
    1,
    ST_GeomFromText('LINESTRING(-59.1050 -37.3150, -59.1070 -37.3170, -59.1090 -37.3200, -59.1100 -37.3230)', 4326),
    ST_Length(ST_GeomFromText('LINESTRING(-59.1050 -37.3150, -59.1070 -37.3170, -59.1090 -37.3200, -59.1100 -37.3230)', 4326)::geography)
);

-- Waypoints del Cerro El Centinela
INSERT INTO waypoints (nombre, descripcion, orden, sendero_id, ubicacion)
VALUES (
    'Inicio del sendero',
    'Punto de partida en el acceso principal.',
    1,
    1,
    ST_GeomFromText('POINT(-59.1133 -37.3221)', 4326)
);

INSERT INTO waypoints (nombre, descripcion, orden, sendero_id, ubicacion)
VALUES (
    'Mirador intermedio',
    'Primera vista panorámica de la ciudad.',
    2,
    1,
    ST_GeomFromText('POINT(-59.1170 -37.3260)', 4326)
);

INSERT INTO waypoints (nombre, descripcion, orden, sendero_id, ubicacion)
VALUES (
    'Cumbre',
    'Punto más alto del recorrido con vista 360°.',
    3,
    1,
    ST_GeomFromText('POINT(-59.1190 -37.3280)', 4326)
);