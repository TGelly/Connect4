package Connect4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe permettant de lancer ou de reprendre une partie. 
 */
public class Game {

    Scanner scan;
    Grid grid;
    LinkedList <Player> playersList;
    boolean isOver;
    LinkedList<String> possibleColors = new LinkedList<String> ();
    Save S;
    String star = "\uD83C\uDF1F";

    public Game(Scanner initScan){
        this.scan = initScan;
        this.grid = new Grid(6, 7, this.scan);
        this.isOver = false;
        this.playersList = new LinkedList<Player>();
        S = new Save();
        possibleColors.add("\uD83D\uDD34"); // red
        possibleColors.add("\uD83D\uDFE0"); // orange
        possibleColors.add("\uD83D\uDFE1"); // yellow
        possibleColors.add("\uD83D\uDFE2"); // green
        possibleColors.add("\uD83D\uDD35"); // blue
        possibleColors.add("\uD83D\uDFE3"); // purple
        possibleColors.add("\uD83D\uDFE4"); // brown
        possibleColors.add("\u26AB"); // black
    }

    /**
     * Demande à l'utilisateur le nombre de joueurs, leur nom et leur couleur.
     * Crée une liste chaînée d'autant d'instances de type Player. 
     */
    public void createPlayersList(){

        System.out.println("À combien voulez-vous jouer?");
        int nbPlayers;
        nbPlayers = scan.nextInt();

        for(int i=0; i<nbPlayers; i++){
            System.out.println("Joueur n° "+(i+1)+", comment vous appelez-vous?");
            String name = scan.next();
            
            int chosenColor = -1;
            while (chosenColor < 0 || chosenColor > possibleColors.size()){
                System.out.println("Quelle couleur voulez-vous?");
                for(int j=0; j<possibleColors.size(); j++){
                    System.out.print(possibleColors.get(j));
                }
                System.out.println("");
                for(int j=0; j<possibleColors.size(); j++){
                    System.out.print(j+" ");
                }
                System.out.println("");
                chosenColor = scan.nextInt();
            }
            Player test = new Player(this.scan, i, name, this.possibleColors.get(chosenColor));
            playersList.addLast(test);
            possibleColors.remove(chosenColor);
        }
    }

    /**
     * Demande au joueur dans quelle colonne il veut placer son jeton
     * et retourne le numéro de la colonne.
     * 
     * @param p
     * @return
     */
    int promptColumn(Player p){
        String column = "-1";
        while(Integer.parseInt(column) < 0 || Integer.parseInt(column) > 6){
            System.out.println(p.getName()+", à vous de jouer. Vous avez les "+p.getColor()+".");
            column = scan.next();
            if(column.equals("q")){
                this.isOver = true;
                return -1;
            }
        }
        return Integer.parseInt(column);
    }

