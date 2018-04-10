package pkg_fourmi;

import java.util.Random;

public class Simulation {

	private Colonie colonie;
	private Case[][] grille;

	public Colonie getColonie() {
		return colonie;
	}

	public void initialisation(int nbTravailleuse, int nbEclaireuse, int nbSoldate, int hauteur, int largeur, int nourMap, int nourColonie ){
		this.grille = new Case[largeur][hauteur];
		Random rd = new Random();
		if(rd.nextBoolean()){
			
		}
		for(int i=0; i < largeur;i++){
			
			for(int j=0; j< hauteur;j++){
				grille[i][j] = new Case(new Coordonnee(i,j),null,);
			}
			
		}
		
	}

	public void apparitionEnnemi() {

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
