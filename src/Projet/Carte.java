package Projet;
//EPR

import java.util.ArrayList;

public class Carte {
    public static int NB_TERRITOIRES = 12;
    private TwoDimensionalArrayList<Territoire> territoires = new TwoDimensionalArrayList<>();

    public Carte() {
    }

    public TwoDimensionalArrayList<Territoire> getTerritoires() {
        return territoires;
    }

    public void setTerritoires(TwoDimensionalArrayList<Territoire> territoires) {
        for (int i = 0; i < NB_TERRITOIRES; i++) {
            territoires.add(new ArrayList<Territoire>());
        }
    }

    public void addTerritoire(Territoire territoire) {
        this.territoires.addToInnerArray(0,0, territoire);
    }

    @Override
    public String toString() {
        return "Carte{" +
                "territoires=" + territoires +
                '}';
    }
}
