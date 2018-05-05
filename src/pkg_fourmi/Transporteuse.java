package pkg_fourmi;

import java.util.List;

public class Transporteuse  extends Eclaireuse{
	
	private boolean nourriture;

	public Transporteuse(Coordonnee position, Colonie colonie) {
		super(position, colonie);
		this.nourriture = nourriture;
		this.nourriture = false;
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
					Coordonnee position = this.allerA(coordEnnemi);
					this.deplacement(position);;
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
			if (this.nourriture == true){
				this.nourriture = false;
				Simulation.getGrille()[x][y].addNourriture(10);		
				this.poserPheromoneDanger();
			}
			if (this.getPosition().distance(coordEnnemi) == 1){
				this.attaquer();
			}
			else{
				Coordonnee position = this.allerA(coordEnnemi);
				this.deplacement(position);;
			}
		}
		
		else if (this.getRetour() == true){
			int x = coordFourmi.getX();
			int y = coordFourmi.getY();
			if (Simulation.getGrille()[x][y].getType() == TypeCase.Fourmiliere){
				this.setRetour(false);
				Coordonnee position = allerAleatoire();
				this.deplacement(position);
			}
			else{
				this.poserPheromoneNourriture();
				this.deplacement(this.getChemin().get(-1));
				this.getChemin().remove(-1);
			}
		}
		
		else if (coordNourriture != null){
			this.setRetour(true);
			this.poserPheromoneNourriture();
			this.deplacement(this.getChemin().get(-1));
		}
		
		else {
			Coordonnee position = allerAleatoire();
			this.deplacement(position);
		}
	
	}
	
}
