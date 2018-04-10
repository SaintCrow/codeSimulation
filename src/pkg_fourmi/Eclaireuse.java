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
		
		if (this.getCombat() == true){
			Coordonnee coordEnnemi = this.rechercheEnnemi();
			if (coordEnnemi != null){
				if (this.getPosition().distance(coordEnnemi) == 1){
					this.attaquer();
				}
				else{
					chemin
				}
			}
			else{
				->deplacement random
			}
		}
		
		else if (this.getRetour() == true){
			this.deplacement(this.getChemin()[-1]);
			
		}
		
	}
	
	public void poserPheromoneNourriture(){
		
	}
	
	public void poserPheromoneDanger(){
		
	}
	
	public Coordonnee rechercheNourriture(){
		
		return ;
	}
	

}
