package pkg_fourmi;

import java.util.Random;

public class Simulation {

	private static Colonie colonie;
	private static Case[][] grille;

	public Colonie getColonie() {
		return colonie;
	}

	public void initialisation(int nbTravailleuse, int nbEclaireuse, int nbSoldate, int hauteur, int largeur, int nourMap, int nourColonie ){
		
		grille = new Case[largeur][hauteur];
		Coordonnee centre = new Coordonnee(((int)largeur/2),((int)hauteur/2));
		Random rd = new Random();
		int nour = 0;
		TypeCase type ;
		Coordonnee c;
		
		for(int i=0; i < largeur;i++){
			for(int j=0; j< hauteur;j++){
				
				if(rd.nextBoolean()){
					nour = (int) (nourMap*rd.nextFloat());
					nourMap-=nour;					
				}
				
				c = new Coordonnee(i,j);
				if(c.euclidienne(centre)<=10){
					type = TypeCase.Fourmiliere;
				}
				else if(c.euclidienne(centre)<=30){
					type = TypeCase.Territoire;
				}
				else{
					type = TypeCase.Badlands;
				}
				grille[i][j] = new Case(c,null,nour,type);
				nour = 0;
			}
			
		}
		// depose le reste de nourriture au hasard
		if(nourMap>0){
			int x_depot = (int) (rd.nextFloat()*largeur);
			int y_depot = (int) (rd.nextFloat()*hauteur);
			grille[x_depot][y_depot].addNourriture(nourMap);
		}
		while(nbTravailleuse>0){
			int x = (int)(rd.nextFloat()*largeur);
			int y = (int)(rd.nextFloat()*hauteur);
			if(grille[x][y].getType() == TypeCase.Fourmiliere){
				if(grille[x][y].getInsecte() == null){
					grille[x][y].setInsecte(new Transporteuse(new Coordonnee(x,y), null, null, y, y, y, false, false));
				}
			}
		}
		
		
	}
	public static Case[][] getGrille(){
		return grille;
	}

	public void apparitionEnnemi() {

	}

	public static void main(String[] args) {
		

	}

}
