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
		Coordonnee centre = new Coordonnee(((int)largeur/2),((int)hauteur/2));
		Random rd = new Random();
		int nour = 0;
		TypeCase type ;
		for(int i=0; i < largeur;i++){
			for(int j=0; j< hauteur;j++){
				
				if(rd.nextBoolean()){
					nour = (int) (nourMap*rd.nextFloat());
					nourMap-=nour;
					
				}
				grille[i][j] = new Case(new Coordonnee(i,j),null,nour,);
				nour = 0;
			}
			
		}
		// depose le reste de nourriture au hasard
		if(nourMap>0){
			int x_depot = (int) (rd.nextFloat()*largeur);
			int y_depot = (int) (rd.nextFloat()*hauteur);
			grille[x_depot][y_depot].addNourriture(nourMap);
		}
		
		
	}
	public Case[][] getGrille(){
		return this.grille;
	}

	public void apparitionEnnemi() {

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
