package Projet;

import java.util.ArrayList;

public class Territoire {
    private static int count = 0;
    private int ID = 0;
    private int IDJoueur;
    private int force;
    private ArrayList<Integer> territoiresVoisins;

    // Constructeur
    public Territoire() {
        
    }

    public void setID() {
        this.ID = count++;
    }

    public int getID() {
        return ID;
    }

    public int getIDJoueur() {
        return IDJoueur;
    }

    public void setIDJoueur(int IDJoueur) {
        this.IDJoueur = IDJoueur;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public ArrayList<Integer> getTerritoiresVoisins() {
        return territoiresVoisins;
    }

    public void setTerritoiresVoisins(ArrayList<Integer> territoiresVoisins) {
        this.territoiresVoisins = territoiresVoisins;
    }
}
