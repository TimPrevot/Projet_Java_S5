package projet;

import java.io.*;
import java.util.Properties;
import java.util.StringTokenizer;


public class CarteTest {

    private static int nbEquitable(int iNbTerritoires, int iNbJoueurs){
        int iTemp = iNbTerritoires % iNbJoueurs;
        while ((iNbTerritoires > 0) && (iTemp > 0)) {
            iNbTerritoires --;
            iTemp = iNbTerritoires % iNbJoueurs;
        }
        return iNbTerritoires;
    }
    private static int coteSqrt(int iNbTerritoires){
        Double dSqrt = Math.sqrt(iNbTerritoires);
        if(dSqrt.doubleValue() > dSqrt.intValue()){
            return dSqrt.intValue() + 1;
        }
        return dSqrt.intValue();
    }
    private static int cote(int iNbTerritoires){
        Double dSqrt = Math.sqrt(iNbTerritoires);
        int iMax = dSqrt.intValue();
        int i = 1;

        int iDiv = iNbTerritoires / i;
        int iCote = iDiv;
        while (iDiv > iMax){
            i++;
            iDiv = iNbTerritoires / i;
            if(iNbTerritoires % i == 0){
                iCote = iDiv;
            }
        }
        return iCote;
    }
    public static void main(String[] args) {


        Properties prop = null;
        FileInputStream appStream = null;
        System.out.println(11 % 4);
        try {
            System.out.println("yo epr");
            String pwd = System.getProperty("user.dir");
            System.out.println("Le répertoire courant est : " + pwd);
            prop = new Properties();
            appStream = new FileInputStream("src/Projet/DiceWars.ini");
            prop.load(appStream);
            System.out.println("Propriétés:");
            System.out.println("generalPath: " + prop.getProperty("generalPath"));
            System.out.println("CSVFileName: " + prop.getProperty("CSVFileName"));

            int iNbT = Integer.parseInt(prop.getProperty("nbTerritories"));
            int iNbP = Integer.parseInt(prop.getProperty("nbPlayers"));

            System.out.println("iNbT = " + iNbT);
            System.out.println("iNbP = " + iNbP);

            int iNbEquitable = nbEquitable(iNbT, iNbP);
            System.out.println("iNbEquitable = " + iNbEquitable);
            int iAbscissesMaxSqrt = coteSqrt(iNbEquitable);
            System.out.println("iAbscissesMaxSqrt = " + iAbscissesMaxSqrt);
            if (iNbEquitable - (iAbscissesMaxSqrt * (iAbscissesMaxSqrt - 1)) < 2){
                int iAbscissesMax = cote(iNbEquitable);
                System.out.println("iAbscissesMax = " + iAbscissesMax);
            }
            //int iAbscissesMax = cote(iNbEquitable);
            //System.out.println("iAbscissesMax = " + iAbscissesMax);

            String res=prop.getProperty("bidon");
            if (res == null) {
                System.out.println("res = null");
            } else{
                System.out.println("res = " + res);
            }
            String res2=prop.getProperty("bidon2");
            if (res2 == null) {
                System.out.println("res2 = null");
            } else{
                System.out.println("res2 = [" + res2 + "]");
            }
            System.out.println("yop epr");
        } catch (FileNotFoundException fnfe){
            System.out.println("Fichier introuvable");
            fnfe.printStackTrace();
        } catch (IOException ioe){
            System.out.println("IO erreur");
            ioe.printStackTrace();
        }
        finally {
            try {
                appStream.close();
                //out.close();
            } catch(Exception e){}
        }

    }
}
