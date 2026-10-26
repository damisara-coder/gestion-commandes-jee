package ma.fstt.servlet;

import ma.fstt.dao.ProduitDao;
import ma.fstt.entities.Produit;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {
    @Inject
    private ProduitDao dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String id = req.getParameter("id");
        if ("supprimer".equals(action) && id != null) {
            dao.supprimer(Long.parseLong(id));
            resp.sendRedirect("produits");
            return;
        }
        if ("modifier".equals(action) && id != null) {
            req.setAttribute("produit", dao.findById(Long.parseLong(id)));
        }
        req.setAttribute("produits", dao.findAll());
        req.getRequestDispatcher("/WEB-INF/produits.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Produit p = new Produit();
        p.setDesignation(req.getParameter("designation"));
        p.setPrix(Double.parseDouble(req.getParameter("prix")));
        p.setStock(Integer.parseInt(req.getParameter("stock")));
        String id = req.getParameter("idProduit");
        if (id == null || id.isEmpty()) {
            dao.ajouter(p);
        } else {
            p.setIdProduit(Long.parseLong(id));
            dao.modifier(p);
        }
        resp.sendRedirect("produits");
    }
}