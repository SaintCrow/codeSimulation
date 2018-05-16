package pkg_fourmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
	
	/**
	 * set du stock de nourriture
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
	 * fonction ajoutant des fourmis a la colonie
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
	 * fonction gerant les famines:
	 * elle elimine aleatoirement un membre de la colonie en cas de penurie de nourriture
	 */

	public void famine() {
		ArrayList<Fourmi> listTransporteuse = new ArrayList<Fourmi>();
		ArrayList<Fourmi> listEclaireuse = new ArrayList<Fourmi>();
		ArrayList<Fourmi> listSoldate = new ArrayList<Fourmi>();
		for (Fourmi fourmi : membres){
			if (fourmi instanceof Transporteuse){
				listTransporteuse.add(fourmi);
			}
			if (fourmi instanceof Eclaireuse && !(fourmi instanceof Transporteuse)){
				listEclaireuse.add(fourmi);
			}
			if (fourmi instanceof Soldate){
				listSoldate.add(fourmi);
			}
		}
		
		Random rd = new Random();
		Fourmi sacrifice;
		if (listEclaireuse.size() != 0 && listSoldate.size() != 0){
			if (rd.nextInt(2) == 0){
				sacrifice = listEclaireuse.get(rd.nextInt(listEclaireuse.size())); 
			}
			else{
				sacrifice = listSoldate.get(rd.nextInt(listSoldate.size()));
			}
		}
		else if (listEclaireuse.size() != 0){
			sacrifice = listEclaireuse.get(rd.nextInt(listEclaireuse.size()));
		}
		else if (listSoldate.size() != 0){
			sacrifice = listSoldate.get(rd.nextInt(listSoldate.size()));
		}
		else {
			sacrifice = listTransporteuse.get(rd.nextInt(listTransporteuse.size()));
		}
		sacrifice.mourir();
	}
	
	/**
	 * fonction gerant la consommation de nourriture par les fourmis
	 * chaque membre consomme une unite de nourriture
	 * si le stock atteint zero, la famine est declenchee
	 */

	public void consommation() {
		this.stockNourriture -= (int) this.membres.size()/10;
		while (this.stockNourriture < 0) {
			this.famine();
			this.stockNourriture += 1;
		}

	}

}
