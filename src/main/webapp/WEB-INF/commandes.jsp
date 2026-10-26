<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>Espace commande</h2>

<form method="post" action="commandes">
    <input type="hidden" name="idCommande" value="${commande.idCommande}">
    Date <input type="date" name="dateCommande" value="${commande.dateCommande}" required>
    Client
    <select name="idClient" required>
        <c:forEach var="cl" items="${clients}">
            <option value="${cl.idClient}" ${cl.idClient == commande.client.idClient ? 'selected' : ''}>
                ${cl.nom} ${cl.prenom}
            </option>
        </c:forEach>
    </select>
    <button type="submit">${empty commande ? 'Ajouter' : 'Enregistrer'}</button>
</form>

<table border="1" cellpadding="5">
    <tr><th>ID</th><th>Date</th><th>Client</th><th>Total</th><th>Actions</th></tr>
    <c:forEach var="cmd" items="${commandes}">
        <tr>
            <td>${cmd.idCommande}</td><td>${cmd.dateCommande}</td>
            <td>${cmd.client.nom} ${cmd.client.prenom}</td>
            <td>${cmd.totalCommande}</td>
            <td>
                <a href="lignes?idCommande=${cmd.idCommande}">Lignes</a>
                <a href="commandes?action=modifier&id=${cmd.idCommande}">Modifier</a>
                <a href="commandes?action=supprimer&id=${cmd.idCommande}"
                   onclick="return confirm('Supprimer ?')">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/">Accueil</a>
</body>
</html>