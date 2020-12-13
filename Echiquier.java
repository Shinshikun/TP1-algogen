import java.util.Arrays;
import java.lang.*;

public class Echiquier {

	private Cellule[][] echiquier;
	private int taille;
	
	
	public Echiquier(int taille) {
		this.taille = taille;
		echiquier = new Cellule[taille][taille];
		initialiserEchiquier();
	}
	
	public void initialiserEchiquier() {
		for (int x = 0; x < taille; x++) {
			for (int y = 0; y < taille; y++){
				echiquier[x][y] = new Cellule(x, y);
			}
		}
		
	}
	
	public void modifierCellule(int x, int y, int valeur) {
		echiquier[x][y].setTypeOccupation(valeur);
	}
	
	public void placerReine(int x, int y) {
		if(echiquier[x][y].getTypeOccupation() == 0) {
			for(int i=0; i<this.taille; i++) {
				echiquier[i][y].setTypeOccupation(2);
			}
			
			for(int j=0; j<this.taille; j++) {
				echiquier[x][j].setTypeOccupation(2);
			}
			
			int i=0;
			
			while(x-i>=0 && y-i>=0) {
				echiquier[x-i][y-i].setTypeOccupation(2);
				i++;
			}
			i=0;

			while(x+i<this.taille && y+i<this.taille) {
				echiquier[x+i][y+i].setTypeOccupation(2);
				i++;
			}
			i=0;
			
			while(x+i<this.taille && y-i>=0) {
				echiquier[x+i][y-i].setTypeOccupation(2);
				i++;
			}
			i=0;
			
			while(x-i>=0 && y+i<this.taille) {
				echiquier[x-i][y+i].setTypeOccupation(2);
				i++;
			}
			
			echiquier[x][y].setTypeOccupation(1);
		}
	}
	
	
	public int calculCellulesMenacees(int x, int y) {
		
		int numMenacees=0;	
		int i=0;
		
		while(x-i>=0 && y-i>=0) {
			numMenacees++;
			i++;
		}
		i=0;
		numMenacees--;
		
		while(x+i<this.taille && y+i<this.taille) {
			numMenacees++;
			i++;
		}
		i=0;
		numMenacees--;
		
		while(x+i<this.taille && y-i>=0) {
			numMenacees++;
			i++;
		}
		i=0;
		numMenacees--;
		
		while(x-i>=0 && y+i<this.taille) {
			numMenacees++;
			i++;
		}
		numMenacees--;
		
		return numMenacees;
	}
	
	public int[] calculCaseMinMenace() {
		int[] CelluleMin = new int[2];
		int min=100;
		for (int i=0; i<this.taille; i++) {
			for (int j=0; j<this.taille; j++) {
				if(echiquier[i][j].getTypeOccupation()==0) {
					if (calculCellulesMenacees(i, j) < min) {
						CelluleMin[0] = i;
						CelluleMin[1] = j;
					}
				}
			}
		}
		
		return CelluleMin;
	}
	
	public Boolean isEchiquierPlein() {
		for (int i=0; i<this.taille; i++) {
			for (int j=0; j<this.taille; j++) {
				if(echiquier[i][j].getTypeOccupation()==0)
					return false;
			}
		}
		return true;
	}
	
	public void remplissageReines() { //algorithme1
		initialiserEchiquier();
		int numReines=0;
		while(!isEchiquierPlein()) {
			int[] caseMin = calculCaseMinMenace();
			placerReine(caseMin[0], caseMin[1]);
			numReines++;
		}
		System.out.println("Grace à l'algorithme 1, nous avons obtenu " + numReines + " reines.");
	}
	
	public boolean[][] videPossibilitesFilles(int y, boolean[][] echFaux) { //efface toutes les cases de droite apres avoir tenté tous les choix possibles
		
		while(y<this.taille) {
			for(int i=0;i<this.taille; i++) {
				echFaux[i][y] = true;
			}
			y++;
		}
		
		return echFaux;
	}
	
	public int findReine(int y) {
		
		for (int i=0; i<this.taille; i++) {
			if(echiquier[i][y].getTypeOccupation()==1) {
				System.out.println("Reine trouvé : " + i + " " + y);
				return i;
			}
		}
		return -1;
	}
	
	public Boolean isReinePlacable(int x, int y) {
		
		for (int j=0; j<this.taille; j++) { //si il se trouve dans la meme ligne
			if(echiquier[x][j].getTypeOccupation() == 1)
				return false;
		}
		
		for (int i = x, j = y; i >= 0 && j >= 0; i--, j--) 
            if (echiquier[i][j].getTypeOccupation() == 1) 
                return false; 
  
        for (int i = x, j = y; j >= 0 && i < this.taille; i++, j--) 
            if (echiquier[i][j].getTypeOccupation() == 1) 
                return false;
		return true;
	}
	
	public Boolean algorithme2(int numReines, int max) {
		
		if(numReines>=max) {
			String aString = (max==1)?".":"s."; //Choix entre pluriel ou singulier
			System.out.println("A l'aide de l'algorithme 2, nous avions trouvé " + max + " reine" + aString);
			return true;
		}
		
		for (int i=0; i<this.taille; i++) {

			if(echiquier[i][numReines].getTypeOccupation()==0 && isReinePlacable(i, numReines)) {
				echiquier[i][numReines].setTypeOccupation(1);
					
				if (algorithme2(numReines+1, max))
					return true;
				else
					echiquier[i][numReines].setTypeOccupation(0);
			}
		}
		return false;	
	}
	
	public void finalisation() {
		for (int i=0; i<this.taille; i++) {
			for (int j=0; j<this.taille; j++) {
				if(echiquier[i][j].getTypeOccupation()==1) {
					echiquier[i][j].setTypeOccupation(0);
					placerReine(i,j);
				}
			}
		}
	}
	
	public Boolean algo() {
		initialiserEchiquier();
		
	
		int i = 0;
		while(!algorithme2(0, this.taille-i) && this.taille>i) {
			i++;
		}
		
		
		finalisation();
		System.out.println(this.toString());
		
		return true;
	
	}
	
	public String toString() {
		String aString = "";
		for (int i=0; i<this.taille; i++) {
			for (int j=0; j<this.taille; j++) {
				aString += " " + Integer.toString(echiquier[i][j].getTypeOccupation());
			}
			
			aString += "\r\n";
		}
		return aString;
	}
	
}


