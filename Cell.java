package projet;

/**
 * Classe permettant de créer une cellule, de la lier à ses voisins (s'ils existent) 
 * et de lui attribuer une valeur (un émoji "rond blanc").
 */
public class Cell {

    Cell left;
    Cell right;
    Cell up;
    Cell down;

    String value;

    public Cell() {
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
        this.value = "\u26AA";
    }

    /**
     * Lie la cellule courante à la cellule à sa droite dans la grille
     * et vice-versa
     * 
     * @param C
     */
    void setRight(Cell C) {
        C.left = this;
        this.right = C;
    }

    /**
     * Lie la cellule courante à la cellule au-dessus d'elle dans la grille
     * et vice-versa
     * 
     * @param C
     */
    void setUp(Cell C) {
        C.down = this;
        this.up = C;
    }

    /**
     * Lie la cellule courante à la cellule en-dessous d'elle dans la grille
     * et vice-versa
     * 
     * @param C
     */
    void setDown(Cell C) {
        C.up = this;
        this.down = C;
    }

    /**
     * Vérifie si quatre jetons de la même couleur sont alignés (dans n'importe quelle sens)
     * et retourne true si c'est le cas. Renvoie false sinon.
     * 
     * @return
     */
    public boolean hasWon() {
        if (checkDown(0) > 2 ||
            checkRight(0) + checkLeft(0) > 2 ||
            checkUpRight(0) + checkDownLeft(0) > 2 ||
            checkUpLeft(0) + checkDownRight(0) > 2) {
            return true;
        }
        return false;
    }

    /**
     * Toutes les méthodes "check" comptent le nombre de jetons de la 
     * même couleur alignés sur une trajectoire et renvoient ce nombre.
     * 
     * @param nb
     * @return
     */
    private int checkUpRight(int nb) {
        if (this.up != null && this.up.right != null) {
            if (this.up.right.value == this.value) {
                nb++;
                return this.up.right.checkUpRight(nb);
            }
        }
        return nb;
    }

    private int checkRight(int nb) {
        if (this.right != null) {
            if (this.right.value == this.value) {
                nb++;
                return this.right.checkRight(nb);
            }
        }
        return nb;
    }

    private int checkDownRight(int nb) {
        if (this.down != null && this.down.right != null) {
            if (this.down.right.value == this.value) {
                nb++;
                return this.down.right.checkDownRight(nb);
            }
        }
        return nb;
    }

    private int checkDown(int nb) {
        if (this.down != null) {
            if (this.down.value == this.value) {
                nb++;
                return this.down.checkDown(nb);
            }
        }
        return nb;
    }

    private int checkDownLeft(int nb) {
        if (this.down != null && this.down.left != null) {
            if (this.down.left.value == this.value) {
                nb++;
                return this.down.left.checkDownLeft(nb);
            }
        }
        return nb;
    }

    private int checkLeft(int nb) {
        if (this.left != null) {
            if (this.left.value == this.value) {
                nb++;
                return this.left.checkLeft(nb);
            }
        }
        return nb;
    }

    private int checkUpLeft(int nb) {
        if (this.up != null && this.up.left != null) {
            if (this.up.left.value == this.value) {
                nb++;
                return this.up.left.checkUpLeft(nb);
            }
        }
        return nb;
    }


    /**
     * toString affiche la grille de manière récursive.
     * Selon la position de la cellule courante, on affiche la grille avec les caractères unicode correspondant.
     * On affiche également un émoji de couleur correspondant au champs value de la cellule courante.
     */
    @Override
    public String toString() {
        // La cellule renvoit d'abord sa propre valeur avec une barre verticale à gauche
        String result = "\u2502 ";
        result += this.value;
        // Si elle n'est pas sur le bord droit, elle renvoit aussi la valeur de son
        // voisin de droite
        if (this.right != null) {
            result += " " + this.right.toString();
        }
        // Si on est sur le bord droit :
        else {
            // On affiche une barre verticale à droite
            result += " \u2502";
            // On saute une ligne
            result += "\n";
            // Si on est sur la ligne du bas :
            if(this.down == null){
                // On affiche une ligne horizontale sans les traits vers le bas
                result += "\u2514";
                for(int i=0; i<6; i++){
                    result += "\u2500" + "\u2500" + "\u2500" + "\u2500";
                    result += "\u2534";
                }
                result += "\u2500" + "\u2500" + "\u2500" + "\u2500";
                result += "\u2518";
            }
            // Sinon :
            else{
                // On affiche une ligne horizontale avec les traits vers le bas
                result += "\u251C";
                for(int i=0; i<6; i++){
                    result += "\u2500" + "\u2500" + "\u2500" + "\u2500";
                    result += "\u253c";
                }
                result += "\u2500" + "\u2500" + "\u2500" + "\u2500";
                result += "\u2524";
            }
            // On re-saute une ligne
            result += "\n";
        }
        // Si elle est sur le bord gauche mais pas sur la ligne du bas, elle renvoit la
        // valeur de son voisin du dessous
        if (this.left == null && this.down != null) {
            result += this.down.toString();
        }
        return result;
    }
}