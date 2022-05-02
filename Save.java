package projet;

import java.io.*;
import java.util.Scanner;

/**
 * Classe permettant de sauvegarder les coups joués par les joueurs 
 * dans un fichier texte et de les lire.
 */
public class Save {

    FileWriter fw;
    Scanner scan;

    public Save(){
    }

    public void initFileWriter() throws IOException{
        this.fw = new FileWriter("save.txt");
    }

    public void initScanner() throws FileNotFoundException{
        this.scan = new Scanner(new File("save.txt"));
    }

    /**
     * Ecrit la String passée en paramètre dans un fichier texte
     * 
     * @param column
     * @throws NumberFormatException
     * @throws IOException
     */
    public void saves(String toWrite) throws NumberFormatException, IOException{ 
        fw.write(toWrite + "\n");
        fw.flush();
    }

    /**
     * Méthode permettant de fermer un fichier ou un scanner si ces-derniers existent
     * 
     * @throws IOException
     */
    public void close() throws IOException{
        if(fw != null){
            fw.close();
        }
        if(scan != null){
            scan.close();
        }
    }

    /**
     * Retourne une String contenue dans une ligne du fichier
     * si elle existe.
     * Retourne une String contenant EOF sinon.
     * 
     * @return 
     * @throws IOException
     */
    public String loads() throws IOException{
        if(scan.hasNextLine()){
            return scan.nextLine();
        }
        return "EOF";
    }
}