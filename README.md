# Gestion des commandes : Atelier JEE (MVC et standards JEE)

Application web de gestion des commandes de plusieurs clients, réalisée dans le cadre du module JEE (Cycle Ingénieur LSI, FST Tanger, Pr. ELAACHAK Lotfi).

## Fonctionnalités

Trois espaces avec les opérations CRUD (lister, ajouter, modifier, supprimer), plus la gestion des lignes de chaque commande :

| Espace | URL |
|---|---|
| Client | `/clients` |
| Produit | `/produits` |
| Commande | `/commandes` (avec le total de chaque commande) |
| Lignes d'une commande | `/lignes?idCommande=...` (lien « Lignes » sur chaque commande) |

## Technologies

- Java 17, Servlet 4.0, JSP 2.3, JSTL 1.2
- CDI (Weld) : `@Inject`, `@ApplicationScoped`
- Maven (packaging `war`), Tomcat 9
- MySQL (XAMPP), JDBC (`mysql-connector-j`)

## Structure du projet

```
src/main/java/ma/fstt/
├── entities/   Client, Produit, Commande, LigneCommande
├── service/    IDao<T> (interface utilisée par les DAO)
├── dao/        DBConnection, ClientDao, ProduitDao, CommandeDao, LigneCommandeDao
└── servlet/    ClientServlet, ProduitServlet, CommandeServlet, LigneCommandeServlet
src/main/webapp/
├── index.jsp
└── WEB-INF/    clients.jsp, produits.jsp, commandes.jsp, lignes.jsp, web.xml, beans.xml
sql/schema.sql  Script de création de la base
```

## Installation

1. Démarrer **MySQL** (par exemple via XAMPP).
2. Exécuter `sql/schema.sql` dans phpMyAdmin ou avec `mysql -u root -p < sql/schema.sql`.
3. Si besoin, adapter l'URL et les identifiants dans `ma.fstt.dao.DBConnection` (par défaut : `root`, sans mot de passe).
4. Ouvrir le projet dans IntelliJ IDEA et configurer **Tomcat 9** (plugin Smart Tomcat), contexte `/gestion-commandes`.
5. Lancer, puis ouvrir `http://localhost:8080/gestion-commandes/` (adapter le port si 8080 est déjà utilisé).

## Modèle de données

- Un **client** passe 0..n **commandes**.
- Une **commande** est composée de 1..n **lignes de commande** (composition : `ON DELETE CASCADE`).
- Une **ligne de commande** concerne un **produit**.

## Limites

- Supprimer un client ou un produit déjà utilisé dans une commande est refusé par les clés étrangères (erreur 500, sans message dédié).
- Les identifiants MySQL sont écrits en dur dans `DBConnection`.
