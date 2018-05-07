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
	 * 
	 * @param c
	 *            other coordinate
	 * @return distance entre les l'objet et c, pas de déplacement sur les
	 *         diagonales
	 */
	public int distance(Coordonnee c) {
		return Math.abs(this.x - c.getX()) + Math.abs(this.y - c.getY());
	}
	
	public boolean estCorrecte() {
		if (this.x < 0) {
			return false;
		}
		else if (this.x >= Simulation.getLargeur()) {
			return false;
		}
		else if (this.y < 0) {
			return false;
		}
		else if (this.y >= Simulation.getHauteur()) {
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
	public int euclidienne(Coordonnee c){
		return (int) Math.sqrt(Math.pow((this.y-c.getY()),2)+Math.pow((this.x-c.getX()),2));
	}
	
	@Override
	public String toString() {
		return "("+this.x+","+this.y+")";
	}

}
