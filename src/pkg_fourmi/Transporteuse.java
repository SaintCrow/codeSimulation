package pkg_fourmi;

import java.util.List;

public class Transporteuse  extends Eclaireuse{
	
	private int nourriture;

	public Transporteuse(Coordonnee position, Colonie colonie) {
		super(position, colonie);
		this.nourriture = 0;
	}

	public int getNourriture() {
		return this.nourriture;
	}

	public void setNourriture(int nourriture) {
		this.nourriture = nourriture;
	}
	
	public void recupererNourriture(Coordonnee position){
		int x = position.getX();
		int y = position.getY();
		int nourritureRecup = Math.min(10, Simulation.getGrille()[x][y].getNourriture());
		Simulation.getGrille()[x][y].addNourriture(-nourritureRecup);
		this.nourriture = nourritureRecup;
		this.setRetour(true);
		System.out.println(this.toString()+" a récupéré de la nourriture.");
	}
	
	public void deposerNourriture() {
		this.getColonie().setStockNourriture(this.getColonie().getStockNourriture()+this.nourriture);
		this.nourriture = 0;
		this.setRetour(false);
		System.out.println(this.toString()+" a rapporté de la nourriture à la fourmilière.");
	}
	
	public void action(){
		
		Coordonnee coordEnnemi = this.rechercheEnnemi();
		Coordonnee coordNourriture = this.rechercheNourriture();
		Coordonnee coordPheroDanger = this.recherchePheromoneDanger();
		Coordonnee coordFourmi = this.getPosition();
		
		
		if (this.getCombat() == true){
			if (coordEnnemi != null){
				if (this.getPosition().distance(coordEnnemi) == 1){
					this.attaquer(coordEnnemi);
				}
				else{
					Coordonnee position = this.allerA(coordEnnemi);
					this.deplacement(position);
				}
			}
			else{
				Coordonnee position = allerAleatoire();
				this.deplacement(position);
			}
		}
		
		else if (coordEnnemi != null){
			int x = coordFourmi.getX();
			int y = coordFourmi.getY();
			this.setCombat(true);
			if (this.nourriture > 0){
				Simulation.getGrille()[x][y].addNourriture(this.nourriture);
				this.nourriture = 0;
				this.setRetour(false);
				this.poserPheromoneDanger();
			}
			if (this.getPosition().distance(coordEnnemi) == 1){
				this.attaquer(coordEnnemi);
			}
			else{
				Coordonnee position = this.allerA(coordEnnemi);
				this.deplacement(position);
			}
		}
		
		else if (coordPheroDanger != null) {
			Coordonnee position = this.allerA(coordPheroDanger);
			this.deplacement(position);
		}
		
		else if (this.getRetour() == true){
			int x = coordFourmi.getX();
			int y = coordFourmi.getY();
			if (Simulation.getGrille()[x][y].getType() == TypeCase.Fourmiliere){
				this.deposerNourriture();
			}
			else{
				this.poserPheromoneNourriture();
				this.deplacement(this.getChemin().get(-1));
				this.getChemin().remove(-1);
			}
		}
		
		else if (coordNourriture != null){
			if (this.getPosition().distance(coordNourriture) == 1){
				this.recupererNourriture(coordNourriture);
			}
			else {
				Coordonnee position = this.allerA(coordNourriture);
				this.deplacement(position);
			}
		}
		
		else {
			Coordonnee position = allerAleatoire();
			this.deplacement(position);
		}
	
	}
	
	@Override
	public String toString(){
		String s = "Transporteuse " + this.getPrenom() + " " + this.getNom();
		return s;
	}
	
}
