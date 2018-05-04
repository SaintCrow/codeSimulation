package pkg_fourmi;

import java.util.ArrayList;

public abstract class Insecte {

	private Coordonnee position;
	private Nom nom;
	private Prenom prenom;
	private int champvision;
	private int endurance;
	private int force;

	public Insecte(Coordonnee position, Nom nom, Prenom prenom, int champvision, int endurance, int force) {
		this.position = position;
		this.nom = nom;
		this.prenom = prenom;
		this.champvision = champvision;
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
	 * @param c
	 */
	public void deplacement(Coordonnee c){
		Simulation.getGrille()[this.position.getX()][this.position.getY()].setInsecte(null);
		this.position = c;
		Simulation.getGrille()[this.position.getX()][this.position.getY()].setInsecte(this);
	}
	
	public Coordonnee allerA(Coordonnee c){
		int insecteX = this.position.getX();
		int insecteY = this.position.getY();
		ArrayList<Coordonnee> posiPossible = new ArrayList<Coordonnee>();
		if (insecteX < c.getX()){
			posiPossible.add(new Coordonnee(insecteX+1,insecteY));
		}
		if (insecteX > c.getX()){
			posiPossible.add(new Coordonnee(insecteX-1,insecteY));
		}
		if (insecteY < c.getY()){
			posiPossible.add(new Coordonnee(insecteX,insecteY+1));
		}
		if (insecteY > c.getY()){
			posiPossible.add(new Coordonnee(insecteX,insecteY-1));
		}
		for (Coordonnee position : posiPossible){
			if (Simulation.getGrille()[position.getX()][position.getY()].getInsecte() == null){
				return position;
			}
		}
		return this.position;
	}
	
	public Coordonnee allerAleatoire(){
		int insecteX = this.position.getX();
		int insecteY = this.position.getY();
		ArrayList<Coordonnee> posiPossible = new ArrayList<Coordonnee>();
		posiPossible.add(new Coordonnee(insecteX+1,insecteY));
		posiPossible.add(new Coordonnee(insecteX-1,insecteY));
		posiPossible.add(new Coordonnee(insecteX,insecteY+1));
		posiPossible.add(new Coordonnee(insecteX,insecteY-1));
		if (this instanceof Soldate) {
			for (Coordonnee position : posiPossible){
				if (Simulation.getGrille()[position.getX()][position.getY()].getType() == TypeCase.Badlands){
					posiPossible.remove(position);
				}
			}
		}
		for (Coordonnee position : posiPossible){
			if (Simulation.getGrille()[position.getX()][position.getY()].getInsecte() != null){
				posiPossible.remove(position);
			}
		}
		if (posiPossible.size() != 0){
			int indice = (int) (Math.random()*posiPossible.size());
			return posiPossible.get(indice);
		}
		else {
			return this.position;
		}
	}

	public abstract void action();

}
