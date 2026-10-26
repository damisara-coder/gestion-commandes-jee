package ma.fstt.servlet;

import ma.fstt.dao.ClientDao;
import ma.fstt.dao.CommandeDao;
import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/commandes")
public class CommandeServlet extends HttpServlet {
    @Inject
    private CommandeDao dao;
    @Inject
    private ClientDao clientDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String id = req.getParameter("id");
        if ("supprimer".equals(action) && id != null) {
            dao.supprimer(Long.parseLong(id));
            resp.sendRedirect("commandes");
            return;
        }
        if ("modifier".equals(action) && id != null) {
            req.setAttribute("commande", dao.findById(Long.parseLong(id)));
        }
        req.setAttribute("commandes", dao.findAll());
        req.setAttribute("clients", clientDao.findAll());
        req.getRequestDispatcher("/WEB-INF/commandes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Commande c = new Commande();
        c.setDateCommande(Date.valueOf(req.getParameter("dateCommande")));
        Client cl = new Client();
        cl.setIdClient(Long.parseLong(req.getParameter("idClient")));
        c.setClient(cl);
        String id = req.getParameter("idCommande");
        if (id == null || id.isEmpty()) {
            dao.ajouter(c);
        } else {
            c.setIdCommande(Long.parseLong(id));
            dao.modifier(c);
        }
        resp.sendRedirect("commandes");
    }
}