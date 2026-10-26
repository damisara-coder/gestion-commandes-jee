package ma.fstt.entities;

public class LigneCommande {
    private Long idLigne;
    private int quantite;
    private double prixUnitaire;
    private Commande commande;
    private Produit produit;

    public LigneCommande() {}
    public double getTotalLigne() { return quantite * prixUnitaire; }
    public Long getIdLigne() { return idLigne; }
    public void setIdLigne(Long idLigne) { this.idLigne = idLigne; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
}