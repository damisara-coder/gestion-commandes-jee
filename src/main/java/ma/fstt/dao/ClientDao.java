package ma.fstt.dao;

import ma.fstt.entities.Client;
import ma.fstt.service.IDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClientDao implements IDao<Client> {
    @Inject
    private DBConnection db;

    @Override
    public void ajouter(Client c) {
        String sql = "INSERT INTO Client (nom, prenom, email, telephone, adresse) VALUES (?,?,?,?,?)";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNom()); ps.setString(2, c.getPrenom());
            ps.setString(3, c.getEmail()); ps.setString(4, c.getTelephone());
            ps.setString(5, c.getAdresse());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void modifier(Client c) {
        String sql = "UPDATE Client SET nom=?, prenom=?, email=?, telephone=?, adresse=? WHERE idClient=?";
        try (Connection cn = db.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNom()); ps.setString(2, c.getPrenom());
            ps.setString(3, c.getEmail()); ps.setString(4, c.getTelephone());
            ps.setString(5, c.getAdresse()); ps.setLong(6, c.getIdClient());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void supprimer(Long id) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM Client WHERE idClient=?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Client findById(Long id) {
        try (Connection cn = db.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM Client WHERE idClient=?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Client> findAll() {
        List<Client> list = new ArrayList<>();
        try (Connection cn = db.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Client")) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    private Client map(ResultSet rs) throws SQLException {
        return new Client(rs.getLong("idClient"), rs.getString("nom"), rs.getString("prenom"),
                rs.getString("email"), rs.getString("telephone"), rs.getString("adresse"));
    }
}