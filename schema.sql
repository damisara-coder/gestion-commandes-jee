-- =====================================================
-- Atelier JEE : Gestion des commandes
-- Base de données MySQL / MariaDB (XAMPP)
-- =====================================================

CREATE DATABASE IF NOT EXISTS gestion_commandes
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_commandes;

-- -----------------------------------------------------
-- Table Client
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Client (
                                      idClient  BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      nom       VARCHAR(100) NOT NULL,
    prenom    VARCHAR(100) NOT NULL,
    email     VARCHAR(100) NOT NULL UNIQUE,
    telephone VARCHAR(20)  NOT NULL,
    adresse   VARCHAR(255) NOT NULL
    );

-- -----------------------------------------------------
-- Table Produit
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Produit (
                                       idProduit   BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       designation VARCHAR(100)  NOT NULL,
    prix        DECIMAL(10,2) NOT NULL,
    stock       INT           NOT NULL DEFAULT 0
    );

-- -----------------------------------------------------
-- Table Commande (un client passe 0..n commandes)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Commande (
                                        idCommande   BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        dateCommande DATE   NOT NULL,
                                        idClient     BIGINT NOT NULL,
                                        CONSTRAINT fk_cmd_client
                                        FOREIGN KEY (idClient) REFERENCES Client (idClient)
    );

-- -----------------------------------------------------
-- Table LigneCommande
-- Composition : supprimer une commande supprime ses lignes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS LigneCommande (
                                             idLigne      BIGINT PRIMARY KEY AUTO_INCREMENT,
                                             idCommande   BIGINT        NOT NULL,
                                             idProduit    BIGINT        NOT NULL,
                                             quantite     INT           NOT NULL,
                                             prixUnitaire DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_ligne_commande
    FOREIGN KEY (idCommande) REFERENCES Commande (idCommande)
    ON DELETE CASCADE,
    CONSTRAINT fk_ligne_produit
    FOREIGN KEY (idProduit) REFERENCES Produit (idProduit)
    );

-- -----------------------------------------------------
-- Données de test (facultatif : retirer les "-- " pour les utiliser)
-- -----------------------------------------------------
-- INSERT INTO Client (nom, prenom, email, telephone, adresse)
--   VALUES ('Alaoui', 'Sara', 'sara@test.ma', '0600000000', 'Tanger');
-- INSERT INTO Produit (designation, prix, stock) VALUES ('Clavier', 150.00, 20);
-- INSERT INTO Commande (dateCommande, idClient) VALUES (CURDATE(), 1);
-- INSERT INTO LigneCommande (idCommande, idProduit, quantite, prixUnitaire)
--   VALUES (1, 1, 2, 150.00);