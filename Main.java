package Connect4;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        Game myGame = new Game(scan);
        myGame.menu();
    }
}