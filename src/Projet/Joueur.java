package projet;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * Joueur défini par un ID unique
 *
 * @see Partie
 * @see Territoire
 */
//public class Joueur extends Thread {
public class Joueur {

    private static int count = 0;                       // pour la génération automatique d'ID

    private int iID = 0;                                // ID du Joueur
    //private ArrayList<Territoire> listeTerritoires;
    private Vector<Territoire> vListeTerritoires;       // liste des Territoires appartenant au Joueur
    private Scanner clavier;
    private boolean bAMonTour = false;                  // permet au Joueur d'attaquer autant de fois qu'il le souhaite
    //    private boolean bPartieEnCours = false; // Threads....

    public int getiID() {
        return iID;
    }

    public Vector<Territoire> getvListeTerritoires() {
        return vListeTerritoires;
    }

    public boolean isAMonTour() {
        return bAMonTour;
    }

    public void setiID() {
        this.iID = count++;
    }

    public void setbAMonTour(boolean bAMonTour) {
        this.bAMonTour = bAMonTour;
    }

    /**
     * Constructeur par défaut: génération automatique de l'ID
     *
     * @see Partie
     */
    public Joueur() {
        this.setiID();
        this.vListeTerritoires = new Vector();
        clavier = new Scanner(System.in);
    }

    /**
     * Ajoute un Territoire à la liste des Territoires appartenant au Joueur
     *
     * @param territoire Territoire à ajouter
     * @see Partie
     * @see Joueur#attaquerTerritoire()
     */
    public void addTerritoire(Territoire territoire) {
        this.vListeTerritoires.add(territoire);
    }

    /**
     * Enlève un Territoire de la liste des Territoires appartenant au Joueur
     *
     * @param territoire Territoire à enlever
     * @see Joueur#attaquerTerritoire()
     */
    public void removeTerritoire(Territoire territoire) {
        this.vListeTerritoires.removeElement(territoire);
    }

//    @Override
//    public void start() {
//        super.start();
//        bPartieEnCours = true;
//    }

    /**
     * Retourne true si la liste des Territoires appartenant au Joueur est vide; false sinon
     *
     * @return true si la liste des Territoires appartenant au Joueur est vide; false sinon
     * @see Partie
     */
    public boolean isActive() {
        return !vListeTerritoires.isEmpty();
    }

//    @Override
//    public void run() {
//        System.out.println("Joueur numéro " + ID);
//        while (bPartieEnCours) {
//            jouerUntour();
//        }
//
//    }
//    private synchronized void jouerUntour() {
//        try {
//            DateFormat df = new SimpleDateFormat("HH:mm:ss");
//            //System.out.println(df.format(new Date()) + " " + getName() + " " + getState() + " Joueur " + ID);
//            while (!bAMonTour){
//                wait();
//            }
//
//            iCount++;
//            System.out.println(df.format(new Date()) + " " + getName() + " " + getState() + " Joueur " + ID + " compteur: " + iCount);
//            if (!isInterrupted()){
//                Thread.sleep(1000);
//
//            }
//            notifyAll();
//            if (iCount > 4){
//                othersToGo();
//                System.out.println("fin");
//            }
//        } catch (InterruptedException  ie){
//            Thread.currentThread().interrupt();
//            ie.printStackTrace();
//        }
//    }
//    private void othersToGo(){
//            System.out.println("Others to go");
//        bPartieEnCours = false;
//    }

