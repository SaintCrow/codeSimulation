package pkg_fourmi;

import java.util.ArrayList;

public abstract class Fourmi extends Insecte{
	
	private boolean combat;
		
	public Fourmi(Coordonnee position, Nom nom, Prenom prenom, int champvision, int endurance, int force, boolean combat) {
		super(position, nom, prenom, champvision, endurance, force);
		this.combat = combat;
	}

	public boolean getCombat() {
		return combat;
	}

	public void setCombat(boolean combat) {
		this.combat = combat;
	}

	public Coordonnee rechercheEnnemi(){
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		ArrayList<Coordonnee> listEnnemi = new ArrayList<Coordonnee>();
		for (int i = x-getChampvision(); i <= x + this.getChampvision(); i++){
			for (int j = y-getChampvision(); j <= y + this.getChampvision(); j++){
				if (Simulation.getGrille()[i][j].getInsecte() instanceof Ennemi){
					listEnnemi.add(Simulation.getGrille()[i][j].getPosition());
				}
			}
		}
		if (listEnnemi.size() == 0){
			return null;
		}
		else if (listEnnemi.size() == 1){
			return listEnnemi.get(0);
		}
		else{
			Coordonnee coordPlusProche = listEnnemi.get(0);
			for (int i = 1; i < listEnnemi.size(); i++){
				if (coordFourmi.distance(listEnnemi.get(i)) > coordFourmi.distance(coordPlusProche)){
					coordPlusProche = listEnnemi.get(i);
				}
			}
			return coordPlusProche;
		}
	}
	
	public Coordonnee recherchePheromoneDanger(){
		
		return ;
		
	}
	
	public void attaquer(){
		
	}

}
