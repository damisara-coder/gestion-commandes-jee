<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<body>
<h2>Lignes de la commande n° ${commande.idCommande}</h2>
<p>Client : ${commande.client.nom} ${commande.client.prenom} · Date : ${commande.dateCommande}</p>

<form method="post" action="lignes">
    <input type="hidden" name="idCommande" value="${commande.idCommande}">
    <input type="hidden" name="idLigne" value="${ligne.idLigne}">
    Produit
    <select name="idProduit" required>
        <c:forEach var="p" items="${produits}">
            <option value="${p.idProduit}" ${p.idProduit == ligne.produit.idProduit ? 'selected' : ''}>
                ${p.designation} (${p.prix})
            </option>
        </c:forEach>
    </select>
    Quantité <input type="number" name="quantite" min="1" value="${empty ligne ? 1 : ligne.quantite}" required>
    <button type="submit">${empty ligne ? 'Ajouter' : 'Enregistrer'}</button>
</form>

<table border="1" cellpadding="5">
    <tr><th>ID</th><th>Produit</th><th>Prix unitaire</th><th>Quantité</th><th>Total ligne</th><th>Actions</th></tr>
    <c:forEach var="l" items="${lignes}">
        <tr>
            <td>${l.idLigne}</td>
            <td>${l.produit.designation}</td>
            <td><fmt:formatNumber value="${l.prixUnitaire}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td>${l.quantite}</td>
            <td><fmt:formatNumber value="${l.totalLigne}" minFractionDigits="2" maxFractionDigits="2"/></td>
            <td>
                <a href="lignes?idCommande=${commande.idCommande}&action=modifier&id=${l.idLigne}">Modifier</a>
                <a href="lignes?idCommande=${commande.idCommande}&action=supprimer&id=${l.idLigne}"
                   onclick="return confirm('Supprimer ?')">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="4" align="right"><b>Total commande</b></td>
        <td colspan="2"><b><fmt:formatNumber value="${total}" minFractionDigits="2" maxFractionDigits="2"/></b></td>
    </tr>
</table>
<a href="commandes">Retour aux commandes</a>
</body>
</html>