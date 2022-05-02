package projet;

import java.util.Scanner;

/**
 * Classe permettant de créer une matrice doublement chaînée 
 * de cellules de la classe Cell.
 */

public class Grid {

    private int tailleX; // Hauteur de la grille
    private int tailleY; // Largeur de la grille
    Cell[] freeCells; // Contient la prochaine cellule libre de chaque colonne
    private Cell upLeftCorner; // Cellule en haut à gauche de la matrice

    public Grid(int initX, int initY, Scanner initScan) {
        this.tailleX = initX; // spécifier d'abord la hauteur, puis la largeur de la grille
        this.tailleY = initY;
        this.freeCells = new Cell[tailleY];
        this.initialize();
    }

    /**
     * Crée une matrice doublement chaînée de cellules (la grille de jeu). 
     * Cette méthode part d'une cellule située en haut à gauche de la grille et 
     * crée et lie entre elles toutes les cellules d'une même ligne.
     * Ensuite, crée une cellule en-dessous de la première et les lie entre elles.
     * Après, crée et lie entre elles les cellules de la même ligne et les lie également 
     * aux cellules du dessus.
     * Effectue ces opérations autant de fois que l'on a besoin de lignes dans la grille.
     */
    public void initialize() {
        // On commence par créer la cellule en haut à gauche de la matrice
        Cell C = new Cell();
        this.upLeftCorner = C;
        // On chaîne la première ligne de cellules à partir de celle-ci
        for (int i = 0; i < this.tailleY - 1; i++) {
            C.setRight(new Cell());
            C = C.right;
        }
        // Pour chaque ligne à assigner en-dessous:
        for (int i = 1; i < this.tailleX; i++) {
            // On repart au maillon en haut à gauche
            C = upLeftCorner;
            // On descend pour que C soit sur le maillon en bas à gauche
            while (C.down != null) {
                C = C.down;
            }
            // On le chaîne à un maillon vers le bas
            C.setDown(new Cell());
            // On considère maintenant ce maillon
            C = C.down;
            // On chaîne une ligne vers la droite
            for (int j = 0; j < this.tailleY - 1; j++) {
                Cell N = new Cell();
                C.setRight(N);
                N.setUp(C.up.right);
                C = N;
            }
        }
        // On repart au maillon en haut à gauche
        C = upLeftCorner;
        // On descend pour que C soit sur le maillon en bas à gauche
        while (C.down != null) {
            C = C.down;
        }
        // Enfin, on récupère les maillons en bas de la chaîne dans freecells
        for (int i = 0; i < tailleY; i++) {
            freeCells[i] = C;
            C = C.right;
        }
    }

    /**
     * Vérifie si la grille est pleine.
     * Retourne vrai si c'est le cas. 
     * Retourne false sinon.
     * 
     * @return
     */
    boolean isFull() {
        for (int i = 0; i < tailleY; i++) {
            if (freeCells[i] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * toString appelle toString de chaque cellule de la grille, ce qui permet de faire l'affichage.
     */
    @Override
    public String toString() {
        // Affichage de la première barre au-dessus de la grille
        String S = "\u250c";
        for(int i=0; i<6; i++){
            S += "\u2500" + "\u2500" + "\u2500" + "\u2500";
            S += "\u252c";
        }
        S += "\u2500" + "\u2500" + "\u2500" + "\u2500";
        S += "\u2510";

        // Affichage du reste de la grille (récursif)
        S += "\n" + upLeftCorner.toString();
        return S;
    }
}