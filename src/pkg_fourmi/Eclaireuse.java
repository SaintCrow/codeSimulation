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
					chemin;
				}
			}
			else{
				->deplacement random;
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
				this.deplacement(this.getChemin()[-1]);
			}
		}
		
		else if (coordEnnemi != null){
			this.setCombat(true);
			this.poserPheromoneDanger();
			if (this.getPosition().distance(coordEnnemi) == 1){
				this.attaquer();
			}
			else{
				chemin;
			}
		}
		
		else if (coordNourriture != null){
			this.setRetour(true);
			this.poserPheromoneNourriture();
			this.deplacement(this.getChemin()[-1]);
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
		
		return ;
	}
	

}
