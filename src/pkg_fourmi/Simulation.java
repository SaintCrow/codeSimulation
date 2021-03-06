package pkg_fourmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class Simulation {

	private static Colonie colonie;
	private static Case[][] grille;
	private static int hauteur = 100;
	private static int largeur = 100;
	private static int nbr_tour = 1;
	private static boolean continuer = true;
	private static List<Ennemi> listEnnemi = new ArrayList<Ennemi>();
	// Initialisation de l'affichage :
	private static JFrame fenetre = new JFrame();
	private static AffichageCase casesGr = new AffichageCase();
	private static Coordonnee centre = new Coordonnee(((int) largeur / 2), ((int) hauteur / 2));

	/**
	 * getter continuer Cette fonction permet de recuperer le booleen continuer
	 * qui permet de savoir si la simulation continue et de passer au tour
	 * suivant si c'est le cas
	 * 
	 * @return continuer
	 */

	public static boolean isContinuer() {
		return continuer;
	}

	/**
	 * set continuer Cette fonction fait passer au tour suivant de simulation
	 * tant que continuer est vraie et l'arrête quand il devient faux
	 * 
	 * @param continuer
	 */

	public static void setContinuer(boolean continuer) {
		Simulation.continuer = continuer;
	}

	/**
	 * getter colonie
	 * 
	 * @return colonie
	 */

	public static Colonie getColonie() {
		return colonie;
	}

	/**
	 * getter hauteur
	 * 
	 * @return hauteur
	 */

	public static int getHauteur() {
		return hauteur;
	}

	/**
	 * get largeur
	 * 
	 * @return hauteur
	 */

	public static int getLargeur() {
		return largeur;
	}

	/**
	 * get listEnnemi
	 * 
	 * @return listEnnemi
	 */

	public static List<Ennemi> getListEnnemi() {
		return listEnnemi;
	}

	/**
	 * get tour Cette fonction retourne le tour actuel de la simulation
	 * 
	 * @return tour
	 */

	public static int getTour() {
		return nbr_tour;
	}

	/**
	 * Cette fonction ajoute 1 au nombre de tour de la simulation
	 */

	public static void tourPlusPlus() {
		Simulation.nbr_tour += 1;
	}

	/**
	 * constructeur
	 * 
	 * @param nbTravailleuse
	 * @param nbEclaireuse
	 * @param nbSoldate
	 * @param haut
	 * @param larg
	 * @param nourMap
	 * @param nourColonie
	 */

	public static void initialisation(int nbTravailleuse, int nbEclaireuse, int nbSoldate, int nourMap,
			int nourColonie) {
		hauteur = 100;
		largeur = 100;
		grille = new Case[largeur][hauteur];
		fenetre.add(getCasesGr());
		fenetre.setSize(10 * largeur, 10 * hauteur);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);

		// Creation de la carte :
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				if (new Coordonnee(i, j).euclidienne(centre) < 6) {
					grille[i][j] = new Case(new Coordonnee(i, j), null, 0, TypeCase.Fourmiliere);
				} else if (new Coordonnee(i, j).euclidienne(centre) < 40) {
					grille[i][j] = new Case(new Coordonnee(i, j), null, 0, TypeCase.Territoire);
				} else {
					grille[i][j] = new Case(new Coordonnee(i, j), null, 0, TypeCase.Badlands);
				}
			}
		}

		// Ajout de la nourriture sur la carte :
		Random rd = new Random();
		for (int i = 0; i < nourMap; i++) {
			int x = rd.nextInt(largeur);
			int y = rd.nextInt(hauteur);
			while (new Coordonnee(x, y).distance(centre) < 9) {
				x = rd.nextInt(largeur);
				y = rd.nextInt(hauteur);
			}
			grille[x][y].addNourriture(50);
		}

		// Creation de la colonie et des premiers individus :

		colonie = new Colonie("Colonie", nourColonie);

		Fourmi reine = new Reine(centre, colonie);
		colonie.ajouterFourmi(reine);

	}

	/**
	 * get grille
	 * 
	 * @return grille
	 */

	public static Case[][] getGrille() {
		return grille;
	}

	/**
	 * Cette fonction fait apparaître des ennemis dans la simulation si le
	 * nouvel ennemi aux coordonnees aleatoires se situe dans la zone de danger
	 * et qu'aucun insecte ne se trouve deja a cette position Cette fonction
	 * appelle ajouterEnnemi
	 */

	public static void apparitionEnnemi() {
		Random rd = new Random();
		int choixCote = rd.nextInt(4);

		int x;
		int y;

		Coordonnee c;
		if (choixCote == 0) {
			x = 0;
			y = rd.nextInt(hauteur);
			c = new Coordonnee(x, y);
			while (Simulation.getGrille()[x][y].getInsecte() != null) {
				x = 0;
				y = rd.nextInt(hauteur);
			}
		}
		else if (choixCote == 1) {
			x = largeur-1;
			y = rd.nextInt(hauteur);
			c = new Coordonnee(x, y);
			while (Simulation.getGrille()[x][y].getInsecte() != null) {
				x = largeur-1;
				y = rd.nextInt(hauteur);
			}
		}
		else if (choixCote == 2) {
			x = rd.nextInt(largeur);
			y = 0;
			c = new Coordonnee(x, y);
			while (Simulation.getGrille()[x][y].getInsecte() != null) {
				x = rd.nextInt(largeur);
				y = 0;
			}
		}
		else{
			x = rd.nextInt(largeur);
			y = hauteur-1;
			c = new Coordonnee(x, y);
			while (Simulation.getGrille()[x][y].getInsecte() != null) {
				x = rd.nextInt(largeur);
				y = hauteur-1;
			}
		}
		c = new Coordonnee(x, y);
		Ennemi mechant = new Ennemi(c);
		Simulation.ajouterEnnemi(mechant);
	}

	/**
	 * Cette fonction ajoute les nouveaux ennemis crees dans la fonction
	 * precedente a la simulation en ajoutant l'ennemi sur la grille et dans la
	 * liste des ennemis
	 */

	public static void ajouterEnnemi(Ennemi ennemi) {
		Simulation.getListEnnemi().add(ennemi);
		int x = ennemi.getPosition().getX();
		int y = ennemi.getPosition().getY();
		Simulation.getGrille()[x][y].setInsecte(ennemi);
	}

	/**
	 * Cette fonction fait en sorte que les pheromones disparaissent au cours du
	 * temps Les pheromones de danger decroissent de 4 a chaque tour et les
	 * pheromones de nourriture de 1 a chaque tour
	 */

	public static void updatePheromonone() {
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				if (grille[i][j].getPheroNourriture() > 0) {
					grille[i][j].addPheroNourriture(-1);
				}
				if (grille[i][j].getPheroDanger() != 0) {
					grille[i][j].addPheroDanger(Math.max(-4, -grille[i][j].getPheroDanger()));
				}
			}
		}
	}

	/**
	 * Cette fonction initialise tous les parametres de la simulation: cases
	 * (longueur et largeur), insectes, quantité de nourriture de la colonie et
	 * quantité de nourriture dans les sources disseminees sur la carte et fait
	 * en sorte que la simulation s'actualise a chaque tour (et actualise
	 * l'affichage graphique) Cette fonction affiche aussi les informations de
	 * la colonie. Elle fixe enfin le taux d'apparition des ennemis
	 *
	 */

	public static void main(String[] args) {

		// Initialisation de la carte :
		initialisation(0, 0, 0, 40, 100);

		// Debut de la simulation :

		// long time = System.currentTimeMillis();
		long time = System.currentTimeMillis();

		while (isContinuer()) {
			if (!(colonie.getMembres().size() > 0 && colonie.getMembres().get(0) instanceof Reine)) {
				setContinuer(false);
			}

			if (nbr_tour % 5 == 0) {
				colonie.consommation();
			}

			updatePheromonone();

			if (Math.random() > 0.9d && Simulation.listEnnemi.size() < 15) {
				Simulation.apparitionEnnemi();
			}

			Simulation.tourPlusPlus();
			for (Ennemi ennemi : listEnnemi) {
				ennemi.action();
			}
			for (Fourmi fourmi : colonie.getMembres()) {
				fourmi.action();
			}

			while (System.currentTimeMillis() - time < 100) {

			}
			time = System.currentTimeMillis();

			casesGr.setGrille(grille);
			casesGr.repaint();

		}

	}

	public static AffichageCase getCasesGr() {
		return casesGr;
	}

	public static void setCasesGr(AffichageCase casesGr) {
		Simulation.casesGr = casesGr;
	}

}
