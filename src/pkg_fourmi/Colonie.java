package pkg_fourmi;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Colonie {

	private String nom;
	private int stockNourriture;
	private List<Fourmi> membres;

	
	
	/**
	 * constructeur
	 * 
	 * on prend les membres de la colonie dans la liste des fourmis
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
	 * getter Nom de la colonie
	 * 
	 * @return nom
	 */

	public String getNom() {
		return nom;
	}
	
	/**
	 * set Nom
	 * 
	 * @param nom
	 */

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * getter du stock de nourriture de la colonie
	 * 
	 * @return stockNourriture
	 */

	public int getStockNourriture() {
		return stockNourriture;
	}
	
<<<<<<< HEAD
	/**
	 * set du stock de nourriture
=======
	/**5
	 * getter x
>>>>>>> abf6ad8946cc3e919cfe98b2c0367af59565bd92
	 * 
	 * @param StockNourriture
	 */

	public void setStockNourriture(int stockNourriture) {
		this.stockNourriture = stockNourriture;
	}
	
	/**
	 * getter des membres de la colonie
	 * 
	 * @return membres
	 */

	public List<Fourmi> getMembres() {
		return membres;
	}
	
	/**
	 * fonction ajoutant des fourmis à la colonie
	 * @param fourmi
	 */

	public void ajouterFourmi(Fourmi fourmi) {
		this.getMembres().add(fourmi);
		int x = fourmi.getPosition().getX();
		int y = fourmi.getPosition().getY();
		Simulation.getGrille()[x][y].setInsecte(fourmi);
	}
	
	/**
	 * set membres de la colonie
	 * @param membres
	 */

	public void setMembres(List<Fourmi> membres) {
		this.membres = membres;
	}
	
	/**
	 * fonction gérant les famines:
	 * elle élimine aléatoirement un membre de la colonie en cas de pénurie de nourriture
	 */

	public void famine() {
		Fourmi sacrifice = this.membres.get((int) (1 + (this.membres.size() - 1) * Math.random()));
		sacrifice.mourir();
	}
	
	/**
	 * fonction gérant la consommation de nourriture par les fourmis
	 * chaque membre consomme une unité de nourriture
	 * si le stock atteint zéro, la famine est déclenchée
	 */

	public void consommation() {
		this.stockNourriture -= this.membres.size();
		while (this.stockNourriture < 0) {
			this.famine();
			this.stockNourriture += 1;
		}

	}

}
