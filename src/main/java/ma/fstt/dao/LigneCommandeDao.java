package ma.fstt.dao;

import ma.fstt.entities.Commande;
import ma.fstt.entities.LigneCommande;
import ma.fstt.entities.Produit;
import ma.fstt.service.IDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LigneCommandeDao implements IDao<LigneCommande> {
    @Inject
    private DBConnection db;

    private static final String SELECT =
            "SELECT l.idLigne, l.idCommande, l.quantite, l.prixUnitaire, p.idProduit, p.designation " +
                    "FROM LigneCommande l JOIN Produit p ON l.idProduit = p.idProduit";

    @Override
    public void ajouter(LigneCommande l) {
        String sql = "INSERT INTO LigneCommande (idCommande, idProduit, quantite, prixUnitaire) VALUES (?,?,?,?)";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, l.getCommande().getIdCommande());
            ps.setLong(2, l.getProduit().getIdProduit());
            ps.setInt(3, l.getQuantite());
            ps.setDouble(4, l.getPrixUnitaire());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void modifier(LigneCommande l) {
        String sql = "UPDATE LigneCommande SET idProduit=?, quantite=?, prixUnitaire=? WHERE idLigne=?";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, l.getProduit().getIdProduit());
            ps.setInt(2, l.getQuantite());
            ps.setDouble(3, l.getPrixUnitaire());
            ps.setLong(4, l.getIdLigne());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void supprimer(Long id) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM LigneCommande WHERE idLigne=?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public LigneCommande findById(Long id) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT + " WHERE l.idLigne=?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<LigneCommande> findAll() {
        List<LigneCommande> list = new ArrayList<>();
        try (Connection cn = db.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(SELECT)) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public List<LigneCommande> findByCommande(Long idCommande) {
        List<LigneCommande> list = new ArrayList<>();
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT + " WHERE l.idCommande=? ORDER BY l.idLigne")) {
            ps.setLong(1, idCommande);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    private LigneCommande map(ResultSet rs) throws SQLException {
        Produit p = new Produit();
        p.setIdProduit(rs.getLong("idProduit"));
        p.setDesignation(rs.getString("designation"));
        Commande c = new Commande();
        c.setIdCommande(rs.getLong("idCommande"));
        LigneCommande l = new LigneCommande();
        l.setIdLigne(rs.getLong("idLigne"));
        l.setQuantite(rs.getInt("quantite"));
        l.setPrixUnitaire(rs.getDouble("prixUnitaire"));
        l.setProduit(p);
        l.setCommande(c);
        return l;
    }
}