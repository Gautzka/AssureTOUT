
package Salle;

import java.util.Random;

public class Jeu_PouF {
    
    public enum ChoixPouF { HEAD, TAIL };
    
    public static int getGagnant(ChoixPouF j1, ChoixPouF j2) {
        if (j1 == j2) return 1;
        else          return 2;
    }
    
    public static ChoixPouF getChoixAleatoire() {
        return ChoixPouF.values()[(new Random()).nextInt(ChoixPouF.values().length)];
    }
}
