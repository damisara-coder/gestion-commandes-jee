package ma.fstt.servlet;

import ma.fstt.dao.ClientDao;
import ma.fstt.entities.Client;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/clients")
public class ClientServlet extends HttpServlet {
    @Inject
    private ClientDao dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String id = req.getParameter("id");
        if ("supprimer".equals(action) && id != null) {
            dao.supprimer(Long.parseLong(id));
            resp.sendRedirect("clients");
            return;
        }
        if ("modifier".equals(action) && id != null) {
            req.setAttribute("client", dao.findById(Long.parseLong(id)));
        }
        req.setAttribute("clients", dao.findAll());
        req.getRequestDispatcher("/WEB-INF/clients.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Client c = new Client();
        c.setNom(req.getParameter("nom"));
        c.setPrenom(req.getParameter("prenom"));
        c.setEmail(req.getParameter("email"));
        c.setTelephone(req.getParameter("telephone"));
        c.setAdresse(req.getParameter("adresse"));
        String id = req.getParameter("idClient");
        if (id == null || id.isEmpty()) {
            dao.ajouter(c);
        } else {
            c.setIdClient(Long.parseLong(id));
            dao.modifier(c);
        }
        resp.sendRedirect("clients");
    }
}