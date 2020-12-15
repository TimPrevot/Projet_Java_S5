package Projet;

import java.util.ArrayList;
import java.util.Scanner;

public class Joueur {
    private static int count = 0;
    private int ID = 0;
    private ArrayList<String> listeTerritoires;

    // Constructeur
    public Joueur() {}

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

    public void attaquerTerritoire() {
        Scanner scanner = new Scanner(System.in);
        String territoireAttaquant = scanner.next();
        String territoireAttaque = scanner.next();
    }

    public void terminerTour() {
        /* A implémenter */
    }
}
