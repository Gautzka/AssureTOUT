<%@page import="javax.ejb.EJBException"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="Session.Jeu_Interface"%>
<%@page import="Session.Salle_Interface"%>
<%@page import="Salle.Jeu_PFC"%>
<%@include file="inc/header.jspf" %>
<%
    boolean game = true;
    if (!_estConnecte) {
        response.sendRedirect("index.jsp");
    }

    String jeu = request.getParameter("game");
    if (jeu == null) {
        response.sendRedirect("index.jsp");
    }
    Jeu_Interface ji = null;
    //String joue = request.getParameter("play");
    if (/*joue != null ||*/ session.getAttribute("session.IGameSession") == null) {
        try {
            // First time we come on this page
            InitialContext ic = new InitialContext();
            ji = (Jeu_Interface) ic.lookup(I_GAME_SESSION);
            ji.setJoueur(_joueur);
            ji.setJeu(jeu);
            ji.setId(1);
            ji.nouveauJeu();
            session.setAttribute("session.IGameSession", ji);
        } catch (EJBException ex) {
        }
    } else {
        ji = (Jeu_Interface) session.getAttribute("session.IGameSession");
        if(ji.getId() != 1){
            try {
            // First time we come on this page
            InitialContext ic = new InitialContext();
            ji = (Jeu_Interface) ic.lookup(I_GAME_SESSION);
            ji.setJoueur(_joueur);
            ji.setJeu(jeu);
            ji.setId(1);
            ji.nouveauJeu();
            session.setAttribute("session.IGameSession", ji);
            } catch (EJBException ex) {
            }
        }
    }

    Jeu_PFC.Choix choixJoueur = null;
    if (request.getParameter("pierre") != null) {
        choixJoueur = Jeu_PFC.Choix.ROCK;
    } else if (request.getParameter("feuille") != null) {
        choixJoueur = Jeu_PFC.Choix.PAPER;
    } else if (request.getParameter("ciseaux") != null) {
        choixJoueur = Jeu_PFC.Choix.SCISSORS;
    }
    int winner = -1;
    if (choixJoueur != null) {
        winner = ji.jouer(choixJoueur, 1);
    }
%>
<h2>Rock Paper Scissors</h2>
<p>Both players have to choose one of the three options and show their choice simultaneously.</p>
<p>Rock beats scissors, scissors beat paper and paper beats rock. It is considered even if they both pick the same.</p>
<% if (!ji.partieTerminee()) {%>

<form method="POST" action="jeu1.jsp" id="game">
    <input type="submit" name="pierre" class="boutonjeu" id="pierre" value="Pierre" />
    <input type="submit" name="feuille" class="boutonjeu" id="feuille" value="Feuille" />
    <input type="submit" name="ciseaux" class="boutonjeu" id="ciseaux" value="Ciseaux" />
    <input type="hidden" name="game" value="<%=jeu%>" /> 
</form>
<% } else {%>
<p>The game is over</p>
<% if (ji.joueurGagne()) {%>
<b>Congratulations, you won!</b>
<% } else {%>
<b>You are the loser!</b>
<% }
session.removeAttribute("session.IGameSession");%>
<br />
<a href="room.jsp">Back to the room</a>
<%}%>
<fieldset class="score"><legend>Score</legend>
    <div style="width:350px; margin:auto;">
        <p class="score"><%=ji.getScoreJoueur()%><span class="playername">You</span></p>
        <p class="score">-</p>
        <p class="score"><%=ji.getScoreOrdi()%><span class="playername">Computer</span></p>
    </div>
</fieldset>
<br class="clear"> </br>
<fieldset><legend>Last move</legend>
    <p><%
        if (winner == 0) {
        %>Even, both of you played <%=ji.getChoixJoueur().toString()%><%
        } else if (winner == 1) {
        %>Victory, you won with <%=ji.getChoixJoueur().toString()%> against <%=ji.getChoixOrdi().toString()%><%
        } else if (winner == 2) {
        %>Defeat, you loose with <%=ji.getChoixJoueur().toString()%> against <%=ji.getChoixOrdi().toString()%><%
        }
        %>
    </p>
</fieldset>


<%@include file="inc/footer.jspf" %>