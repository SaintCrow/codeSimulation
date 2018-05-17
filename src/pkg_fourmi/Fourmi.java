package pkg_fourmi;

import java.util.ArrayList;

public abstract class Fourmi extends Insecte {

	private Colonie colonie;
	
	/**
	 * Constructeur
	 * 
	 * @param position
	 * @param endurance
	 * @param force
	 * @colonie
	 */

	public Fourmi(Coordonnee position, int endurance, int force, Colonie colonie) {
		super(position, endurance, force);
		this.colonie = colonie;
	}
	
	/**
	 *@getter
	 *
	 *@return colonie
	 */

	public Colonie getColonie() {
		return colonie;
	}
	
	/**
	 * Cette fonction recherche les ennemis en parcourant la liste des ennemis pour trouver ceux dont les coordonnées 
	 * sont dans le champ de vision de la fourmi et en les mettant dans une liste.
	 * Ensuite, si la liste est vide, la fourmi ne trouve pas d'ennemis.
	 *  Si la liste ne contient qu'un seul ennemi, la fourmi le trouve et si elle en contient plusieurs, elle recherche 
	 *  le plus proche grâce à la fonction distance et le renvoie
	 */

	public Coordonnee rechercheEnnemi() {
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		ArrayList<Coordonnee> listEnnemi = new ArrayList<Coordonnee>();
		for (int i = x - getChampvision(); i <= x + this.getChampvision(); i++) {
			for (int j = y - getChampvision(); j <= y + this.getChampvision(); j++) {
				Coordonnee position = new Coordonnee(i, j);
				if ((position.estCorrecte()) && (Simulation.getGrille()[i][j].getInsecte() instanceof Ennemi)) {
					listEnnemi.add(Simulation.getGrille()[i][j].getPosition());
				}
			}
		}
		if (listEnnemi.size() == 0) {
			return null;
		} else if (listEnnemi.size() == 1) {
			return listEnnemi.get(0);
		} else {
			Coordonnee coordPlusProche = listEnnemi.get(0);
			for (int i = 1; i < listEnnemi.size(); i++) {
				if (coordFourmi.distance(listEnnemi.get(i)) < coordFourmi.distance(coordPlusProche)) {
					coordPlusProche = listEnnemi.get(i);
				}
			}
			return coordPlusProche;
		}
	}
	

	/**
	 * Cette fonction permet à la fourmi de poser une phéromone de danger de forme circulaire autour d'elle
	 * L'intensité décroit lorsqu'on s'écarte du centre du cercle
	 * 
	 */

	public void poserPheromoneDanger() {
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		for (int i = x - 4; i <= x + 4; i++) {
			for (int j = y - 4; j <= y + 4; j++) {
				if (new Coordonnee(i, j).euclidienne(coordFourmi) <= 4) {
					Simulation.getGrille()[i][j].addPheroDanger(10);
				}
				if (new Coordonnee(i, j).euclidienne(coordFourmi) <= 3) {
					Simulation.getGrille()[i][j].addPheroDanger(10);
				}
				if (new Coordonnee(i, j).euclidienne(coordFourmi) <= 2) {
					Simulation.getGrille()[i][j].addPheroDanger(10);
				}
				if (new Coordonnee(i, j).euclidienne(coordFourmi) <= 1) {
					Simulation.getGrille()[i][j].addPheroDanger(10);
				}
			}
		}
	}
	
	/**
	 * Cette fonction permet de rechercher une pheromone de danger dans le champ de vision de la fourmi
	 * 
	 * 
	 *@return null si aucune phéromone de danger n'a ete trouvee 
	 *@return listPheromone.get(0) dans le cas ou une seule phéromone de danger a été trouvée
	 *@return coordPlusProche dans le cas ou plusieurs pheromones de danger ont ete detectees, on retourne alors
	 *la pheromone la plus proche de la fourmi
	 */

