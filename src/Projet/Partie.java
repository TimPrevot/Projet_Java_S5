package Projet;

import java.util.ArrayList;
import java.util.Random;


public class Partie {
    public static int NB_TERRITOIRES = 12;
    public static int MAX_FORCE = 8;
    public static int MAX_X = 4;
    public static int MAX_Y = 3;
    public static int MAX_TERRITOIRES = 4;
    private int nbJoueurs;

    // Constructeur
    public Partie(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    // Initialisation des variables
    private Carte carte;
    private Joueur currentPlayer;
    private ArrayList<Joueur> joueurs;
    private TwoDimensionalArrayList<String> map;

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    // Création de la carte
    public void initCarte() {
        this.carte = new Carte();
    }

    // Création des joueurs
    public void initJoueurs() {
        for (int i = 0; i < nbJoueurs; i++) {
            Joueur nouveauJoueur = new Joueur();
            joueurs.add(nouveauJoueur);
        }
    }

    // Initialisation de la map
    public void initMap() {
        Random random = new Random();
        int ID;
        int force;
        for (int i = 0; i < MAX_X; i++) {
            for (int j = 0; j < MAX_Y; j++) {
                do {
                    ID = random.nextInt(nbJoueurs);
                    force = random.nextInt(9 - 1) + 1;
                    if (this.joueurs.get(ID).getListeTerritoires().size() <= 5) {
                        this.map.addToInnerArray(i, j, ID + " " + force);
                    }
                } while (this.joueurs.get(ID).getListeTerritoires().size() > 5);
            }
        }
    }

    // Changer joueur en cours
    public void changeCurrentPlayer() {
        Random random = new Random();
        int nextPlayerID = random.nextInt(nbJoueurs);
        this.currentPlayer = this.joueurs.get(nextPlayerID);
    }

    public void combat(int territoireAttaquant, int territoireAttaque) {
        Territoire attaquant = new Territoire();
        Territoire defenseur = new Territoire();
        for (int i = 0; i < NB_TERRITOIRES; i++) {
            for (int j = 0; j < NB_TERRITOIRES; j++) {
                if (this.carte.getTerritoires().get(i).get(j).getID() == territoireAttaquant) {
                    attaquant = this.carte.getTerritoires().get(i).get(j);
                }
            }
        }
        for (int i = 0; i < NB_TERRITOIRES; i++) {
            for (int j = 0; j < NB_TERRITOIRES; j++) {
                if (this.carte.getTerritoires().get(i).get(j).getID() == territoireAttaque) {
                    defenseur = this.carte.getTerritoires().get(i).get(j);
                }
            }
        }
        int totalAttaquant = 0;
        int totalDefenseur = 0;
        int coup;
        Random random = new Random();
        // Calcul des jets de l'attaquant
        for (int i = 0; i < attaquant.getForce() - 1; i++) {
            coup = random.nextInt(7 - 1) + 1;
            totalAttaquant += coup;
        }

        // Calcul des jets du défenseur
        for (int i = 0; i < defenseur.getForce() - 1; i++) {
            coup = random.nextInt(7 - 1) + 1;
            totalDefenseur += coup;
        }

        // Calcul des résultats
        if (totalAttaquant > totalDefenseur) {
            defenseur.setIDJoueur(attaquant.getIDJoueur());
            defenseur.setForce(attaquant.getForce() - 1);
            attaquant.setForce(1);
        } else if (totalAttaquant < totalDefenseur) {
            attaquant.setForce(1);
        }


    }

}
