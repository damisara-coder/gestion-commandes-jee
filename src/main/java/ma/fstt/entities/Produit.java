package ma.fstt.entities;

public class Produit {
    private Long idProduit;
    private String designation;
    private double prix;
    private int stock;

    public Produit() {}
    public Produit(Long idProduit, String designation, double prix, int stock) {
        this.idProduit = idProduit; this.designation = designation; this.prix = prix; this.stock = stock;
    }
    public Long getIdProduit() { return idProduit; }
    public void setIdProduit(Long idProduit) { this.idProduit = idProduit; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}