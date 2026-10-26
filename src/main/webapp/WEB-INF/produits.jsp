<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>Espace produit</h2>

<form method="post" action="produits">
    <input type="hidden" name="idProduit" value="${produit.idProduit}">
    Désignation <input name="designation" value="${produit.designation}" required>
    Prix <input name="prix" type="number" step="0.01" value="${produit.prix}" required>
    Stock <input name="stock" type="number" value="${produit.stock}" required>
    <button type="submit">${empty produit ? 'Ajouter' : 'Enregistrer'}</button>
</form>

<table border="1" cellpadding="5">
    <tr><th>ID</th><th>Désignation</th><th>Prix</th><th>Stock</th><th>Actions</th></tr>
    <c:forEach var="p" items="${produits}">
        <tr>
            <td>${p.idProduit}</td><td>${p.designation}</td><td>${p.prix}</td><td>${p.stock}</td>
            <td>
                <a href="produits?action=modifier&id=${p.idProduit}">Modifier</a>
                <a href="produits?action=supprimer&id=${p.idProduit}"
                   onclick="return confirm('Supprimer ?')">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/">Accueil</a>
</body>
</html>