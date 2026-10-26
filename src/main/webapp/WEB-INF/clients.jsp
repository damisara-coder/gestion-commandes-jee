<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>Espace client</h2>

<form method="post" action="clients">
    <input type="hidden" name="idClient" value="${client.idClient}">
    Nom <input name="nom" value="${client.nom}" required>
    Prénom <input name="prenom" value="${client.prenom}" required>
    Email <input name="email" value="${client.email}" required>
    Téléphone <input name="telephone" value="${client.telephone}" required>
    Adresse <input name="adresse" value="${client.adresse}" required>
    <button type="submit">${empty client ? 'Ajouter' : 'Enregistrer'}</button>
</form>

<table border="1" cellpadding="5">
    <tr><th>ID</th><th>Nom</th><th>Prénom</th><th>Email</th><th>Téléphone</th><th>Adresse</th><th>Actions</th></tr>
    <c:forEach var="c" items="${clients}">
        <tr>
            <td>${c.idClient}</td><td>${c.nom}</td><td>${c.prenom}</td>
            <td>${c.email}</td><td>${c.telephone}</td><td>${c.adresse}</td>
            <td>
                <a href="clients?action=modifier&id=${c.idClient}">Modifier</a>
                <a href="clients?action=supprimer&id=${c.idClient}"
                   onclick="return confirm('Supprimer ?')">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>