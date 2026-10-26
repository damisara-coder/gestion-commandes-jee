package ma.fstt.servlet;
import ma.fstt.dao.CommandeDao;
import ma.fstt.dao.LigneCommandeDao;
import ma.fstt.dao.ProduitDao;
import ma.fstt.entities.Commande;
import ma.fstt.entities.LigneCommande;
import ma.fstt.entities.Produit;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/lignes")
public class LigneCommandeServlet extends HttpServlet {
    @Inject
    private LigneCommandeDao dao;
    @Inject
    private CommandeDao commandeDao;
    @Inject
    private ProduitDao produitDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idCmd = req.getParameter("idCommande");
        if (idCmd == null) {
            resp.sendRedirect("commandes");
            return;
        }
        Long idCommande = Long.parseLong(idCmd);
        String action = req.getParameter("action");
        String id = req.getParameter("id");

        if ("supprimer".equals(action) && id != null) {
            dao.supprimer(Long.parseLong(id));
            resp.sendRedirect("lignes?idCommande=" + idCommande);
            return;
        }
        if ("modifier".equals(action) && id != null) {
            req.setAttribute("ligne", dao.findById(Long.parseLong(id)));
        }

        List<LigneCommande> lignes = dao.findByCommande(idCommande);
        double total = 0;
        for (LigneCommande l : lignes) total += l.getTotalLigne();

        req.setAttribute("commande", commandeDao.findById(idCommande));
        req.setAttribute("lignes", lignes);
        req.setAttribute("produits", produitDao.findAll());
        req.setAttribute("total", total);
        req.getRequestDispatcher("/WEB-INF/lignes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long idCommande = Long.parseLong(req.getParameter("idCommande"));
        Produit p = produitDao.findById(Long.parseLong(req.getParameter("idProduit")));

        Commande cmd = new Commande();
        cmd.setIdCommande(idCommande);

        LigneCommande l = new LigneCommande();
        l.setCommande(cmd);
        l.setProduit(p);
        l.setQuantite(Integer.parseInt(req.getParameter("quantite")));
        l.setPrixUnitaire(p.getPrix());

        String id = req.getParameter("idLigne");
        if (id == null || id.isEmpty()) {
            dao.ajouter(l);
        } else {
            l.setIdLigne(Long.parseLong(id));
            dao.modifier(l);
        }
        resp.sendRedirect("lignes?idCommande=" + idCommande);
    }
}