package pkg_fourmi;

import java.util.List;

public class Transporteuse  extends Eclaireuse{
	
	private boolean nourriture;

	public Transporteuse(Coordonnee position, Nom nom, Prenom prenom, int champvision, int endurance, int force,
			boolean combat, boolean nourriture) {
		super(position, nom, prenom, champvision, endurance, force, combat);
		this.nourriture = nourriture;
	}

	public boolean getNourriture() {
		return nourriture;
	}

	public void setNourriture(boolean nourriture) {
		this.nourriture = nourriture;
	}
	
	public void recupererNourriture(){
		
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
		
		else if (coordEnnemi != null){
			int x = coordFourmi.getX();
			int y = coordFourmi.getY();
			this.setCombat(true);
			if (this.nourriture == true){
				this.nourriture = false;
				Simulation.getGrille()[x][y].addNourriture(10);		
			this.poserPheromoneDanger();
			if (this.getPosition().distance(coordEnnemi) == 1){
				this.attaquer();
			}
			else{
				chemin;
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
			this.deplacement(this.getChemin()[-1]);
		}
		
		else {
			deplacement aleatoire;
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
	
}
