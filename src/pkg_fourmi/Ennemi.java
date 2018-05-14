package pkg_fourmi;

import java.util.ArrayList;

public class Ennemi extends Insecte{
	
	public Ennemi(Coordonnee position) {
		super(position, 3, 1);
	}
	
	public void action(){
		
		Coordonnee coordFourmi = this.rechercheFourmi();
		
		if (coordFourmi != null){
			System.out.println("Position : "+this.getPosition().toString()+"   , Cible : "+coordFourmi.toString());
			if (this.getPosition().distance(coordFourmi) == 1){
				this.attaquer(coordFourmi);
			}
			else{
				Coordonnee position = this.allerA(coordFourmi);
				this.deplacement(position);
			}
			
		}
		
		else {
			Coordonnee position = allerAleatoire();
			this.deplacement(position);
		}
	}
	
	public Coordonnee rechercheFourmi(){
		
		Coordonnee coordEnnemi = this.getPosition();
		int x = coordEnnemi.getX();
		int y = coordEnnemi.getY();
		ArrayList<Coordonnee> listFourmi = new ArrayList<Coordonnee>();
		System.out.println("Fourmis détectées : ");
		for (int i = x-getChampvision(); i <= x + this.getChampvision(); i++){
			for (int j = y-getChampvision(); j <= y + this.getChampvision(); j++){
				if ((new Coordonnee(i,j).estCorrecte())&&(Simulation.getGrille()[i][j].getInsecte() instanceof Fourmi)){
					System.out.println("  - "+Simulation.getGrille()[i][j].getInsecte().toString()+" -> "+coordEnnemi.distance(Simulation.getGrille()[i][j].getPosition()));					
					listFourmi.add(Simulation.getGrille()[i][j].getPosition());
				}
			}
		}
		if (listFourmi.size() == 0){
			return null;
		}
		else if (listFourmi.size() == 1){
			return listFourmi.get(0);
		}
		else{
			Coordonnee coordPlusProche = listFourmi.get(0);
			for (int i = 1; i < listFourmi.size(); i++){
				if (coordEnnemi.distance(listFourmi.get(i)) < coordEnnemi.distance(coordPlusProche)){
					coordPlusProche = listFourmi.get(i);
				}
			}
			return coordPlusProche;
		}
		
	}
	
	public void tuerFourmi(Fourmi fourmi) {
		fourmi.getColonie().getMembres().remove(fourmi);
	}
	
	@Override
	public String toString(){
		String s = "Ennemi " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
