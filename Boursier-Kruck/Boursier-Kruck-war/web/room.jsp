<%@page import="Salle.Jeu"%>
<%@page import="java.util.Enumeration"%>
<%@page import="javax.jms.TextMessage"%>
<%@page import="javax.jms.Message"%>
<%@page import="javax.jms.MessageConsumer"%>
<%@page import="javax.jms.Session"%>
<%@page import="javax.jms.Connection"%>
<%@page import="javax.jms.Topic"%>
<%@page import="javax.jms.ConnectionFactory"%>
<%@page import="java.util.Set"%>
<%@page import="Session.Salle_Interface"%>
<%@page import="javax.naming.InitialContext"%>
<%@include file="inc/header.jspf" %>
<%
    if (!_estConnecte) {
        response.sendRedirect("index.jsp");
    }
%>
<h2>Game room</h2>
<br />
<%
    try {
        InitialContext ic = new InitialContext();
        Salle_Interface siSession = (Salle_Interface) ic.lookup(I_ROOM_SESSION);
        
        for (Jeu jeu : siSession.getJeux()) {
%>

<a href="<%="jeu"+jeu.getId()%>.jsp?game=<%=jeu.getId()%>">Play to <%=jeu.getTitre()%></a>
<br />

<%
        }
    } catch (Exception e) {
    }
%>
<br />
<br />
<hr>
<br />
<%// Si le paramètre redirect est donné dans l'URL, ça veut dire que l'on charge
// la salle de jeux après une redirection
    Boolean firstTime;
    Set<Joueur> joueurs = null;
    if (request.getParameter("redirect") == null || session.getAttribute("FirstTimeDisplay") == null) {
        firstTime = true;
        session.setAttribute("FirstTimeDisplay", firstTime);
    } else {
        firstTime = (Boolean) session.getAttribute("FirstTimeDisplay");
    }
    try {
        InitialContext ic = new InitialContext();
        Object o = ic.lookup(I_ROOM_SESSION);
        Salle_Interface si = (Salle_Interface) o;
        joueurs = si.getJoueurs();

        if (firstTime) {
            firstTime = false;
            session.setAttribute("FirstTimeDisplay", false);
        } else {
            ConnectionFactory connectionFactory = (ConnectionFactory) ic.lookup("jms/ConnectionFactory");
            Topic topic = (Topic) ic.lookup("jms/TopicUsers");
            Connection connection = connectionFactory.createConnection();
            Session jmsSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = jmsSession.createConsumer(topic);
            connection.start();
            Message msg = consumer.receive();
            joueurs = si.getJoueurs();
            connection.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println(e);
    }
    if (joueurs == null || joueurs.isEmpty()) {
%>
There is no player in the room.
<% } else {%>
<table border="1" cellpadding="10">
    <tr>
        <th>Player</th>
        <th>Informations</th>
    </tr>

    <% for (Joueur joueur : joueurs) {%>
    <tr>
        <td><%=joueur.getPseudo()%></td>
        <td><a href="profile.jsp?id=<%=joueur.getId()%>">See the profile</a></td>
    </tr>
    <% }%>
</table>

<%
    }
%>
<%@include file="inc/footer.jspf" %>
<!--<script type="text/javascript">
    if ( window.location.href.indexOf("?") > -1 ) { // On est déjà en attente d'un rafraîchissement
        window.location.reload();
    } else {
        document.location.href = "room.jsp?redirect=1";
    }
</script>-->
