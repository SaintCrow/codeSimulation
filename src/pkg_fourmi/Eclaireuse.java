package pkg_fourmi;

import java.util.ArrayList;

public class Eclaireuse extends Fourmi{
	
	private boolean retour;
	protected ArrayList<Coordonnee> chemin;

	public Eclaireuse(Coordonnee position, Colonie colonie) {
		super(position, 1, 1, colonie);
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
	@Override
	public void deplacement(Coordonnee c){
		super.deplacement(c);
		if(this.retour==false){
			this.chemin.add(c);
		}
	}
	
	public void action(){
		
		Coordonnee coordEnnemi = this.rechercheEnnemi();
		Coordonnee coordNourriture = this.rechercheNourriture();
		Coordonnee coordPheroNourriture = this.recherchePheromoneNourriture();
		Coordonnee coordPheroDanger = this.recherchePheromoneDanger();
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		
		if (coordEnnemi != null){
			if (Simulation.getGrille()[x][y].getPheroDanger() == 0){
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
			if (Simulation.getGrille()[x][y].getType() == TypeCase.Fourmiliere){
				this.setRetour(false);
				Coordonnee position = allerAleatoire();
				this.deplacement(position);
			}
			else{
				this.poserPheromoneNourriture();
				this.deplacement(this.getChemin().get(this.getChemin().size()-1));
				this.getChemin().remove(-1);
			}
		}
		
		else if ((coordNourriture != null)&&(coordPheroNourriture == null)){
			this.poserPheromoneNourriture();
			this.setRetour(true);
			if (Simulation.getGrille()[x][y].getType() == TypeCase.Fourmiliere){
				this.setRetour(false);
				Coordonnee position = allerAleatoire();
				this.deplacement(position);
				
			}
			else {
				this.deplacement(this.getChemin().get(this.getChemin().size()-1));
			}
		}
		
		else {
			Coordonnee position = allerAleatoire();
			this.deplacement(position);
		}
	
	}
	
	public void poserPheromoneNourriture(){
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		Simulation.getGrille()[x][y].addPheroNourriture(20);
	}
		
	public Coordonnee rechercheNourriture(){
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		ArrayList<Coordonnee> listNourriture = new ArrayList<Coordonnee>();
		for (int i = x-getChampvision(); i <= x + this.getChampvision(); i++){
			for (int j = y-getChampvision(); j <= y + this.getChampvision(); j++){
				Coordonnee position = new Coordonnee(i,j);
				if ((position.estCorrecte())&&(Simulation.getGrille()[i][j].getNourriture() > 0)){
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
	
	@Override
	public String toString(){
		String s = "Eclaireuse " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
