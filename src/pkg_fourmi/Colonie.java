package pkg_fourmi;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Colonie {

	private String nom;
	private int stockNourriture;
	private List<Fourmi> membres;

	public Colonie(String nom, int stockNourriture, List<Fourmi> membres) {
		super();
		this.nom = nom;
		this.stockNourriture = stockNourriture;
		this.membres = membres;
	}
	
	/**
	 * constructeur
	 * 
	 * @param nom
	 * @param stockNourriture
	 * 
	 */

	public Colonie(String nom, int stockNourriture) {
		super();
		this.nom = nom;
		this.stockNourriture = stockNourriture;
		this.membres = new CopyOnWriteArrayList<Fourmi>();
	}
	
	/**
	 * getter x
	 * 
	 * @return x
	 */

	public String getNom() {
		return nom;
	}
	
	/**
	 * getter x
	 * 
	 * @return x
	 */

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * getter x
	 * 
	 * @return x
	 */

	public int getStockNourriture() {
		return stockNourriture;
	}
	
	/**5
	 * getter x
	 * 
	 * @return x
	 */

	public void setStockNourriture(int stockNourriture) {
		this.stockNourriture = stockNourriture;
	}
	
	/**
	 * getter x
	 * 
	 * @return x
	 */

	public List<Fourmi> getMembres() {
		return membres;
	}
	
	/**
	 * getter x
	 * 
	 * @return x
	 */

	public void ajouterFourmi(Fourmi fourmi) {
		this.getMembres().add(fourmi);
		int x = fourmi.getPosition().getX();
		int y = fourmi.getPosition().getY();
		Simulation.getGrille()[x][y].setInsecte(fourmi);
	}
	
	/**
	 * getter x
	 * 
	 * @return x
	 */

	public void setMembres(List<Fourmi> membres) {
		this.membres = membres;
	}
	
	/**
	 * getter x
	 * 
	 * @return x
	 */

	private void famine() {
		Fourmi sacrifice = this.membres.get((int) (1 + (this.membres.size() - 1) * Math.random()));
		sacrifice.mourir();
	}
	
	/**
	 * getter x
	 * 
	 * @return x
	 */

	private void consommation() {
		this.stockNourriture -= this.membres.size();
		while (this.stockNourriture < 0) {
			this.famine();
			this.stockNourriture += 1;
		}

	}

}
