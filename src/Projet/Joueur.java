package Projet;

import java.util.ArrayList;
import java.util.Scanner;

public class Joueur {
    private static int count = 0;
    private int ID = 0;
    private ArrayList<String> listeTerritoires;

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

    public ArrayList<String> getListeTerritoires() {
        return listeTerritoires;
    }

    public ArrayList<Integer> attaquerTerritoire() {
        Scanner scanner = new Scanner(System.in);
        int territoireAttaquant;
        int territoireAttaque;
        do {
            System.out.println("Choisissez le territoire avec lequel vous attaquez :");
            territoireAttaquant = scanner.nextInt();
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
