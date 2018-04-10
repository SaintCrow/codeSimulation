package pkg_fourmi;

import java.util.List;

public class Eclaireuse extends Fourmi{
	
	private boolean retour;
	private List<Coordonnee> chemin;
	
	public Eclaireuse(Coordonnee position, Nom nom, Prenom prenom, int champvision, boolean combat) {
		super(position, nom, prenom, champvision, combat);
		this.retour = false;
		this.chemin = new List();
	}

	public boolean getRetour() {
		return retour;
	}

	public void setRetour(boolean retour) {
		this.retour = retour;
	}

	public List<Coordonnee> getChemin() {
		return chemin;
	}

	public void setChemin(List<Coordonnee> chemin) {
		this.chemin = chemin;
	}
	
	public void action(){
		
	}
	
	public void poserPheromoneNourriture(){
		
	}
	
	public void poserPheromoneD
	
	

}
