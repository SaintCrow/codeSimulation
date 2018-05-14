package pkg_fourmi;

import java.util.ArrayList;

public abstract class Fourmi extends Insecte {

	private Colonie colonie;

	public Fourmi(Coordonnee position, int endurance, int force, Colonie colonie) {
		super(position, endurance, force);
		this.colonie = colonie;
	}

	public Colonie getColonie() {
		return colonie;
	}

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

	public void poserPheromoneDanger() {
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		for (int i = x - 4; i <= x + 4; i++) {
			for (int j = y - 4; j <= y + 4; j++) {
				if (new Coordonnee(i, j).distance(coordFourmi) <= 4) {
					Simulation.getGrille()[x][y].addPheroDanger(5);
				}
				if (new Coordonnee(i, j).distance(coordFourmi) <= 3) {
					Simulation.getGrille()[x][y].addPheroDanger(5);
				}
				if (new Coordonnee(i, j).distance(coordFourmi) <= 2) {
					Simulation.getGrille()[x][y].addPheroDanger(5);
				}
				if (new Coordonnee(i, j).distance(coordFourmi) <= 1) {
					Simulation.getGrille()[x][y].addPheroDanger(5);
				}
			}
		}
	}

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

	@Override
	public void mourir() {
		super.mourir(); 
		this.getColonie().getMembres().remove(this);

	}
}
