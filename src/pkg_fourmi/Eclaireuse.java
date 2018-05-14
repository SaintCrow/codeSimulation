package pkg_fourmi;

import java.util.ArrayList;

import java.util.List;

public class Eclaireuse extends Fourmi{
	
	private boolean retour;
	private ArrayList<Coordonnee> chemin;
	
	/**
	 * constructeur
	 * 
	 * On prend en paramètre la position de l'éclaireuse et la colonie à laquelle elle appartient
	 * 
	 * @param position
	 * @param colonie
	 */

	public Eclaireuse(Coordonnee position, Colonie colonie) {
		super(position, 1, 1, colonie);
		this.retour = false;
		this.chemin = new ArrayList<Coordonnee>();
	}
	
	/**
	 * Cette fonction renvoie un booléen pour déterminer si la fourmi retourne vers la colonie ou est en recherche
	 * 
	 * @return retour 
	 */

	public boolean getRetour() {
		return retour;
	}
	
	/**
	 *set 
	 * 
	 * @param retour (le booléen défini dans la foonction getRetour)  
	 */

	public void setRetour(boolean retour) {
		this.retour = retour;
	}
	
	/**
	 *getter
	 *
	 *Le chemin est une liste de coordonnées qui contient le chemin parcouru par la fourmi pendant qu'elle était en recherche
	 * (pas en état de retour) afin qu'elle puisse retrouver son chemin lorsqu'elle retourne à la colonie
	 * 
	 * @return chemin
	 */

	public ArrayList<Coordonnee> getChemin() {
		return chemin;
	}
	
	/**
	 * set 
	 * 
	 * @param chemin 
	 */

	public void setChemin(ArrayList<Coordonnee> chemin) {
		this.chemin = chemin;
	}
	
	/**
	 * Cette fonction toString nous permet de récupérer facilement le nom et le prénom de l'éclaireuse 
	 * 
	 * @return s 
	 */
	
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
	
	/**
	 * Cette fonction permet à l'éclaireuse de poser une phéromone de nourriture
	 * La phéromone est posée aux coordonnées de la fourmi et est ajoutée à la grille
	 * 
	 * 
	 */
	
	public void poserPheromoneNourriture(){
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		Simulation.getGrille()[x][y].addPheroNourriture(20);
	}
	
	/**
	 * Cette fonction permet à la fourmi de rechercher et de détecter la nourriture
	 * dans le champ de vision de la fourmi
	 * Si plusieurs sources de nourriture sont en vue, la fourmi prend en compte la
	 * plus proche
	 * 
	 * 
	 * @return null si la fonction ne détecte pas de nourriture
	 * @return listNourriture.get(0) si une seule source de nourriture est détectée,
	 * la fonction renvoie donc les coordonnées de cette source de nourriture
	 * @return coordPlusProche si plusieurs sources sont détectées, on mesure
	 */
		
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
	
	
	/**
	 * Cette fonction toString nous permet de récupérer facilement le nom et le prénom de l'éclaireuse 
	 * 
	 * @return s 
	 */
	
	@Override
	public String toString(){
		String s = "Eclaireuse " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
