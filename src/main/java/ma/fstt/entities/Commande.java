package ma.fstt.entities;

import java.sql.Date;

public class Commande {
    private Long idCommande;
    private Date dateCommande;
    private Client client;

    public Commande() {}
    public Long getIdCommande() { return idCommande; }
    public void setIdCommande(Long idCommande) { this.idCommande = idCommande; }
    public Date getDateCommande() { return dateCommande; }
    public void setDateCommande(Date dateCommande) { this.dateCommande = dateCommande; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    private double totalCommande;

    public double getTotalCommande() { return totalCommande; }
    public void setTotalCommande(double totalCommande) { this.totalCommande = totalCommande; }}