package Projet;

import java.util.ArrayList;

public class Carte {
    private TwoDimensionalArrayList<Territoire> territoires = new TwoDimensionalArrayList<>();

    public Carte() {
    }

    public TwoDimensionalArrayList<Territoire> getTerritoires() {
        return territoires;
    }

    public void setTerritoires(TwoDimensionalArrayList<Territoire> territoires) {
        for (int i = 0; i < 12; i++) {
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
