package Projet;

import java.util.ArrayList;
import java.util.Random;

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
        Random random = new Random();
        int nextPlayerID = random.nextInt(nbJoueurs - 0);
        this.currentPlayer = this.joueurs.get(nextPlayerID);
    }

    public void combat(int territoireAttaquant, int territoireAttaque) {
        Territoire attaquant = new Territoire();
        Territoire defenseur = new Territoire();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (this.carte.getTerritoires().get(i).get(j).getID() == territoireAttaquant) {
                    attaquant = this.carte.getTerritoires().get(i).get(j);
                }
            }
        }
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
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

        // Si l'attaquant gagne
        if (totalAttaquant > totalDefenseur) {
            defenseur.setIDJoueur(attaquant.getIDJoueur());
            defenseur.setForce(attaquant.getForce() - 1);
            attaquant.setForce(1);
        } else if (totalAttaquant < totalDefenseur) {
            attaquant.setForce(1);
        }


    }

}