    void clearScreen(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

    /**
     * Insère d'abord le jeton dans la colonne que le joueur a indiqué.
     * Vérifie ensuite si le joueur associé à cette couleur a gagné grâce
     * à ce jeton.
     * S'il n'a pas gagné, augmente d'une case la valeur de la case la plus basse
     * où un jeton peut être inséré.
     * Vérifie ensuite, si la grille est pleine.
     * Sauvegarde le dernier coup joué dans un fichier texte.
     * Enfin, affiche la grille avec le dernier coup joué.
     * 
     * @param p
     * @param column
     */
    void playMove(Player p, int column) {
        // At the beginning of each move, clear the console.
        clearScreen();
        if (column != -1){
            // Then we display all the informations again:
            grid.freeCells[column].value = p.color;
            if (grid.freeCells[column].hasWon()) {
                System.out.println(p.name + " a gagné! Il avait les " + p.color + ".");
                this.isOver = true;
            }
            this.grid.freeCells[column] = grid.freeCells[column].up;
            if (this.grid.isFull()){
                System.out.println("La grille est pleine, Match Nul!");
                this.isOver = true;
            }
            try {
                S.saves(p.number +"," + column);
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }
            System.out.println(grid.toString());
        }
        else{
            System.out.println("Vous quittez la partie.");
        }
    }

    /**
     * Méthode permettant de charger une partie.
     * La méthode replace tous les coups joués stockés dans la sauvegarde
     * et affiche la grille.
     * 
     * @param p
     * @param column
     */
    void replayMove(int p, int column){
        String color = possibleColors.get(p);
        grid.freeCells[column].value = color;
        if (grid.freeCells[column].hasWon()) {
            System.out.println("Le Joueur " + color + " a gagné.");
            this.isOver = true;
        }
        this.grid.freeCells[column] = grid.freeCells[column].up;
        if (this.grid.isFull()){
            System.out.println("La grille est pleine, Match Nul!");
            this.isOver = true;
        }
        System.out.println(grid.toString());
    }
    
    /**
     * Tout d'abord, cette méthode initialise un fichier de sauvegarde (fichier texte).
     * Ensuite, crée la liste de joueurs en appelant la méthode createPlayerList et
     * affiche la grille de jeu.
     * Si personne n'a gagné et que la grille n'est pas pleine, fait jouer les joueurs 
     * chacun leur tour.
     * Finalement, ferme le fichier de sauvegarde et les scanners utilisés.
     */
    public void playGame() {
        try {
            S.initFileWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        this.createPlayersList();
        this.clearScreen();
        System.out.println(grid.toString());
        int playerIndex=0;
        while (this.isOver == false) {        
            Player currentPlayer = playersList.get(playerIndex);
            playMove(currentPlayer, promptColumn(currentPlayer));
            playerIndex = (playerIndex+1)%playersList.size();
        }
        scan.close();
        try {
            S.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant de charger une partie (stockée dans le fichier de sauvegarde)
     * et de continuer à jouer la partie.
     * 
     */
    public void loadGame(){
        try {
            S.initScanner();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        String line = "";
        String save = "";
        int player;
        int column;
        

        try {
            line = S.loads();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        while(line != "EOF"){
            player = Integer.parseInt(line.substring(0, 1));
            column = Integer.parseInt(line.substring(2, 3));
            replayMove(player, column);
            try {
                line = S.loads();
                save += line + "\n";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            S.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int playerIndex = 0;
        Player player1 = new Player(scan, 0, "Joueur 1", possibleColors.get(0));
        Player player2 = new Player(scan, 1, "Joueur 2", possibleColors.get(1));
        LinkedList<Player> listPlayer = new LinkedList<Player>();
        listPlayer.add(player1);
        listPlayer.add(player2);
        Player currentPlayer;
        try {
            S.initFileWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            S.saves(save);
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }

        while(this.isOver == false){
            currentPlayer = listPlayer.get(playerIndex);
            playMove(currentPlayer, promptColumn(currentPlayer));
            playerIndex = (playerIndex+1)%2;
        }
    }

    /**
     * Méthode affichant le titre du jeu et demande à l'utilisateur 
     * s'il veut commencer une partie ou en charger une.
     */
    public void menu(){
        String title = "        __________      .__                                               _____  \n        \\______   \\__ __|__| ______ ___________    ____   ____  ____     /  |  | \n         |     ___/  |  \\  |/  ___//  ___/\\__  \\  /    \\_/ ___\\/ __ \\   /   |  |_\n         |    |   |  |  /  |\\___ \\ \\___ \\  / __ \\|   |  \\  \\__\\  ___/  /    ^   /\n         |____|   |____/|__/____  >____  >(____  /___|  /\\___  >___  > \\____   | \n                                \\/     \\/      \\/     \\/     \\/    \\/       |__|";
        System.out.println(title);

        System.out.println("1 - Jouer une nouvelle partie\n2 - Ouvrir un fichier de partie");
        int choix = scan.nextInt();
        switch(choix){
            case 1:
                playGame();
                break;
            case 2:
                loadGame();
                break;
            default:
                break;
        }
    }
}