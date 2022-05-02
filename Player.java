package projet;
import java.util.Scanner;

/**
 * Classe permettant de créer des instances de types Player 
 * ayant un numéro, un nom et une couleur.
 */
public class Player {

    int number;
    String name;
    String color;
    Scanner scan;

    public Player(Scanner initScan, int initNb, String initName, String initColor){
        this.scan = initScan;
        this.number = initNb;
        this.name = initName;
        this.color = initColor;
    }

    public String getName() {
        return name;
    }
    public String getColor() {
        return color;
    }
}