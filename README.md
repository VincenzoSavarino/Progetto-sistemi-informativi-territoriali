# Progetto-sistemi-informativi-territoriali

# Creazione DataBase
CREATE DATABASE gis2021; 
\connect gis2021;
CREATE EXTENSION postgis;
# Create table utenti
create table utenti (email varchar(255) PRIMARY KEY, 
nome varchar(255), 
cognome varchar(255), 
codicefiscale varchar(255) UNIQUE, 
password varchar(255), 
telefono varchar(255) UNIQUE);
# Create table segnalazioni
create table segnalazioni (id SERIAL PRIMARY KEY,
email varchar(255),
lat varchar(255),
lon varchar(255),
dim varchar(255),
descrizione varchar(255),
geometry GEOMETRY,
data DATE,
Foto BOOLEAN DEFAULT FALSE
);

# Modifica file config 
È necessario modificare il file configDB.php
