package pkg_fourmi;

import java.util.ArrayList;
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
	
	public Colonie(String nom, int stockNourriture) {
		super();
		this.nom = nom;
		this.stockNourriture = stockNourriture;
		this.membres = new CopyOnWriteArrayList<Fourmi>();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getStockNourriture() {
		return stockNourriture;
	}

	public void setStockNourriture(int stockNourriture) {
		this.stockNourriture = stockNourriture;
	}

	public List<Fourmi> getMembres() {
		return membres;
	}

	public void ajouterFourmi(Fourmi fourmi) {
		this.getMembres().add(fourmi);
		int x = fourmi.getPosition().getX();
		int y = fourmi.getPosition().getY();
		Simulation.getGrille()[x][y].setInsecte(fourmi);
	}
	
	public void setMembres(List<Fourmi> membres) {
		this.membres = membres;
	}
	
	private void famine(){
		
	}
	
	private void consommation(){
		
	}

}
