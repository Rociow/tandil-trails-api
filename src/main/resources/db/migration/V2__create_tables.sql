CREATE TABLE estados_sendero (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE senderos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    longitud FLOAT8,
    dificultad VARCHAR(20),
    estado_id BIGINT,
    ruta geometry(LineString, 4326),
    FOREIGN KEY (estado_id) REFERENCES estados_sendero(id)
);

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('ROLE_USER', 'ROLE_ADMIN')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    avatar_url VARCHAR(255)
);

CREATE TABLE resenas (
    id BIGSERIAL PRIMARY KEY,
    comentario TEXT,
    puntuacion INT CHECK (puntuacion >= 1 AND puntuacion <= 5),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sendero_id BIGINT,
    usuario_id BIGINT,
    FOREIGN KEY (sendero_id) REFERENCES senderos(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE imagenes_sendero (
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    descripcion TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sendero_id BIGINT,
    usuario_id BIGINT,
    FOREIGN KEY (sendero_id) REFERENCES senderos(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE waypoints (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    orden INT NOT NULL,
    sendero_id BIGINT,
    ubicacion geometry(Point, 4326),
    FOREIGN KEY (sendero_id) REFERENCES senderos(id)
);