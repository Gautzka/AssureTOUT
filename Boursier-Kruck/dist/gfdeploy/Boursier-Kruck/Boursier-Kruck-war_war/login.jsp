<%@page import="Session.Salle_Interface"%>
<%@page import="javax.ejb.EJBException"%>
<%@page import="Exception.UtilisateurInconnu"%>
<%@page import="Session.Joueur_Interface"%>
<%@page import="javax.naming.InitialContext"%>
<%@include file="inc/header.jspf" %>
<%
    if (_estConnecte) {
        response.sendRedirect("index.jsp");
    }
    String pseudo = request.getParameter("pseudo");
    String pwd = request.getParameter("password");
    if (pseudo == null || pseudo.equals("") || pwd == null || pwd.equals("")) {
        if (request.getMethod().equals("POST")) { // Form is partially filled-in
%>
<p class="error">You must fill in all the scope.</p>
<%    }
} else {
    try {
        InitialContext ic = new InitialContext();
        Joueur_Interface ji = (Joueur_Interface) ic.lookup(I_PLAYER_SESSION);
        Salle_Interface si = (Salle_Interface) ic.lookup(I_ROOM_SESSION);
        Joueur joueur = ji.identification(pseudo, pwd);
        if (joueur == null) throw new UtilisateurInconnu();
        si.addJoueur(joueur);
        session.setAttribute("player", joueur);
        session.setAttribute("info", "You are now logged, have fun!");
        session.setAttribute("FirstTimeDisplay", true);
        response.sendRedirect("index.jsp");
        si.envoyerMessage();
    } catch (UtilisateurInconnu e) {
%>
<p class="error">Please check your login.</p>
<%} catch (EJBException e) {
%>
<p class="error">Connection error.</p>
<%    }
    }
%>
<h2>Connection</h2>
<form method="post" action="login.jsp">
    <table>
        <tr>
            <td>Login</td>
            <td><input type="text" name="pseudo" size="25" value=""></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" size="25" value=""></td>
        </tr>
    </table>
    <input type="submit" name="submit" value="Send">
</form>
<%@include file="inc/footer.jspf" %>