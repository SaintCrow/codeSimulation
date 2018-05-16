package pkg_fourmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

public class Simulation {

	private static Colonie colonie;
	private static Case[][] grille;
	private static int hauteur;
	private static int largeur;
	private static int nbr_tour = 1;
	private static boolean continuer = true;
	private static List<Ennemi> listEnnemi = new ArrayList<Ennemi>();

	public static boolean isContinuer() {
		return continuer;
	}

	public static void setContinuer(boolean continuer) {
		Simulation.continuer = continuer;
	}

	public static Colonie getColonie() {
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

	public static int getTour() {
		return nbr_tour;
	}

	public static void tourPlusPlus() {
		Simulation.nbr_tour += 1;
	}

	public static void initialisation(int nbTravailleuse, int nbEclaireuse, int nbSoldate, int haut, int larg, int nourMap, int nourColonie){
		hauteur = haut;
		largeur = larg;
		grille = new Case[largeur][hauteur];
		Coordonnee centre = new Coordonnee(((int)largeur/2),((int)hauteur/2));
		
		//Creation de la carte :
		for(int i=0; i < largeur;i++){
			for(int j=0; j < hauteur;j++){
				if (new Coordonnee(i,j).euclidienne(centre) < 6){
					grille[i][j] = new Case(new Coordonnee(i,j), null, 0, TypeCase.Fourmiliere);
				}
				else if (new Coordonnee(i,j).euclidienne(centre) < 40){
					grille[i][j] = new Case(new Coordonnee(i,j), null, 0, TypeCase.Territoire);
				}
				else {
					grille[i][j] = new Case(new Coordonnee(i,j), null, 0, TypeCase.Badlands);
				}
			}
		}
		
		//Ajout de la nourriture sur la carte :
		Random rd = new Random();
		for (int i = 0; i < nourMap; i++){
			int x = rd.nextInt(largeur);
			int y = rd.nextInt(hauteur);
			while (new Coordonnee(x,y).distance(centre) < 9){
				x = rd.nextInt(largeur);
				y = rd.nextInt(hauteur);
			}
			grille[x][y].addNourriture(50);
		}
		
		//Creation de la colonie et des premiers individus :

		colonie = new Colonie("Colonie", nourColonie);
		
		Fourmi reine = new Reine(centre, colonie);
		colonie.ajouterFourmi(reine);
		
		Ennemi ennemi1 = new Ennemi(new Coordonnee(0,0)) ;
		ajouterEnnemi(ennemi1);
		
	}
	
	public static Case[][] getGrille(){
		return grille;
	}

	public static void apparitionEnnemi() {
		Random rd = new Random();
		int x = rd.nextInt(largeur);
		int y = rd.nextInt(hauteur);
		
		Coordonnee c = new Coordonnee(x,y);
		
		while(Simulation.grille[x][y].getType()!=TypeCase.Badlands && Simulation.getGrille()[x][y].getInsecte()!=null){
			x = rd.nextInt(largeur);
			y = rd.nextInt(hauteur);
			c = new Coordonnee(x,y);
			
		}
		Ennemi mechant = new Ennemi(c);
		Simulation.ajouterEnnemi(mechant);
	}
	
	public static void ajouterEnnemi(Ennemi ennemi) {
		Simulation.getListEnnemi().add(ennemi);
		int x = ennemi.getPosition().getX();
		int y = ennemi.getPosition().getY();
		Simulation.getGrille()[x][y].setInsecte(ennemi);
	}
	
	public static void updatePheromonone(){
		for (int i = 0; i < largeur; i++){
			for (int j = 0; j < hauteur; j++){
				if (grille[i][j].getPheroNourriture() > 0){
					grille[i][j].addPheroNourriture(-1);
				}
				if (grille[i][j].getPheroDanger() != 0){
					grille[i][j].addPheroDanger(Math.max(-4,-grille[i][j].getPheroDanger()));
				}
			}
		}
	}

	public static void main(String[] args) {
		
		//Saisi des parametres de la simulation :
		Scanner scan = new Scanner(System.in);
		System.out.println("Hauteur de la carte ?");
		int haut = 100;
		System.out.println("Largeur de la carte ?");
		int larg = 100;
		System.out.println("Nombre de points de nourriture ?");
		int nourMap = 100;
		System.out.println("Quantite de nourriture de la colonie ?");
		int nourColonie =100;
		scan.close();
		
		//Initialisation de la carte :
		initialisation(0, 0, 0, haut, larg, nourMap, nourColonie);

		//Initialisation de l'affichage :
		JFrame fenetre = new JFrame();
		AffichageCase cases = new AffichageCase();
		fenetre.add(cases);
		fenetre.setSize(10*largeur, 10*hauteur);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
		
		//Debut de la simulation :
		
		
		//long time = System.currentTimeMillis();
		long time = System.currentTimeMillis();

		
		while (isContinuer()) {
			if(!(colonie.getMembres().size()>0 && colonie.getMembres().get(0) instanceof Reine)){
				setContinuer(false);
			}
			System.out.println("Tour : "+Simulation.getTour());
			for (Ennemi ennemi : listEnnemi) {
				ennemi.action();
			}
			for (Fourmi fourmi : colonie.getMembres()) {
				fourmi.action();
			}
			
			colonie.consommation();
			updatePheromonone();
			
			if(Math.random()>0.9d && Simulation.listEnnemi.size()<15){
				Simulation.apparitionEnnemi();
			}
			
			System.out.println("   - Stock de nourriture : "+colonie.getStockNourriture());
			System.out.println("   - Nombre de fourmis : "+colonie.getMembres().size());
			System.out.println("   - Nombres d'ennemis : "+Simulation.getListEnnemi().size());
			System.out.println("  ");
			Simulation.tourPlusPlus();
			
			while (System.currentTimeMillis() - time < 100){
				
			}
			time = System.currentTimeMillis();
			
			cases.setGrille(grille);
			cases.repaint();
			
		}
		


	}

}
