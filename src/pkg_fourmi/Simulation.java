package pkg_fourmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Simulation {

	private static Colonie colonie;
	private static Case[][] grille;
	private static int hauteur;
	private static int largeur;
	private static List<Ennemi> listEnnemi;

	public Colonie getColonie() {
		return colonie;
	}

	public static int getHauteur() {
		return hauteur;
	}

	public static int getLargeur() {
		return largeur;
	}
	
	public static List<Ennemi> getListEnnemi() {
		return listEnnemi;
	}

	public void initialisation(int nbTravailleuse, int nbEclaireuse, int nbSoldate, int haut, int larg, int nourMap, int nourColonie ){
		hauteur = haut;
		largeur = larg;
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
					grille[x][y].setInsecte(new Transporteuse(new Coordonnee(x,y), colonie));
				}
			}
		}
		
		
	}
	public static Case[][] getGrille(){
		return grille;
	}

	public void apparitionEnnemi() {

	}
	
	public static void ajouterEnnemi(Ennemi ennemi) {
		Simulation.getListEnnemi().add(ennemi);
		int x = ennemi.getPosition().getX();
		int y = ennemi.getPosition().getY();
		Simulation.getGrille()[x][y].setInsecte(ennemi);
	}

	public static void main(String[] args) {
		
		/**
		 * Ce qui suis est un test !
		 * Si quelqu'un souhaite utiliser son propre main, qu'il mette celui-la en commentaire.
		 */
		
		hauteur = 11;
		largeur = 11;
		listEnnemi = new CopyOnWriteArrayList<Ennemi>();
		grille = new Case[11][11];
		Coordonnee centre = new Coordonnee(((int)11/2),((int)11/2));
		for(int i=0; i < 11;i++){
			for(int j=0; j< 11;j++){
				grille[i][j] = new Case(new Coordonnee(i,j), null, 0, TypeCase.Territoire);
			}
		}
			
		Colonie colonie = new Colonie("Colonie", 50);
		
		Fourmi reine = new Reine(centre, colonie);
		colonie.ajouterFourmi(reine);
		
		Ennemi ennemi1 = new Ennemi(new Coordonnee(0,0)) ;
		ajouterEnnemi(ennemi1);
		
		int nbr_tour = 1;
		
		while ((colonie.getMembres().contains(reine))&&(nbr_tour < 300)) {
			System.out.println("Tour : "+nbr_tour);
			for (Ennemi ennemi : listEnnemi) {
				ennemi.action();
			}
			for (Fourmi fourmi : colonie.getMembres()) {
				System.out.println(fourmi);
				fourmi.action();
			}
			System.out.println("   - Stock de nourriture : "+colonie.getStockNourriture());
			System.out.println("   - Nombre de fourmis : "+colonie.getMembres().size());
			System.out.println("   - Nombres d'ennemis : "+Simulation.getListEnnemi().size());
			System.out.println("  ");
			nbr_tour ++;
		}

	}

}
