package pkg_fourmi;

import java.util.ArrayList;
import java.util.Random;

public abstract class Insecte {

	private Coordonnee position;
	private Nom nom;
	private Prenom prenom;
	private int champvision;
	private int endurance;
	private int force;

	public Insecte(Coordonnee position, int endurance, int force) {
		Random rd = new Random();
		Nom[] listNom = Nom.values();
		Prenom[] listPrenom = Prenom.values();
		Nom nom = listNom[rd.nextInt(listNom.length)];
		Prenom prenom = listPrenom[rd.nextInt(listPrenom.length)];
		this.position = position;
		this.nom = nom;
		this.prenom = prenom;
		this.champvision = 4;
		this.endurance = endurance;
		this.force = force;
	}
	
	public Coordonnee getPosition() {
		return position;
	}
	
	public void setPosition(Coordonnee position) {
		this.position = position;
	}

	public Nom getNom() {
		return nom;
	}

	public void setNom(Nom nom) {
		this.nom = nom;
	}

	public Prenom getPrenom() {
		return prenom;
	}

	public void setPrenom(Prenom prenom) {
		this.prenom = prenom;
	}

	public int getChampvision() {
		return champvision;
	}

	public void setChampvision(int champvision) {
		this.champvision = champvision;
	}

	public int getEndurance() {
		return endurance;
	}

	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	/**
	 * deplace l'insecte( change sa coordonnee
	 * 
	 * @param c
	 */
	public void deplacement(Coordonnee c) {
		if (c.estCorrecte()) {
			Simulation.getGrille()[this.position.getX()][this.position.getY()].setInsecte(null);
			this.position = c;
			Simulation.getGrille()[this.position.getX()][this.position.getY()].setInsecte(this);
		}
	}

	public void attaquer(Coordonnee position) {
		int x = position.getX();
		int y = position.getY();
		Insecte insecte = Simulation.getGrille()[x][y].getInsecte();
		insecte.setEndurance(Math.max(0, insecte.getEndurance() - this.getForce()));
		System.out.println(this.toString() + " a attaque " + insecte.toString() + ".");
		if (insecte.getEndurance() == 0) {
			insecte.mourir();
		}
	}

	public void mourir() {
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		Simulation.getGrille()[x][y].setInsecte(null);
		System.out.println(this.toString() + " est decedee.");

	}

	public Coordonnee allerA(Coordonnee c) {
		int insecteX = this.position.getX();
		int insecteY = this.position.getY();
		ArrayList<Coordonnee> posiPossible = new ArrayList<Coordonnee>();
		if (insecteX < c.getX()) {
			posiPossible.add(new Coordonnee(insecteX + 1, insecteY));
		}
		if (insecteX > c.getX()) {
			posiPossible.add(new Coordonnee(insecteX - 1, insecteY));
		}
		if (insecteY < c.getY()) {
			posiPossible.add(new Coordonnee(insecteX, insecteY + 1));
		}
		if (insecteY > c.getY()) {
			posiPossible.add(new Coordonnee(insecteX, insecteY - 1));
		}
		for (Coordonnee position : posiPossible) {
			if ((position.estCorrecte())
					&& (Simulation.getGrille()[position.getX()][position.getY()].getInsecte() == null)) {
				return position;
			}
		}
		return this.position;
	}
	public ArrayList<Coordonnee> caseVacantes(){
		int insecteX = this.position.getX();
		int insecteY = this.position.getY();
		ArrayList<Coordonnee> posiPossible = new ArrayList<Coordonnee>();
		posiPossible.add(new Coordonnee(insecteX + 1, insecteY));
		posiPossible.add(new Coordonnee(insecteX - 1, insecteY));
		posiPossible.add(new Coordonnee(insecteX, insecteY + 1));
		posiPossible.add(new Coordonnee(insecteX, insecteY - 1));

		ArrayList<Coordonnee> listPosition = new ArrayList<Coordonnee>();

		for (Coordonnee position : posiPossible) {
			if (position.estCorrecte() && (Simulation.getGrille()[position.getX()][position.getY()].getInsecte() == null)) {
				listPosition.add(position);
			}
		}
		return listPosition;
	}
	public Coordonnee allerAleatoire() {
		
		ArrayList<Coordonnee> listPosition = this.caseVacantes();
		
		if (listPosition.size() != 0) {
			int indice = (int) (Math.random() * listPosition.size());
			return listPosition.get(indice);
		} else {
			return this.position;
		}
	}

	public abstract void action();

}
