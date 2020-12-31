// TODO renommer le package en minuscules: package projet;
package Projet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Initialise une Partie et gère l'ordonnancement du jeu
 *
 * @see Carte
 * @see Territoire
 * @see Joueur
 */
public class Partie {

    private Carte carte;                        // Carte du jeu
    private Vector<Joueur> vJoueurs;            // liste de Joueurs
    private Vector<Joueur> vJoueursActifs;      // liste de Joueurs possédant au moins 1 Territoire
    private boolean bPartieEnCours = false;     // permet de terminer la Partie
    private final Scanner clavier;
    private final Random random;                // variable d'instance car plusieurs Random déclarés en variables locales ne semblent pas donner satisfaction


    /**
     * Réduit le nombre de Territoires à un multiple du nombre de Joueurs
     *
     * @return      nombre de Territoires éligibles à la Partie
     * @param       iNbTerritoires nombre de Territoires
     * @param       iNbJoueurs nombre de Joueurs
     *
     * @see Partie#init()
     */
    private static int nbEquitable(int iNbTerritoires, int iNbJoueurs) {
        int iTemp = iNbTerritoires % iNbJoueurs;
        while ((iNbTerritoires > 0) && (iTemp > 0)) {
            iNbTerritoires --;
            iTemp = iNbTerritoires % iNbJoueurs;
        }
        return iNbTerritoires;
    }

    /**
     * Retourne la longueur du côté d'une matrice CARREE pouvant contenir (au moins) tous les Territoires
     *
     * @return      longueur du côté de la matrice carrée pouvant contenir les Territoires
     * @param       iNbTerritoires nombre de Territoires
     *
     * @see Partie#init()
     */
    private static int coteSqrt(int iNbTerritoires) {
        double dSqrt = Math.sqrt(iNbTerritoires);
        if (dSqrt > (int) dSqrt) {
            return (int) dSqrt + 1;
        }
        return (int) dSqrt;
    }

    /**
     * Retourne la longueur d'un côté d'une matrice RECTANGULAIRE pouvant contenir EXACTEMENT tous les Territoires
     *
     * @return      longueur d'un côté de la matrice rectangulaire
     * @param       iNbTerritoires nombre de Territoires
     *
     * @see Partie#init()
     */
    private static int cote(int iNbTerritoires) {
        double dSqrt = Math.sqrt(iNbTerritoires);
        int iMax = (int) dSqrt;
        int i = 1;

        int iDiv = iNbTerritoires / i;
        int iCote = iDiv;
        while (iDiv > iMax) {
            i++;
            iDiv = iNbTerritoires / i;
            if(iNbTerritoires % i == 0) {
                iCote = iDiv;
            }
        }
        return iCote;
    }

