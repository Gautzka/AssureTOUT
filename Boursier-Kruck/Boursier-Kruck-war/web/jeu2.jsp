<%@page import="javax.ejb.EJBException"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="Session.Jeu_Interface"%>
<%@page import="Session.Salle_Interface"%>
<%@page import="Salle.Jeu_PouF"%>
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
            ji.setId(2);
            ji.nouveauJeu();
            session.setAttribute("session.IGameSession", ji);
        } catch (EJBException ex) {
        }
    } else {
        ji = (Jeu_Interface) session.getAttribute("session.IGameSession");
        if(ji.getId() != 2){
            try {
            // First time we come on this page
            InitialContext ic = new InitialContext();
            ji = (Jeu_Interface) ic.lookup(I_GAME_SESSION);
            ji.setJoueur(_joueur);
            ji.setJeu(jeu);
            ji.setId(2);
            ji.nouveauJeu();
            session.setAttribute("session.IGameSession", ji);
            } catch (EJBException ex) {
            }
        }
    }

    Jeu_PouF.ChoixPouF choixJoueur = null;
    if (request.getParameter("pile") != null) {
        choixJoueur = Jeu_PouF.ChoixPouF.HEAD;
    } else if (request.getParameter("face") != null) {
        choixJoueur = Jeu_PouF.ChoixPouF.TAIL;
    }
    int winner = -1;
    if (choixJoueur != null) {
        winner = ji.jouer(choixJoueur, 2);
    }
%>
<h2>Heads or Tails?</h2>
<p>The computer tosses a coin, and you have to guess if it is heads or tail!</p>
<% if (!ji.partieTerminee()) {%>
<form method="POST" action="jeu2.jsp" id="game">
    <input type="submit" name="pile" class="boutonjeu2" id="pile" value="Pile" />
    <input type="submit" name="face" class="boutonjeu2" id="face" value="Face" />
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
        if (winner == 1) {
        %>Victory, you have chosen what computer tossed : <%=ji.getChoixJoueur().toString()%><%
        } else if (winner == 2) {
        %>Defeat, you have chosen <%=ji.getChoixJoueur().toString()%> and the computer tossed <%=ji.getChoixOrdi().toString()%><%
        }
        %>
    </p>
</fieldset>

<%@include file="inc/footer.jspf" %>