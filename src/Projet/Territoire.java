// TODO renommer le package en minuscules: package projet;
package Projet;

import java.util.Vector;

/**
 * Territoire défini par un ID unique
 *
 * @see Partie
 * @see Carte
 * @see Joueur
 */
public class Territoire {

    private final static int MAX_FORCE = 8;         // nombre max de dés autorisé pour 1 Territoire
    private static int count = 0;                   // pour la génération automatique d'ID

    private int iId = 0;                            // ID du Territoire
    private Joueur owner;                           // propriétaire du Territoire
    private int iForce;                             // nombre de dés sur le Territoire
    private int iAbscissa;                          // abscisse du Territoire dans la matrice Carte
    private int iOrdinate;                          // ordonnée du Territoire dans la matrice Carte
    private Vector<Territoire> vTerritoireVoisin;   // liste des Territoires voisins

    public static int getMAX_FORCE() { return MAX_FORCE; }

    public int getiId() { return iId; }
    public Joueur getOwner() { return owner; }
    public int getiForce() { return iForce; }
    public int getiAbscissa() { return iAbscissa; }
    public int getiOrdinate() { return iOrdinate; }
    public Vector<Territoire> getvTerritoireVoisin() { return vTerritoireVoisin; }

    private void setiId() {
        this.iId = count++;
    }

    public void setOwner(Joueur owner) { this.owner = owner; }
    public void setiForce(int iForce) { this.iForce = iForce; }
    public void setiAbscissa(int iAbscissa) { this.iAbscissa = iAbscissa; }
    public void setiOrdinate(int iOrdinate) { this.iOrdinate = iOrdinate; }
    public void setvTerritoireVoisin(Vector<Territoire> vTerritoireVoisin) { this.vTerritoireVoisin = vTerritoireVoisin; }


    /**
     * Constructeur par défaut: génération automatique de l'ID
     *
     * @see Carte(int, int, int)
     */
    public Territoire() {
        this.setiId();
    }

    /**
     * Utilisé si on initialise la Carte via un fichier CSV
     *
     * @param       iId ID du Territoire
     *
     * @see Carte(String)
     */
    public Territoire(int iId){
        this.iId = iId;
    }

}
