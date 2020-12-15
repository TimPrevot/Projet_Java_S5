package Projet;

import java.util.ArrayList;
import java.util.List;

public class Carte {
    private TwoDimensionalArrayList<Territoire> territoires = new TwoDimensionalArrayList<>();

    public Carte() {
    }

    public List<List<Territoire>> getTerritoires() {
        return territoires;
    }

    public void setTerritoires(List<List<Territoire>> territoires) {
        for (int i = 0; i < 12; i++) {
            territoires.add(new ArrayList<Territoire>());
        }
    }

    public void addTerritoire(Territoire territoire) {
        this.territoires.get(0).add(territoire);
    }

    @Override
    public String toString() {
        return "Carte{" +
                "territoires=" + territoires +
                '}';
    }
}
