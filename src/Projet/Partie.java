package Projet;

import java.util.ArrayList;

public class Partie {
    private int nbJoueurs;

    // Constructeur
    public Partie(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }

    // Initialisation des variables
    private Carte carte;
    private Joueur currentPlayer;
    private ArrayList<Joueur> joueurs;
    private TwoDimensionalArrayList<Integer> map;

    // Création de la carte
    public void initCarte() {
        this.carte = new Carte();
    }

    // Changer joueur en cours
    public void changeCurrentPlayer() {
        Joueur nextPlayer = new Joueur();
        /* Randomizer pour choisir le joueur */
        this.currentPlayer = nextPlayer;
    }

    

}
