package Projet;

import java.util.ArrayList;
import java.util.Scanner;

public class Joueur {
    private static int count = 0;
    private int ID = 0;
    private ArrayList<Territoire> listeTerritoires;

    // Constructeur
    public Joueur() {
        this.setID();
    }

    // Getters et setters
    public void setID() {
        this.ID = count++;
    }

    public int getID() {
        return ID;
    }

    public static int getCount() {
        return count;
    }

    public ArrayList<Territoire> getListeTerritoires() {
        return listeTerritoires;
    }

    public ArrayList<Integer> attaquerTerritoire() throws TerritoryNotOwnedException {
        Scanner scanner = new Scanner(System.in);
        int territoireAttaquant = 0;
        int territoireAttaque;
        do {
            System.out.println("Choisissez le territoire avec lequel vous attaquez :");
            try {
                territoireAttaquant = scanner.nextInt();
                boolean isOwned = false;
                for (int i = 0; i < this.listeTerritoires.size(); i++) {
                    if (territoireAttaquant == this.listeTerritoires.get(i).getID()){
                        isOwned = true;
                    }
                }
                if (!isOwned){
                    throw new TerritoryNotOwnedException("Veuillez choisir un territoire qui vous appartient !");
                }
            } catch (TerritoryNotOwnedException e) {
                System.out.println(e);
            }

            System.out.println("Choisissez le territoire que vous attaquez :");
            territoireAttaque = scanner.nextInt();
        } while (territoireAttaquant == territoireAttaque);
        ArrayList<Integer> combat = null;
        combat.add(territoireAttaquant);
        combat.add(territoireAttaque);
        scanner.close();
        return combat;
    }

    public void terminerTour() {
        /* A implémenter */
    }
}
