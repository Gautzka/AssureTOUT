<%@page import="Salle.JeuxJoueur"%>
<%@page import="javax.ejb.EJBException"%>
<%@page import="Session.Joueur_Interface"%>
<%@page import="javax.naming.InitialContext"%>
<%@include file="inc/header.jspf" %>
<% if (!_estConnecte) {
        response.sendRedirect("index.jsp");
    }
    Long id = null;
    try {
        String idString = request.getParameter("id");
        if (idString != null) {
            id = Long.parseLong(idString);
        }
    } catch (NumberFormatException ex) {
    }
    if (id == null) {
        response.sendRedirect("index.jsp");
    }
    Joueur joueur = null;
    try {
        InitialContext ic = new InitialContext();
        Joueur_Interface ji = (Joueur_Interface) ic.lookup(I_PLAYER_SESSION);
        joueur = ji.chercherJoueurs(id);
        if (joueur == null) { // Joueur inconnu
            response.sendRedirect("index.jsp");
        }
    } catch (EJBException ex) {
        out.println(ex);
    }
%>
<h2 class="title">Player profile</h2>
<ul>
    <li>Login: <%=joueur.getPseudo()%></li>
    <li>Email: <a href="mailto:<%=joueur.getEmail()%>"><%=joueur.getEmail()%></a></li>
    <li>Games:
        <ul>
    <% for (JeuxJoueur jeuJoueur : joueur.getJeuxAssocies()) { %>
    <li><%=jeuJoueur.getJeu().getTitre() %> : <%=jeuJoueur.getPartiesGagnees()%> victories / <%=jeuJoueur.getPartiesJouees()%> games played</li>
    <% } %>
        </ul>
    </li>
</ul>
<%@include file="inc/footer.jspf" %>