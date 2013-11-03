<%@page import="javax.transaction.RollbackException"%>
<%@page import="javax.ejb.EJBException"%>
<%@page import="Exception.UtilisateurInconnu"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="Session.Joueur_Interface"%>
<%@include file="inc/header.jspf" %>
<%
    if (_estConnecte) {
        response.sendRedirect("index.jsp");
    }
    String pseudo = request.getParameter("pseudo");
    String mail = request.getParameter("mail");
    String pwd = request.getParameter("pwd");
    if (pseudo == null || pseudo.equals("") || mail == null || mail.equals("") || pwd == null || pwd.equals("")) {
        if (request.getMethod().equals("POST")) { // Form is partially filled-in
%>
<p class="error">You must fill in all the scope.</p>
<%    }
} else {
    try {
        InitialContext ic = new InitialContext();
        Joueur_Interface ji = (Joueur_Interface) ic.lookup(I_PLAYER_SESSION);
        ji.enregistrement(pseudo, pwd, mail);
        session.setAttribute("info", "You are registered. You have to log you in if you want to play !");
        response.sendRedirect("index.jsp");
    } catch (EJBException e) {
        if (e.getCausedByException() instanceof RollbackException) {
%>
<p class="error">This user already exists. E-mail and login must be unique.</p>
<%    } else {
%>
<p class="error">Unknown error : <%=e.getLocalizedMessage()%>.</p>
<%
            }
        }
    }
%>
<h2 class="title">Subscribe</h2>
<form method="post" action="">
    <table>
        <tr>
            <td>Login</td>
            <td><input type="text" name="pseudo" size="25" value="<%=pseudo == null ? "" : pseudo%>"></td>
        </tr>
        <tr>
            <td>E-mail</td>
            <td><input type="text" name="mail" size="25" value="<%=mail == null ? "" : mail%>"></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="pwd" size="25" value="<%=pwd == null ? "" : pwd%>"></td>
        </tr>
    </table>
    <input type="submit" name="submit" value="Submit" />
</form>
<%@include file="inc/footer.jspf" %>