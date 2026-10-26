package ma.fstt;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnexion {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/gestion_commandes";
        try (Connection c = DriverManager.getConnection(url, "root", "")) {
            System.out.println("Connexion OK : " + c.getCatalog());
        }
    }
}