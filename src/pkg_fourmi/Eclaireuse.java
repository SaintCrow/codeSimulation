package pkg_fourmi;

import java.util.ArrayList;
import java.util.List;

public class Eclaireuse extends Fourmi{
	
	private boolean retour;
	private ArrayList<Coordonnee> chemin;

	public Eclaireuse(Coordonnee position, Nom nom, Prenom prenom, int champvision, int endurance, int force,
			boolean combat) {
		super(position, nom, prenom, champvision, endurance, force, combat);
		this.retour = false;
		this.chemin = new ArrayList<Coordonnee>();
	}

	public boolean getRetour() {
		return retour;
	}

	public void setRetour(boolean retour) {
		this.retour = retour;
	}

	public ArrayList<Coordonnee> getChemin() {
		return chemin;
	}

	public void setChemin(ArrayList<Coordonnee> chemin) {
		this.chemin = chemin;
	}
	
	public void action(){
		
		Coordonnee coordEnnemi = this.rechercheEnnemi();
		Coordonnee coordNourriture = this.rechercheNourriture();
		Coordonnee coordFourmi = this.getPosition();
		
		
		if (this.getCombat() == true){
			if (coordEnnemi != null){
				if (this.getPosition().distance(coordEnnemi) == 1){
					this.attaquer();
				}
				else{
					Coordonnee position = this.allerA(coordEnnemi);
					this.deplacement(position);
				}
			}
			else{
				->deplacement random;
			}
		}
		
		else if (coordEnnemi != null){
			this.setCombat(true);
			this.poserPheromoneDanger();
			if (this.getPosition().distance(coordEnnemi) == 1){
				this.attaquer();
			}
			else{
				Coordonnee position = this.allerA(coordEnnemi);
				this.deplacement(position);
			}
		}
		
		else if (this.getRetour() == true){
			int x = coordFourmi.getX();
			int y = coordFourmi.getY();
			if (Simulation.getGrille()[x][y].getType() == TypeCase.Fourmiliere){
				this.setRetour(false);
				deplacement aleatoire;
			}
			else{
				this.poserPheromoneNourriture();
				this.deplacement(this.getChemin().get(-1));
				this.setChemin(this.getChemin().remove(-1));
			}
		}
		
		else if (coordNourriture != null){
			this.setRetour(true);
			this.poserPheromoneNourriture();
			this.deplacement(this.getChemin().get(-1));
		}
		
		else {
			deplacement aleatoire;
		}
	
	}
	
	public void poserPheromoneNourriture(){
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		Simulation.getGrille()[x][y].addPheroNourriture(20);
	}
	
	public void poserPheromoneDanger(){
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		Simulation.getGrille()[x][y].addPheroDanger(20);
	}
	
	public Coordonnee rechercheNourriture(){
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		ArrayList<Coordonnee> listNourriture = new ArrayList<Coordonnee>();
		for (int i = x-getChampvision(); i <= x + this.getChampvision(); i++){
			for (int j = y-getChampvision(); j <= y + this.getChampvision(); j++){
				if (Simulation.getGrille()[i][j].getNourriture() > 0){
					listNourriture.add(Simulation.getGrille()[i][j].getPosition());
				}
			}
		}
		if (listNourriture.size() == 0){
			return null;
		}
		else if (listNourriture.size() == 1){
			return listNourriture.get(0);
		}
		else{
			Coordonnee coordPlusProche = listNourriture.get(0);
			for (int i = 1; i < listNourriture.size(); i++){
				if (coordFourmi.distance(listNourriture.get(i)) > coordFourmi.distance(coordPlusProche)){
					coordPlusProche = listNourriture.get(i);
				}
			}
			return coordPlusProche;		
		}

	}
	

}
