package pkg_fourmi;

import java.util.Random;

public class Reine extends Fourmi{
	
	public Reine(Coordonnee position, Colonie colonie) {
		super(position, 1, 1, colonie);
	}
	
	public void action(){
		
		if (this.getColonie().getStockNourriture() > 15) {
			this.pondre();
		}
		
	}
	
	public void pondre(){
		
		Random rd = new Random();
		
		if (rd.nextFloat() > 0.8) {
			
			int choixPonte = (int) (rd.nextFloat()*3);
			Coordonnee position = this.allerAleatoire();
			int stockNourriture = this.getColonie().getStockNourriture();
			
			if (choixPonte == 0) {
				Fourmi fourmi = new Soldate(position, this.getColonie());
				this.getColonie().ajouterFourmi(fourmi);
				this.getColonie().setStockNourriture(stockNourriture-5);
				System.out.println(fourmi.toString() + " est né(e).");
			}
			
			if (choixPonte == 1) {
				Fourmi fourmi = new Eclaireuse(position, this.getColonie());
				this.getColonie().ajouterFourmi(fourmi);
				this.getColonie().setStockNourriture(stockNourriture-2);
				System.out.println(fourmi.toString() + " est né(e).");
			}
			
			if (choixPonte == 2) {
				Fourmi fourmi = new Transporteuse(position, this.getColonie());
				this.getColonie().ajouterFourmi(fourmi);
				this.getColonie().setStockNourriture(stockNourriture-2);
				System.out.println(fourmi.toString() + " est né(e).");
			}
			
		}
	}
	
	@Override
	public String toString(){
		String s = "Reine " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
