package ma.fstt.dao;

import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;
import ma.fstt.service.IDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CommandeDao implements IDao<Commande> {
    @Inject
    private DBConnection db;

    private static final String SELECT =
            "SELECT c.idCommande, c.dateCommande, cl.idClient, cl.nom, cl.prenom, " +
                    "(SELECT COALESCE(SUM(l.quantite * l.prixUnitaire), 0) FROM LigneCommande l " +
                    "WHERE l.idCommande = c.idCommande) AS total " +
                    "FROM Commande c JOIN Client cl ON c.idClient = cl.idClient";
    @Override
    public void ajouter(Commande c) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO Commande (dateCommande, idClient) VALUES (?,?)")) {
            ps.setDate(1, c.getDateCommande()); ps.setLong(2, c.getClient().getIdClient());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void modifier(Commande c) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE Commande SET dateCommande=?, idClient=? WHERE idCommande=?")) {
            ps.setDate(1, c.getDateCommande()); ps.setLong(2, c.getClient().getIdClient());
            ps.setLong(3, c.getIdCommande());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void supprimer(Long id) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM Commande WHERE idCommande=?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Commande findById(Long id) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT + " WHERE c.idCommande=?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Commande> findAll() {
        List<Commande> list = new ArrayList<>();
        try (Connection cn = db.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(SELECT)) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    private Commande map(ResultSet rs) throws SQLException {
        Client cl = new Client();
        cl.setIdClient(rs.getLong("idClient"));
        cl.setNom(rs.getString("nom"));
        cl.setPrenom(rs.getString("prenom"));
        Commande c = new Commande();
        c.setIdCommande(rs.getLong("idCommande"));
        c.setDateCommande(rs.getDate("dateCommande"));
        c.setClient(cl);
        c.setTotalCommande(rs.getDouble("total"));
        return c;
    }
}