package Projet;

import java.util.ArrayList;
import java.util.Random;


public class Partie {
    public static int MAX_FORCE = 8;
    public static int MAX_X = 4;
    public static int MAX_Y = 4;
    private static int MAX_TERRITOIRES = 4;
    public static int nbJoueurs;
    private static int NB_TERRITOIRES;

    // Constructeur
    public Partie(int nbJoueurs) {
        Partie.nbJoueurs = nbJoueurs;
    }

    public void setNbTerritoires() {
        NB_TERRITOIRES = nbJoueurs * MAX_TERRITOIRES;
    }

    // Initialisation des variables
    private Carte carte;
    private Joueur currentPlayer;
    private ArrayList<Joueur> joueurs;
    private TwoDimensionalArrayList<String> map;

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public Joueur getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Joueur nextPlayer) {
        this.currentPlayer = nextPlayer;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public TwoDimensionalArrayList<String> getMap() {
        return map;
    }

    // Création de la carte
    public void initCarte() {
        this.carte = new Carte();
    }

    // Création des joueurs
    public void initJoueurs() {
        for (int i = 0; i < nbJoueurs; i++) {
            Joueur nouveauJoueur = new Joueur();
            this.getJoueurs().add(nouveauJoueur);
        }
    }

    // Initialisation de la map
    public void initMap() {
        Random random = new Random();
        int ID;
        int force;
        ArrayList<Integer> forceJoueurs;
        for (int i = 0; i < MAX_X; i++) {
            for (int j = 0; j < MAX_Y; j++) {
                do {
                    ID = random.nextInt(nbJoueurs);
                    force = random.nextInt(9 - 1) + 1;
                    if (this.getJoueurs().get(ID).getListeTerritoires().size() <= 5) {
                        this.getMap().addToInnerArray(i, j, ID + " " + force);
                    }
                } while (this.getJoueurs().get(ID).getListeTerritoires().size() > 5);
            }
        }
    }

    // Afficher la map
    public void displayMap() {
        int maxLength = 3;

        System.out.println(' ' * (maxLength + 1) + '|');
        for (int i = 0; i < MAX_X; i++) {
            System.out.println('-' * ((maxLength + 1) * (MAX_X + 1) + 1));
        }
        System.out.println('\n');
        System.out.println('-' * ((maxLength + 1) * (MAX_X + 1) + 1));

        for (int i = 0; i < MAX_X; i++) {
            for (int j = 0; j < MAX_Y; j++) {
                if (j == 0) {
                    System.out.println(' ' * (maxLength + 1) + '|');
                }
                System.out.println(' ' * (maxLength + 1) + '|');
            }
        }
    }

    // Changer joueur en cours
    public void changeCurrentPlayer() {
        Random random = new Random();
        int nextPlayerID;
        do {
            nextPlayerID = random.nextInt(nbJoueurs);
        } while (nextPlayerID == this.getCurrentPlayer().getID());
        this.setCurrentPlayer(this.joueurs.get(nextPlayerID));
    }

    // Gère les attaques
    public void combat() throws TerritoryNotOwnedException {
        Territoire attaquant = new Territoire();
        Territoire defenseur = new Territoire();
        ArrayList<Integer> combat = this.currentPlayer.attaquerTerritoire();
        for (int i = 0; i < NB_TERRITOIRES; i++) {
            for (int j = 0; j < NB_TERRITOIRES; j++) {
                if (this.carte.getTerritoires().get(i).get(j).getID() == combat.get(0)) {
                    attaquant = this.carte.getTerritoires().get(i).get(j);
                }
            }
        }
        for (int i = 0; i < NB_TERRITOIRES; i++) {
            for (int j = 0; j < NB_TERRITOIRES; j++) {
                if (this.getCarte().getTerritoires().get(i).get(j).getID() == combat.get(1)) {
                    defenseur = this.getCarte().getTerritoires().get(i).get(j);
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
