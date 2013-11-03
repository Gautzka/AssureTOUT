<%@page import="java.util.List"%>
<%@page import="Salle.Jeu"%>
<%@page import="java.util.Set"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.ejb.EJBException"%>
<%@page import="Session.Salle_Interface"%>
<%@include file="inc/header.jspf" %>
<div id="banner"><img src="images/pics01.jpg" width="450" height="225" alt="" /></div>
<h2 class="title">Welcome</h2>
<p>
    Welcome aboard the games server. Subscribe and join the community.
    Once connected, you will be able to join a room and launch a game.
</p>
<h3>Available games</h3>
<p>The website has just opened so only two games are available at the moment.</p>
    <%
        try {
            // First time we come on this page
            InitialContext ic = new InitialContext();
            Salle_Interface si = (Salle_Interface) ic.lookup(I_ROOM_SESSION);
            /*if(executeOneTime){
                ir.createGame();
                session.setAttribute("executed", true);
            }*/
            List<Jeu> jeux = si.getJeux();
            for (Jeu jeu : jeux) {
    %>
<h4><%=jeu.getTitre()%></h4>
<p><%=jeu.getDescription().replace("\n", "<br />")%></p>
<%        }
    } catch (EJBException ex) {
    }
%>
<%@include file="inc/footer.jspf" %>