    /**
     * Choix du Territoire à étendre et du Territoire à attaquer;
     * lancement des dés de chaque Joueur (l'autre Joueur étant le propriétaire du Territoire attaqué);
     * comparaison des résultats et modification des listes de Territoires de chacun des 2 Joueurs
     *
     * @throws TerritoryNotOwnedException si le Territoire à étendre n'appartient pas au Joueur
     * @see Partie
     */
    public void attaquerTerritoire() throws TerritoryNotOwnedException {
        //Scanner clavier = new Scanner(System.in);
        Territoire territoireAttaquant = null;
        Territoire territoireAttaque = null;
        try {
            System.out.print("Territoire à étendre : ");
            int iIDAttaquant = clavier.nextInt();

            /* contrôler que le Territoire attaqué appartient au Joueur */
            boolean isOwned = false;
            for (Territoire terr : vListeTerritoires) {
                if (iIDAttaquant == terr.getiId()) {
                    isOwned = true;
                    territoireAttaquant = terr;
                    break;
                }
            }
            if (!isOwned) {
                throw new TerritoryNotOwnedException("Veuillez choisir un territoire qui vous appartient !");
            }
            if (territoireAttaquant.getiForce() < 2) {
                //TODO throw new TerritoryTooWeakException("Le Territoire que vous souhaitez étendre est trop faible");
                System.out.println("Le Territoire que vous souhaitez étendre est trop faible");
            }

            System.out.print("Territoire attaqué : ");
            int iIDAttaque = clavier.nextInt();

            /* contrôler que le Territoire attaqué n'appartient PAS au Joueur */
            isOwned = false;
            for (Territoire terr : vListeTerritoires) {
                if (iIDAttaque == terr.getiId()) {
                    isOwned = true;
                    break;
                }
            }
            if (isOwned) {
                //TODO throw new TerritoryAlreadyOwnedException("Veuillez attaquer un territoire qui NE vous appartient PAS !");
                System.out.println("Veuillez attaquer un territoire qui NE vous appartient PAS !");
            }

            /* contrôler que le Territoire attaqué est un Territoire voisin du Territoire à étendre */
            boolean isVoisin = false;
            for (Territoire terrVoisin : territoireAttaquant.getvTerritoireVoisin()) {
                if (iIDAttaque == terrVoisin.getiId()) {
                    isVoisin = true;
                    territoireAttaque = terrVoisin;
                    break;
                }
            }
            if (!isVoisin) {
                //TODO throw new TerritoryDistantException("Le Territoire attaqué n'est pas voisin du Territoire que vous souhaitez étendre");
                System.out.println("Le Territoire attaqué n'est pas voisin du Territoire que vous souhaitez étendre");
            }

            System.out.println("Attaquant: " + territoireAttaquant.getiId() + " Attaqué: " + territoireAttaque.getiId());

            Vector<Integer> vDiceAttaquant = runDice(territoireAttaquant.getiForce());
            Vector<Integer> vDiceAttaque = runDice(territoireAttaque.getiForce());

            System.out.println("Dés Attaquant: " + vDiceAttaquant.toString());
            System.out.println("Dés Attaqué: " + vDiceAttaque.toString());

            int iTotalAttaquant = 0;
            for (int i = 0; i < vDiceAttaquant.size(); i++) {
                iTotalAttaquant = iTotalAttaquant + vDiceAttaquant.elementAt(i);
            }

            int iTotalAttaque = 0;
            for (int i = 0; i < vDiceAttaque.size(); i++) {
                iTotalAttaque = iTotalAttaque + vDiceAttaque.elementAt(i);
            }

            System.out.println("Total Attaquant = " + iTotalAttaquant);
            System.out.println("Total Attaqué = " + iTotalAttaque);

            if (iTotalAttaquant > iTotalAttaque) {
                territoireAttaque.getOwner().removeTerritoire(territoireAttaque);
                addTerritoire(territoireAttaque);

                territoireAttaque.setOwner(this);

                territoireAttaque.setiForce(territoireAttaquant.getiForce() - 1);
                territoireAttaquant.setiForce(1);

            } else if (iTotalAttaque > iTotalAttaquant) {
                territoireAttaquant.setiForce(1);
            } else {
                //TODO Que fait-on en cas d'égalité???
            }

        } catch (InputMismatchException ime) {
            System.out.println("Entrée invalide; saisir un int SVP");
            //ime.printStackTrace();
        } finally {
            //            try {
            //                if (clavier != null){clavier.close();}
            //            } catch (Exception ignored) {}
        }
    }

    /**
     * Retourne une liste de valeurs de dés (points des faces)
     *
     * @param iForce nombre de dés
     * @return liste de valeurs de dés
     * @see Joueur#attaquerTerritoire()
     */
    private Vector<Integer> runDice(int iForce) {
        Vector<Integer> vRetour = new Vector<>(iForce);
        Random random = new Random();

        for (int i = 0; i < iForce; i++) {
            vRetour.addElement(random.nextInt(6) + 1);
        }

        return vRetour;
    }

    /**
     * Calcul récursif du plus grand nombre de territoires contigus du joueur
     *
     * @param iNbCont   compteur du nombre de territoires contigus
     * @param terr      territoire à examiner
     * @param vTerrDone liste des territoires déjà examinés
     * @return plus grand nombre de territoires contigus du joueur
     * @see Partie
     */
    private int addContigu(int iNbCont, Territoire terr, Vector<Territoire> vTerrDone) {
        for (Territoire terrJoueur : terr.getvTerritoireVoisin()) {
            if ((!vTerrDone.contains(terrJoueur)) && (terrJoueur.getOwner().getiID() == iID)) {
                iNbCont++;
                vTerrDone.addElement(terrJoueur);
                iNbCont = addContigu(iNbCont, terrJoueur, vTerrDone);
            }
        }
        return iNbCont;
    }

    /**
     * Attribution des dés de renfort au Joueur à la fin du tour
     * Distribution aléatoire des dés de renfort sur les territoires du Joueur
     *
     * @see Partie
     */
    public void terminerTour() {
        int iNbMaxContigus = 0;
        Vector<Territoire> vTerrDone = new Vector<>();
        for (Territoire terrJoueur : vListeTerritoires) {
            if (!vTerrDone.contains(terrJoueur)) {
                vTerrDone.addElement(terrJoueur);
                int iNbContigus = addContigu(1, terrJoueur, vTerrDone);
                if (iNbContigus > iNbMaxContigus) {
                    iNbMaxContigus = iNbContigus;
                }
            }
        }
        System.out.println("Territoires contigus = " + iNbMaxContigus);
        if (!vTerrDone.isEmpty()) {
            System.out.println("Territoires examinés : " + vTerrDone.toString());
        }
        Random random1 = new Random();
        Random random2 = new Random();
        int forceAjoutee, territoireRenforce;
        do {
            forceAjoutee = random1.nextInt(iNbMaxContigus) + 1;
            territoireRenforce = random2.nextInt(this.vListeTerritoires.size());
            this.vListeTerritoires.get(territoireRenforce).setiForce(this.vListeTerritoires.get(territoireRenforce).getiForce() + forceAjoutee);
            System.out.println("Le territoire " + this.vListeTerritoires.get(territoireRenforce).getiId() + " a gagné " + forceAjoutee + " points de force !");
            iNbMaxContigus -= forceAjoutee;
        } while (iNbMaxContigus > 0);

        bAMonTour = false;
    }
}
