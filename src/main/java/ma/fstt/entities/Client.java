package ma.fstt.entities;

public class Client {
    private Long idClient;
    private String nom, prenom, email, telephone, adresse;

    public Client() {}
    public Client(Long idClient, String nom, String prenom, String email, String telephone, String adresse) {
        this.idClient = idClient; this.nom = nom; this.prenom = prenom;
        this.email = email; this.telephone = telephone; this.adresse = adresse;
    }
    public Long getIdClient() { return idClient; }
    public void setIdClient(Long idClient) { this.idClient = idClient; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
}