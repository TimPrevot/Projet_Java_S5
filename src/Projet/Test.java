package Projet;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans Dice Wars ! A combien de joueurs voulez-vous jouer ?\n");
        Scanner scanner = new Scanner(System.in);
        int nbJoueurs = scanner.nextInt();
        scanner.close();
/*
        Partie game = new Partie(nbJoueurs);
        game.setNbTerritoires();
        game.initCarte();
        game.initJoueurs();
        game.initMap();

        boolean tourFini = false;
        while (game.getJoueurs().size() > 1){
            do {
                tourFini = false;

            } while (tourFini != true);
        }*/
    }
}
