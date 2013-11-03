
package Salle;

import java.util.Random;

public class Jeu_PFC {
    
    public enum Choix { ROCK, PAPER, SCISSORS };
    
    public static int getGagnant(Choix j1, Choix j2) {
        if (j1 == j2)                   return 0;
        else if (j1 == Choix.ROCK)      return j2 == Choix.PAPER ? 2 : 1;
        else if (j1 == Choix.PAPER)     return j2 == Choix.SCISSORS ? 2 : 1;
        else if (j1 == Choix.SCISSORS)  return j2 == Choix.ROCK ? 2 : 1;
        else                            return 0;
    }
    
    public static Choix getChoixAleatoire() {
        return Choix.values()[(new Random()).nextInt(Choix.values().length)];
    }
}
