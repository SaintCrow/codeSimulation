package pkg_fourmi;

import java.util.ArrayList;

public class Eclaireuse extends Fourmi {

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
	 * Cette fonction permet a l'eclaireuse de se deplacer, c'est-a-dire qu'elle change les coordonnees et donc la position 
	 * de cette fourmi
	 * si la fourmi n'est pas en retour, on ajoute ses coordonnees au chemin
	 *
	 */


	@Override
	public void deplacement(Coordonnee c) {
		if (c.estCorrecte()) {
			Simulation.getGrille()[this.getPosition().getX()][this.getPosition().getY()].setInsecte(null);
			this.setPosition(c);
			Simulation.getGrille()[this.getPosition().getX()][this.getPosition().getY()].setInsecte(this);
			if (this.getRetour() == false) {
				this.getChemin().add(c);
			}
		}
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
	 * set chemin
	 * 
	 * @param chemin 
	 */

	public void setChemin(ArrayList<Coordonnee> chemin) {
		this.chemin = chemin;
	}
	
	/**
	 *Cette fonction code les actions successives de l'éclaireuse:
	 *la recherche d'ennemis puis la pose de pheromones de danger,
	 *l'attaque des ennemis eventuels,
	 *le fait de suivre le chemin dans le sens inverse si elle est en retour en posant des pgeromones de nourriture,
	 *le fait de se mettre en retour si elle trouve de la nourriture,
	 *et enfin le fait de se deplacer aleatoirement sinon
	 * 
	 */

	public void action() {

		Coordonnee coordEnnemi = this.rechercheEnnemi();
		Coordonnee coordNourriture = this.rechercheNourriture();
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();

		if (coordEnnemi != null) {
			if (Simulation.getGrille()[x][y].getPheroDanger() == 0) {
				this.poserPheromoneDanger();
			}

			if (this.getPosition().distance(coordEnnemi) <= 1){

				this.attaquer(coordEnnemi);
			} else {
				Coordonnee position = this.allerA(coordEnnemi);
				this.deplacement(position);
			}
		}

		else if (this.getRetour() == true) {
			if (Simulation.getGrille()[x][y].getType() == TypeCase.Fourmiliere) {
				this.setRetour(false);
				Coordonnee position = allerAleatoire();
				this.deplacement(position);
			} else {
				this.poserPheromoneNourriture();
				//System.out.print(this.getChemin().size());
				this.setChemin(this.lisserChemin());
				//System.out.println(" -> "+this.getChemin().size());
				this.deplacement(this.getChemin().get(this.getChemin().size()-1));
				this.getChemin().remove(this.getChemin().size()-1);
			}
		}

		else if (coordNourriture != null) {
			if (this.getPosition().distance(coordNourriture) == 1) {
				this.setRetour(true);
				if (Simulation.getGrille()[x][y].getType() == TypeCase.Fourmiliere) {
					this.setRetour(false);
					Coordonnee position = allerAleatoire();
					this.deplacement(position);
				} else {
					this.deplacement(this.getChemin().get(this.getChemin().size() - 1));
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
	

	/**
	 * Cette fonction permet à l'éclaireuse de poser une phéromone de nourriture
	 * La phéromone est posée aux coordonnées de la fourmi et est ajoutée à la grille
	 * Les pheromones sont posees tant que la fourmi est en retour
	 * 
	 */

	public void poserPheromoneNourriture() {
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		Simulation.getGrille()[x][y].addPheroNourriture(40);
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

	public Coordonnee rechercheNourriture() {
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();
		ArrayList<Coordonnee> listNourriture = new ArrayList<Coordonnee>();
		for (int i = x - getChampvision(); i <= x + this.getChampvision(); i++) {
			for (int j = y - getChampvision(); j <= y + this.getChampvision(); j++) {
				Coordonnee position = new Coordonnee(i, j);
				if ((position.estCorrecte()) && (Simulation.getGrille()[i][j].getNourriture() > 0)) {
					listNourriture.add(Simulation.getGrille()[i][j].getPosition());
				}
			}
		}
		if (listNourriture.size() == 0) {
			return null;
		} else if (listNourriture.size() == 1) {
			return listNourriture.get(0);
		} else {
			Coordonnee coordPlusProche = listNourriture.get(0);
			for (int i = 1; i < listNourriture.size(); i++) {
				if (coordFourmi.distance(listNourriture.get(i)) < coordFourmi.distance(coordPlusProche)) {
					coordPlusProche = listNourriture.get(i);
				}
			}
			return coordPlusProche;
		}

	}
	

	/**
	 * Cette fonction fait la liste des nouvelles positions possibles pour l'eclaireuse et en choisit une aleatoirement
	 * parmi elles
	 * On a egalement ajoute une fonctionnalite pour empecher la fourmi de parcourir une nouvelle fois les cases deja stockees
	 * dans le chemin (liste des cases deja parcourues par la fourmi depuis la fourmiliere)
	 */
	
	public ArrayList<Coordonnee> lisserChemin() {
		ArrayList<Coordonnee> nChemin = new ArrayList<Coordonnee>();
		int l = this.getChemin().size();
		for (int i = 0; i < l; i++){
			Coordonnee position1 = this.getChemin().get(i);
			for (int j = l-1; j >=0; j--){
				Coordonnee position2 = this.getChemin().get(j);
				if (position1.getX() == position2.getX() && position1.getY() == position2.getY() && i != j){
					for (int k = 0; k < l; k++){
						if (k <= i || k > j){
							nChemin.add(this.getChemin().get(k));
						}
					}
					return nChemin;
				}
			}
		}
		return this.getChemin();
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

		if (this.getChemin().size() > 1) {
			Coordonnee derCase = this.getChemin().get(this.getChemin().size() - 2);
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
	

	/**
	 * Cette fonction toString nous permet de récupérer facilement le nom et le prénom de l'éclaireuse 
	 * 
	 * @return s 
	 */

	@Override
	public String toString() {
		String s = "Eclaireuse " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
