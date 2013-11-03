
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="Salle.Joueur" %>
<%@ page import="Session.*" %>
<%@ page import='java.util.*' %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Display</title>
    </head>
    <body>
        <%
            try{
                InitialContext ic = new InitialContext();
                Object o = ic.lookup("java:global/Boursier-Kruck/Boursier-Kruck-ejb/Joueur_Session!Session.Joueur_Interface");
                Joueur_Interface ji = (Joueur_Interface) o;

                List joueurs = ji.trouverJoueurs();

                for(int i = 0; i < joueurs.size(); i++) {
                    Joueur p = (Joueur) joueurs.get(i);
                    Long id = p.getId();
        %>
        <%=p.getPseudo()%> - <%=p.getStatus()%><br>
        <%
                }
            } catch(Exception e) {
                out.println(e);
            }
        %>
    </body>
</html>