    /**
     * Initialisation de la Partie
     *
     */
    private void init() {
        FileInputStream appStream = null;
        try {
            Properties prop = new Properties();
            appStream = new FileInputStream("src/Projet/DiceWars.ini");
            prop.load(appStream);

            //System.out.println("generalPath: " + prop.getProperty("generalPath"));
            //System.out.println("CSVFileName: " + prop.getProperty("CSVFileName"));
            String sGeneralPath = prop.getProperty("generalPath");
            String sCSVFileName = prop.getProperty("CSVFileName");
            int iNbTerritories = Integer.parseInt(prop.getProperty("nbTerritories"));
            int iNbPlayers = Integer.parseInt(prop.getProperty("nbPlayers"));
            int iNbDicePerPlayer = Integer.parseInt(prop.getProperty("nbDicePerPlayer"));

            System.out.println("iNbTerritories = " + iNbTerritories);
            System.out.println("iNbPlayers = " + iNbPlayers);
            System.out.println("iNbDicePerPlayer = " + iNbDicePerPlayer);

            if (sGeneralPath != null && !sGeneralPath.isEmpty() && sCSVFileName != null && !sCSVFileName.isEmpty()) {

                /* instanciation de la Carte à partir du fichier CSV */
                this.carte = new Carte(sGeneralPath + sCSVFileName);

            } else {
                int iNbEquitable = nbEquitable(iNbTerritories, iNbPlayers);
                int iAbscissesMaxSqrt = coteSqrt(iNbEquitable);
                System.out.println("iNbEquitable = " + iNbEquitable);
                System.out.println("iAbscissesMaxSqrt = " + iAbscissesMaxSqrt);

                /* on ne veut pas de matrice carrée contenant une ligne vide ou une ligne avec 1 seul Territoire */
                if (iNbEquitable - (iAbscissesMaxSqrt * (iAbscissesMaxSqrt - 1)) < 2){
                    int iAbscissesMax = cote(iNbEquitable);
                    System.out.println("iAbscissesMax = " + iAbscissesMax);

                    /* instanciation aléatoire de la Carte (matrice rectangulaire) */
                    this.carte = new Carte(iAbscissesMax, iNbEquitable / iAbscissesMax, iNbEquitable);
                } else {

                    /* instanciation aléatoire de la Carte (matrice carrée) */
                    this.carte = new Carte(iAbscissesMaxSqrt, iAbscissesMaxSqrt, iNbEquitable);
                }
            }

            //TODO Vérifications
            //nbDice doit être suffisant pour avoir au moins 1 dé par Territoire par Player
            //nb de Territoires > nb Joueurs
            //nb Joueurs > 1
            //nb de Dés par Joueur <= MAX_FORCE * iNbEquitable / iNbPlayers (sinon il restera des dés qu'on ne pourra...
            //... pas placer sur les Territoires du Joueur (saturés))

            /* instanciation des Joueurs */
            vJoueurs = new Vector<>(iNbPlayers);
            for(int i = 0; i < iNbPlayers; i++){
                Joueur joueur = new Joueur();
                vJoueurs.addElement(joueur);
            }

            /*
             * initialisation des Joueurs actifs (qui n'ont pas perdu)
             * Cette liste sera réduite chaque fois qu'un Joueur n'aura plus de Terrritoire
             * La Partie prend fin lorsqu'il n'y a plus qu'un seul Joueur actif
             */
            vJoueursActifs = (Vector)vJoueurs.clone();

            /* Attribution des Territoires aux Joueurs */

            /* le clonage des Territoires permet de les éliminer un à un après attribution sans toucher à la Carte */
            Vector<Territoire> vTerritoireAAllouer = (Vector)carte.getvTerritoires().clone();

            while (! vTerritoireAAllouer.isEmpty()) {
                for (Joueur joueur : vJoueurs) {

                    int indiceTerritoire = random.nextInt(vTerritoireAAllouer.size());
                    Territoire territoire = vTerritoireAAllouer.elementAt(indiceTerritoire);

                    territoire.setOwner(joueur);
                    joueur.addTerritoire(territoire);

                    vTerritoireAAllouer.removeElementAt(indiceTerritoire);
                }
            }

            /* répartition des dés sur les Territoires */
            for (Joueur joueur : vJoueurs) {

                /* chaque Territoire du Joueur reçoit une Force de 1 au minimum */
                for (Territoire terr : joueur.getvListeTerritoires()) {
                    terr.setiForce(1);
                }

                int iForceRestante = iNbDicePerPlayer - joueur.getvListeTerritoires().size();
                while (iForceRestante > 0) {
                    for (Territoire terr : joueur.getvListeTerritoires()) {
                        int iBorne = Integer.min(Territoire.getMAX_FORCE() - terr.getiForce(), iForceRestante);
                        int iForce = random.nextInt(iBorne + 1);
                        terr.setiForce(terr.getiForce() + iForce);
                        iForceRestante = iForceRestante - iForce;
                    }
                }
            }

            /* initialisation de la liste des Territoires voisins pour chaque Territoire */
            Territoire[][] territoiresMap = carte.getTerritoiresMap();
            for (Territoire terr : carte.getvTerritoires()) {
                int iAbscissa = terr.getiAbscissa();
                int iOrdinate = terr.getiOrdinate();
                int iGauche = iAbscissa - 1;
                int iDroite = iAbscissa + 1;
                int iBas = iOrdinate - 1;
                int iHaut = iOrdinate + 1;

                Vector<Territoire> vTerritoireVoisin = new Vector<>();

                if((iGauche >= 0) && (territoiresMap[iGauche][iOrdinate] != null)) {
                    vTerritoireVoisin.addElement(territoiresMap[iGauche][iOrdinate]);
                }
                if((iDroite < carte.getiAbscissaMax()) && (territoiresMap[iDroite][iOrdinate] != null)) {
                    vTerritoireVoisin.addElement(territoiresMap[iDroite][iOrdinate]);
                }
                if((iBas >= 0) && (territoiresMap[iAbscissa][iBas] != null)) {
                    vTerritoireVoisin.addElement(territoiresMap[iAbscissa][iBas]);
                }
                if((iHaut < carte.getiOrdinateMax()) && (territoiresMap[iAbscissa][iHaut] != null)) {
                    vTerritoireVoisin.addElement(territoiresMap[iAbscissa][iHaut]);
                }

                terr.setvTerritoireVoisin(vTerritoireVoisin);
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println("Fichier introuvable");
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("IO erreur");
            ioe.printStackTrace();
        } catch (NumberFormatException nfe) {
            System.out.println("Il faut des int pour les valeurs suivantes du fichier DiceWars.ini: nbTerritories, nbPlayers, nbDicePerPlayer");
            nfe.printStackTrace();
        }
        finally {
            try {
                if (appStream != null) { appStream.close(); }
            } catch (Exception ignored) {}
        }
    }

//    private void begin(){
//        for (int i=0; i < iNbPlayers; i++) {
//            joueurs[i].start();
//        }
//    }
//
//    private void tourDeJeu(){
//        //Affichage de la Carte
//        carte.displayCarte();
//
//        //Sélection aléatoire d'un Joueur actif
//        Random random = new Random();
//        int iTour = random.nextInt(iNbPlayers);
//        System.out.println("iTour = " + iTour);
//        //joueurs[iTour].setToPlay(true);
//        //joueurs[iTour].notify();
//        joueurs[iTour].setBAMonTour(true);
//        System.out.println("Les joueurs jouent...");
//    }

    /**
     * Sélectionne aléatoirement un Joueur ACTIF
     * Boucle sur l'attaque d'un Territoire par le Joueur
     *
     * @see Joueur#attaquerTerritoire()
     */
    private void tourDeJeu() {

        /* sélection aléatoire du Joueur ACTIF */
        int iTour = random.nextInt(vJoueursActifs.size());

        Joueur joueurActif = vJoueursActifs.elementAt(iTour);

        System.out.println();
        System.out.println("Joueur sélectionné = " + joueurActif.getiID());

        joueurActif.setbAMonTour(true);

        while (joueurActif.isAMonTour() && joueurActif.isActive() && bPartieEnCours) {
            try {

                joueurActif.attaquerTerritoire();

                //Affichage de la Carte
                carte.displayCarte();

                //Vérifier que la Partie n'est pas terminée
                Vector<Joueur> vJoueursActifsClone = (Vector)vJoueursActifs.clone();

                for (Joueur joueurActifClone : vJoueursActifsClone) {
                    if (! joueurActifClone.isActive()) {
                        vJoueursActifs.removeElement(joueurActifClone);
                    }
                }

                if (vJoueursActifs.size() < 2) {
                    bPartieEnCours = false;
                } else {
                    System.out.print("Souhaitez-vous arrêter ce Tour ? (O/N) : ");
                    if (clavier.next().equals("O")) {
                        joueurActif.terminerTour();
                    }
                }

            } catch(TerritoryNotOwnedException tnoe) {
                System.out.println(tnoe.toString());
                //tnoe.printStackTrace();
            }
        }
    }

    /**
     * Instanciation d'une Partie
     * Boucle sur les tours de jeu jusqu'à la fin de Partie
     *
     * @see Partie#tourDeJeu()
     */
    public static void main(String[] args) {
        Partie partie = new Partie();
        partie.init();
        //partie.begin(); // Utilisation des Threads

        /* affichage de la Carte */
        partie.carte.displayCarte();

        //TODO Persister la Partie ici!

        partie.bPartieEnCours = true;
        while (partie.bPartieEnCours) {

            partie.tourDeJeu();

            //TODO Persister la Partie ici!

        }
        System.out.println("   F I N   D E   P A R T I E");
        System.out.println();
        System.out.println("Le Joueur " + partie.vJoueursActifs.firstElement().getiID() + " a gagné !");

    }

    /**
     * Constructeur par défaut
     *
     */
    public Partie() {
        clavier = new Scanner(System.in);
        random = new Random();
    }


/*

    // Initialisation des variables
    private Joueur currentPlayer;
    private ArrayList<Joueur> joueursT;
    private TwoDimensionalArrayList<String> map;

    public ArrayList<Joueur> getJoueurs() {
        return joueursT;
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
*/

    // Gère les attaques
    /*public void combat() throws TerritoryNotOwnedException {
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
    }*/
}
