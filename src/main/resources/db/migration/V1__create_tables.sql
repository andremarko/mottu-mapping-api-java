-- Tabela de modelos
CREATE TABLE tb_model (
model_id INT IDENTITY(1,1) PRIMARY KEY,
model_name NVARCHAR(255) NOT NULL
);

-- Tabela de pátios
CREATE TABLE tb_moto_yard (
yard_id INT IDENTITY(1,1) PRIMARY KEY,
branch_name NVARCHAR(255) NOT NULL,
address NVARCHAR(255) NOT NULL,
city NVARCHAR(100) NOT NULL,
state NVARCHAR(100) NOT NULL,
capacity INT NOT NULL
);

-- Tabela de setores
CREATE TABLE tb_sector (
sector_id INT IDENTITY(1,1) PRIMARY KEY,
yard_id INT NOT NULL,
name NVARCHAR(100) NOT NULL,
description NVARCHAR(255) NULL,
color_rgb NVARCHAR(50) NOT NULL,
color_name NVARCHAR(50) NOT NULL,
CONSTRAINT fk_sector_yard FOREIGN KEY (yard_id) REFERENCES tb_moto_yard(yard_id)
);

-- Tabela de motos
CREATE TABLE tb_motorcycle (
motorcycle_id INT IDENTITY(1,1) PRIMARY KEY,
plate NVARCHAR(50) NOT NULL UNIQUE,
coordinates NVARCHAR(255) NOT NULL,
model_id INT NULL,
sector_id INT NULL,
CONSTRAINT fk_motorcycle_model FOREIGN KEY (model_id) REFERENCES tb_model(model_id),
CONSTRAINT fk_motorcycle_sector FOREIGN KEY (sector_id) REFERENCES tb_sector(sector_id)
);

-- Tabela de usuários
CREATE TABLE tb_user (
user_id INT IDENTITY(1,1) PRIMARY KEY,
username NVARCHAR(100) NOT NULL UNIQUE,
password NVARCHAR(255) NOT NULL,
role NVARCHAR(50) NOT NULL
);
