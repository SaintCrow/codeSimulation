package pkg_fourmi;

public class Coordonnee {
	private int x;
	private int y;

	/**
	 * constructeur
	 * 
	 * @param x
	 * @param y
	 */
	public Coordonnee(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Cette fonction calcule la distance (en case de la grille) entre un objet 
	 * pris en param�tre et l'insecte qui l'appelle
	 * 
	 * @param c 
	 *            
	 * @return distance entre l'insecte et c, pas de deplacement sur les
	 *         diagonales
	 */
	public int distance(Coordonnee c) {
		return Math.abs(this.x - c.getX()) + Math.abs(this.y - c.getY());
	}
	
	/**
	 * Cette fonction permet de d�terminer si les coordonn�es d'un objet sont correctes:
	 * elle renvoie false si l'objet n'est pas dans la grille ou si ses coordonn�es sont n�gatives
	 * et true sinon
	 * 
	 * @return boolean
	 */
	
	public boolean estCorrecte() {
		if (this.x < 0) {
			return false;
		}
		else if (this.x > Simulation.getLargeur()) {
			return false;
		}
		else if (this.y < 0) {
			return false;
		}
		else if (this.y > Simulation.getHauteur()) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * getter x
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * set coordonnee x
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * getter y
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * set coordonnee y
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	
	/**
	 *Cette fonction nous permet de calculer la distance euclidienne entre l'insecte qui l'appelle et 
	 *un point c
	 *Nous avons besoin de cette distance euclidienne pour prendre le rayon du cercle de ph�romones de danger
	 *pos� par les fourmis lorsqu'elles rep�rent un ennemi	 
	 *
	 * @return distance euclidienne entre c et l'insecte
	 */
	
	public int euclidienne(Coordonnee c){
		return (int) Math.sqrt(Math.pow((this.y-c.getY()),2)+Math.pow((this.x-c.getX()),2));
	}
	
	@Override
	/**
	* Cette fonction toString nous permet d'afficher facilement les coordonn�es et d'y acc�der
	*/
	public String toString() {
		return "("+this.x+","+this.y+")";
	}

}
