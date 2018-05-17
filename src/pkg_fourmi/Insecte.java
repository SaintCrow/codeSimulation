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
	
	/**
	 *correcteur 
	 * 
	 * @param position
	 * @param endurance
	 * @param force
	 */

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
	
	/**
	 *getter position
	 * 
	 *@return position
	 */
	
	public Coordonnee getPosition() {
		return position;
	}
	
	/**
	 *set position
	 * 
	 *@param position
	 */
	
	public void setPosition(Coordonnee position) {
		this.position = position;
	}
	
	/**
	 *getter nom
	 * 
	 *@return nom
	 */


	public Nom getNom() {
		return nom;
	}
	

	/**
	 *set nom
	 * 
	 *@param nom
	 */

	public void setNom(Nom nom) {
		this.nom = nom;
	}
	
	/**
	 *getter prenom
	 * 
	 *@return prenom
	 */

	public Prenom getPrenom() {
		return prenom;
	}
	
	/**
	 *set prenom
	 * 
	 *@param prenom
	 */

	public void setPrenom(Prenom prenom) {
		this.prenom = prenom;
	}
	
	/**
	 *getter champvision
	 * 
	 *@return champvision
	 */

	public int getChampvision() {
		return champvision;
	}
	
	/**
	 *set champvision
	 * 
	 *@param champvision
	 */

	public void setChampvision(int champvision) {
		this.champvision = champvision;
	}
	
	/**
	 *getter endurance
	 * 
	 *@return position
	 */

	public int getEndurance() {
		return endurance;
	}
	

	/**
	 *set endurance
	 * 
	 *@param endurance
	 */


	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}
	
	/**
	 *getter force
	 * 
	 *@return force
	 */

	public int getForce() {
		return force;
	}
	
	/**
	 *set force
	 * 
	 *@param force
	 */

	public void setForce(int force) {
		this.force = force;
	}

	/**
	 * deplace l'insecte c'est-a-dire change sa coordonnee
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
	
	/**
	 *fonction d'attaque qui recupere la position de la cible et diminue son endurance de la valeur de la force de l'attaquant
	 *Cette fonction renvoie egalment un message d'attaque et active la fonction de mort si l'endurance de l'insecte est reduite a zero
	 *@param position
	 */

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
	
	/**
	 * fait mourir l'insecte en question en l'effaçant de la grille apres avoir recupere sa position
	 * et affiche un message de deces
	 */

	public void mourir() {
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		Simulation.getGrille()[x][y].setInsecte(null);
		System.out.println(this.toString() + " est decedee.");

	}
	
	/**
	 * deplace l'insecte c'est-a-dire change sa coordonnee
	 * cette fonction fait d'abord la liste des positions possibles pour la fourmi
	 * @param c
	 * @return position
	 */


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
	
	/**
	 * Cette fonction retourne une liste de toutes les positions possibles ou l'insecte opurra se deplacer,
	 * c'est-a-dire la liste des cases vacantes autour de de lui
	 * @return position
	 */
	
	
	
	
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
	
	/**
	 * deplace l'insecte c'est-a-dire change sa coordonnee
	 * Cette fonction choisit aleatoirement la nouvelle position de l'insecte parmi la liste des positions possibles 
	 * 
	 * @param position
	 */
	
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
