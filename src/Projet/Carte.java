package projet;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Carte de Territoires, modélisée par une matrice à 2 dimensions
 *
 * @see Partie
 */
public class Carte {

    private int iAbscissaMax = -1;                  // nombre de lignes de la matrice
    private int iOrdinateMax = -1;                  // nombre de colonnes de la matrice
    private Territoire[][] territoiresMap = null;   // matrice des Territoires
    private Vector<Territoire> vTerritoires;        // utile pour établir la liste des Territoires voisins (entre autres)

    public int getiAbscissaMax() { return iAbscissaMax; }
    public int getiOrdinateMax() { return iOrdinateMax; }
    public Territoire[][] getTerritoiresMap() { return territoiresMap; }
    public Vector<Territoire> getvTerritoires() { return vTerritoires; }

    /**
     * Constructeur par défaut: initialisation aléatoire de la Carte
     *
     * @param       iAbscissaMax largeur de la matrice des Territoires
     * @param       iOrdinateMax longueur de la matrice des Territoires
     * @param       iNbEquitable nombre de Territoires à instancier dans la matrice
     *
     * @see Carte(String)
     */
    public Carte(int iAbscissaMax, int iOrdinateMax, int iNbEquitable) {
        territoiresMap = new Territoire[iAbscissaMax][iOrdinateMax];
        vTerritoires = new Vector<>();
        this.iAbscissaMax = iAbscissaMax;
        this.iOrdinateMax = iOrdinateMax;

        int iCompteurTerritoire = 0;
        for (int i = 0; i < iAbscissaMax; i++) {
            for (int j = 0; j < iOrdinateMax; j++) {
                iCompteurTerritoire++;

                /* Il est possible que le nombre de cases de la matrice  soit > au nombre de Territoires */
                if (iCompteurTerritoire <= iNbEquitable) {

                    Territoire territoire = new Territoire();
                    territoire.setiAbscissa(i);
                    territoire.setiOrdinate(j);
                    territoiresMap[i][j] = territoire;
                    vTerritoires.addElement(territoire);
                }
            }
        }
    }

    /**
     * Initialisation de la Carte à partir d'un fichier CSV
     *
     * @param       sCSVFileName nom du fichier CSV (path complet)
     *
     * @see Carte(int, int, int)
     */
    public Carte(String sCSVFileName) {
        BufferedReader bfIn = null;
        try {
            bfIn = new BufferedReader(new FileReader(sCSVFileName));
            String sLine;
            int iAbscissa = 0;

            /* la première ligne nous donne le nombre de colonnes */
            if ((sLine = bfIn.readLine()) != null) {

                StringTokenizer strToken = new StringTokenizer(sLine, ";");
                iOrdinateMax = strToken.countTokens();
                iAbscissa++;

                /* lecture complète du fichier CSV pour avoir le nombre de lignes */
                while ((bfIn.readLine()) != null) {
                    iAbscissa++;
                }

                iAbscissaMax = iAbscissa;
                System.out.println("territoires " + iAbscissaMax + " X " + iOrdinateMax);
                territoiresMap = new Territoire[iAbscissaMax][iOrdinateMax];
                vTerritoires = new Vector<>();

                bfIn.close();

                /* nouvelle lecture du fichier CSV pour alimenter la matrice */
                bfIn = new BufferedReader(new FileReader(sCSVFileName));
                iAbscissa = 0;

                while ((sLine = bfIn.readLine()) != null) {

                    strToken = new StringTokenizer(sLine, ";");
                    int iOrdinate = 0;

                    while (strToken.hasMoreTokens()) {
                        String strId = strToken.nextToken();
                        //System.out.println("ordonnee " + iOrdinate + " strId : " + strId);

                        if (! strId.equals("null")) {

                            /* vérification ID de Territoire non déjà existant */
                            int iId = Integer.parseInt(strId);
                            if (isTerritoireExistant(iId)) {
                                // TODO Throw new Exception
                            } else {
                                Territoire territoire = new Territoire(iId);
                                territoire.setiAbscissa(iAbscissa);
                                territoire.setiOrdinate(iOrdinate);
                                territoiresMap[iAbscissa][iOrdinate] = territoire;
                                vTerritoires.addElement(territoire);
                            }
                        }
                        iOrdinate++;
                    }
                    iAbscissa++;
                }
            } else {

                /* fichier CSV vide */
                // TODO Throw new Exception
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Fichier introuvable");
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("IO erreur");
            ioe.printStackTrace();
        } catch (NumberFormatException nfe){
            System.out.println("Le fichier CSV doit contenir des int ou 'null'");
            nfe.printStackTrace();
        } finally {
            try {
                if (bfIn != null){bfIn.close();}
            } catch (Exception ignored) {}
        }
    }

    /**
     * Vérification de l'unicité de l'ID d'un Territoire
     *
     * @return      true si l'ID existe déjà dans la matrice des Territoires; false sinon
     * @param       iId ID du Territoire
     *
     * @see Carte(String)
     */
    private boolean isTerritoireExistant(int iId) {

        if (territoiresMap == null) {     // la matrice des Territoires n'est pas instanciée
            return false;
        }
        for (int i = 0; i < iAbscissaMax; i++) {
            for (int j = 0; j < iOrdinateMax; j++) {
                if ((territoiresMap[i][j] != null) && (territoiresMap[i][j].getiId() == iId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Affichage de la matrice des Territoires
     *
     * @see Partie
     */
    public void displayCarte() {
        System.out.println();
        for (int i = 0; i < iAbscissaMax; i++) {
            for (int j = 0; j < iOrdinateMax; j++) {
                if(territoiresMap[i][j] != null) {
                    System.out.print(territoiresMap[i][j].getiId() + " Owner:" + territoiresMap[i][j].getOwner().getiID() + " Force:" + territoiresMap[i][j].getiForce() + "\t|\t" );
                } else {
                    System.out.print("      NULL      \t|\t");
                }
            }
            System.out.println();
        }
        System.out.print("");
    }

}
