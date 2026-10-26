package ma.fstt.dao;

import ma.fstt.entities.Produit;
import ma.fstt.service.IDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProduitDao implements IDao<Produit> {
    @Inject
    private DBConnection db;

    @Override
    public void ajouter(Produit p) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO Produit (designation, prix, stock) VALUES (?,?,?)")) {
            ps.setString(1, p.getDesignation()); ps.setDouble(2, p.getPrix()); ps.setInt(3, p.getStock());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void modifier(Produit p) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE Produit SET designation=?, prix=?, stock=? WHERE idProduit=?")) {
            ps.setString(1, p.getDesignation()); ps.setDouble(2, p.getPrix());
            ps.setInt(3, p.getStock()); ps.setLong(4, p.getIdProduit());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void supprimer(Long id) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM Produit WHERE idProduit=?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Produit findById(Long id) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM Produit WHERE idProduit=?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Produit> findAll() {
        List<Produit> list = new ArrayList<>();
        try (Connection cn = db.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Produit")) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    private Produit map(ResultSet rs) throws SQLException {
        return new Produit(rs.getLong("idProduit"), rs.getString("designation"),
                rs.getDouble("prix"), rs.getInt("stock"));
    }
}