	public Coordonnee recherchePheromoneDanger() {
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		ArrayList<Coordonnee> listPheromone = new ArrayList<Coordonnee>();
		for (int i = x - getChampvision(); i <= x + this.getChampvision(); i++) {
			for (int j = y - getChampvision(); j <= y + this.getChampvision(); j++) {
				if ((new Coordonnee(i, j).estCorrecte()) && (Simulation.getGrille()[i][j].getPheroDanger() != 0)) {
					listPheromone.add(Simulation.getGrille()[i][j].getPosition());
				}
			}
		}
		if (listPheromone.size() == 0) {
			return null;
		} else if (listPheromone.size() == 1) {
			return listPheromone.get(0);
		} else {
			int xPhero = listPheromone.get(0).getX();
			int yPhero = listPheromone.get(0).getY();
			int intenMax = Simulation.getGrille()[xPhero][yPhero].getPheroDanger();
			ArrayList<Coordonnee> listPheromonePlusForte = new ArrayList<Coordonnee>();
			for (int i = 0; i < listPheromone.size(); i++) {
				xPhero = listPheromone.get(i).getX();
				yPhero = listPheromone.get(i).getY();
				if (Simulation.getGrille()[xPhero][yPhero].getPheroDanger() > intenMax) {
					intenMax = Simulation.getGrille()[xPhero][yPhero].getPheroDanger();
					listPheromonePlusForte.clear();
					listPheromonePlusForte.add(listPheromone.get(i));
				}
				if (Simulation.getGrille()[xPhero][yPhero].getPheroDanger() == intenMax) {
					listPheromonePlusForte.add(listPheromone.get(i));
				}
			}
			Coordonnee coordPlusProche = listPheromonePlusForte.get(0);
			for (int i = 1; i < listPheromonePlusForte.size(); i++) {
				if (coordFourmi.distance(listPheromonePlusForte.get(i)) < coordFourmi.distance(coordPlusProche)) {
					coordPlusProche = listPheromonePlusForte.get(i);
				}
			}
			return coordPlusProche;
		}

	}
	

	/**
	 *Cette fonction fonctionne de la meme maniere que les deux autres fonctions de recherche, mais sert a rechercher
	 *les pheromones de nourriture
	 */
	
	public Coordonnee recherchePheromoneNourriture(){
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		ArrayList<Coordonnee> listPheromone = new ArrayList<Coordonnee>();
		for (int i = x - getChampvision(); i <= x + this.getChampvision(); i++) {
			for (int j = y - getChampvision(); j <= y + this.getChampvision(); j++) {
				if ((new Coordonnee(i, j).estCorrecte()) && (Simulation.getGrille()[i][j].getPheroDanger() != 0)) {
					listPheromone.add(Simulation.getGrille()[i][j].getPosition());
				}
			}
		}
		if (listPheromone.size() == 0) {
			return null;
		} else if (listPheromone.size() == 1) {
			return listPheromone.get(0);
		} else {
			int xPhero = listPheromone.get(0).getX();
			int yPhero = listPheromone.get(0).getY();
			int intenMax = Simulation.getGrille()[xPhero][yPhero].getPheroDanger();
			ArrayList<Coordonnee> listPheromonePlusForte = new ArrayList<Coordonnee>();
			for (int i = 0; i < listPheromone.size(); i++) {
				xPhero = listPheromone.get(i).getX();
				yPhero = listPheromone.get(i).getY();
				if (Simulation.getGrille()[xPhero][yPhero].getPheroDanger() > intenMax) {
					intenMax = Simulation.getGrille()[xPhero][yPhero].getPheroDanger();
					listPheromonePlusForte.clear();
					listPheromonePlusForte.add(listPheromone.get(i));
				}
				if (Simulation.getGrille()[xPhero][yPhero].getPheroDanger() == intenMax) {
					listPheromonePlusForte.add(listPheromone.get(i));
				}
			}
			Coordonnee coordPlusProche = listPheromonePlusForte.get(0);
			for (int i = 1; i < listPheromonePlusForte.size(); i++) {
				if (coordFourmi.distance(listPheromonePlusForte.get(i)) < coordFourmi.distance(coordPlusProche)) {
					coordPlusProche = listPheromonePlusForte.get(i);
				}
			}
			return coordPlusProche;
		}
	}
	
	/**
	 * Cette fonction code la mort pour les fourmis en les effaçant de la liste des membres de la colonie.
	 */

	@Override
	public void mourir() {
		super.mourir(); 
		this.getColonie().getMembres().remove(this);

	}
}
