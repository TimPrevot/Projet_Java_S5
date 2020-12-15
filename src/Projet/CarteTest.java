package Projet;

public class CarteTest {
    public static void main(String[] args) {
        Territoire t1 = new Territoire();
        System.out.println(t1.toString());
        Carte carte = new Carte();
        carte.addTerritoire(t1);
        System.out.println(carte.toString());

    }
}
