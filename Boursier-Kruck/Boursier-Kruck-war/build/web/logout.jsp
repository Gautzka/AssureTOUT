<%@page import="javax.ejb.EJBException"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="Session.Salle_Interface"%>
<%@include file="inc/header.jspf" %>
<%
    if (_estConnecte) {
        session.removeAttribute("player");
    }
    try {
        InitialContext ic = new InitialContext();
        Salle_Interface si = (Salle_Interface) ic.lookup(I_ROOM_SESSION);
        si.removeJoueur(_joueur);
        si.envoyerMessage();
    } catch (EJBException ex) {
    }
    response.sendRedirect("index.jsp");
%>
<%@include file="inc/footer.jspf" %>