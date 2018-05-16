package pkg_fourmi;

import java.util.ArrayList;

public class Eclaireuse extends Fourmi{
	
	private boolean retour;
	private ArrayList<Coordonnee> chemin;

	public Eclaireuse(Coordonnee position, Colonie colonie) {
		super(position, 1, 1, colonie);
		this.retour = false;
		this.chemin = new ArrayList<Coordonnee>();
	}
	
	@Override
	public void deplacement(Coordonnee c) {
		if (c.estCorrecte()) {
			Simulation.getGrille()[this.getPosition().getX()][this.getPosition().getY()].setInsecte(null);
			this.setPosition(c);
			Simulation.getGrille()[this.getPosition().getX()][this.getPosition().getY()].setInsecte(this);
			if (this.getRetour() == false){
				this.getChemin().add(c);
			}
		}
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
		
		else if (this.getRetour() == true){
			if (Simulation.getGrille()[x][y].getType() == TypeCase.Fourmiliere){
				this.setRetour(false);
				Coordonnee position = allerAleatoire();
				this.deplacement(position);
			}
			else{
				this.poserPheromoneNourriture();
				this.deplacement(this.getChemin().get(this.getChemin().size()-1));
				this.getChemin().remove(this.getChemin().size()-1);
			}
		}
		
		else if (coordNourriture != null){
			if (this.getPosition().distance(coordNourriture) == 1) {
				this.setRetour(true);
				if (Simulation.getGrille()[x][y].getType() == TypeCase.Fourmiliere){
					this.setRetour(false);
					Coordonnee position = allerAleatoire();
					this.deplacement(position);
				}
				else {
					this.deplacement(this.getChemin().get(this.getChemin().size()-1));
				}
			} else {
				Coordonnee position = this.allerA(coordNourriture);
				this.deplacement(position);
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
		Simulation.getGrille()[x][y].addPheroNourriture(40);
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
				if (coordFourmi.distance(listNourriture.get(i)) < coordFourmi.distance(coordPlusProche)){
					coordPlusProche = listNourriture.get(i);
				}
			}
			return coordPlusProche;		
		}

	}
	
	@Override
	public Coordonnee allerAleatoire() {
		int insecteX = this.getPosition().getX();
		int insecteY = this.getPosition().getY();
		ArrayList<Coordonnee> posiPossible = new ArrayList<Coordonnee>();
		posiPossible.add(new Coordonnee(insecteX + 1, insecteY));
		posiPossible.add(new Coordonnee(insecteX - 1, insecteY));
		posiPossible.add(new Coordonnee(insecteX, insecteY + 1));
		posiPossible.add(new Coordonnee(insecteX, insecteY - 1));
		ArrayList<Coordonnee> listPosition1 = new ArrayList<Coordonnee>();
		
		for (Coordonnee posi : posiPossible) {
			Case c = Simulation.getGrille()[posi.getX()][posi.getY()];
			if (posi.estCorrecte() && c.getInsecte() == null) {
				listPosition1.add(posi);
			}
			posiPossible = listPosition1;
		}
		
		ArrayList<Coordonnee> listPosition2 = new ArrayList<Coordonnee>();

		if (this.getChemin().size() > 1){
			Coordonnee derCase = this.getChemin().get(this.getChemin().size()-2);
			for (Coordonnee posi : posiPossible) {
				if (derCase.getX() != posi.getX() || derCase.getY() != posi.getY()) {
					listPosition2.add(posi);
				}
				posiPossible = listPosition2;
			}
		}

		if (posiPossible.size() != 0) {
			int indice = (int) (Math.random() * posiPossible.size());
			return posiPossible.get(indice);
		} else {
			return this.getPosition();
		}
	}
	
	@Override
	public String toString(){
		String s = "Eclaireuse " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
