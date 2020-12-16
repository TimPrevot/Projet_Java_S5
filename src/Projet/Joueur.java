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

    public void attaquerTerritoire() {
        Scanner scanner = new Scanner(System.in);
        int territoireAttaquant = scanner.nextInt();
        int territoireAttaque = scanner.nextInt();

    }

    public void terminerTour() {
        /* A implémenter */
    }
}
