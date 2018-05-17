package pkg_fourmi;

public class Transporteuse extends Eclaireuse {

	private int nourriture;
	
	/**
	 * constructeur
	 * @param position
	 * @param colonie
	 */

	public Transporteuse(Coordonnee position, Colonie colonie) {
		super(position, colonie);
		this.nourriture = 0;
	}
	

/**
 * getter nourriture
 * Cela represente la quantite de nourriture que la fourmi transporte
 * @return nourriture
 */


	public int getNourriture() {
		return this.nourriture;
	}
	

/**
 * set nourriture
 * @param nourriture
 */


	public void setNourriture(int nourriture) {
		this.nourriture = nourriture;
	}

	

/**
 * Cette fonction permet a la fourmi de recuperer la nourriture contenue dans les cases de la simulation
 * en prenant en compte les coordonnees de la nourriture, en enlevant 10 a la pile de nourriture et en en 
 * ajoutant 10 a la valeur de nourriture de la fourmi
 * @param position
 */
	
	
	public void recupererNourriture(Coordonnee position) {
		int x = position.getX();
		int y = position.getY();
		int nourritureRecup = Math.min(10, Simulation.getGrille()[x][y].getNourriture());
		Simulation.getGrille()[x][y].addNourriture(-nourritureRecup);
		this.nourriture = nourritureRecup;
		this.setRetour(true);
		Simulation.getCasesGr().setMessage(this.toString() + " a recupere de la nourriture.");
	}
	

/**
 * Cette fonction depose la nourriture transportee dans le stock de la colonie une fois que la fourmi est rentree a la fourmiliere
 * et annule l'etat retour de la fourmi
 */


	public void deposerNourriture() {
		this.getColonie().setStockNourriture(this.getColonie().getStockNourriture() + this.nourriture);
		this.nourriture = 0;
		this.setRetour(false);
		Simulation.getCasesGr().setMessage(this.toString() + " a rapporte de la nourriture a la fourmiliere.");
	}

	

/**
 * Cette fonction fait en sorte que la fourmi suive les pheromones de nourriture presentes dans son champ de vision
 * en retournant les coordonnees des pheromones concernees
 */

	
	
	public Coordonnee suivrePheromone() {

		for (int i = 0; i < this.getChampvision(); i++) {
			for (int j = 0; j < this.getChampvision(); j++) {
				if (Simulation.getGrille()[i][j].getPheroNourriture() > 0 && this.getChemin().contains(Simulation.getGrille()[i][j].getPosition())) {
					if (Simulation.getGrille()[this.getPosition().getX()][this.getPosition().getY()].getPheroNourriture() == 0 || Simulation.getGrille()[this.getPosition().getX()][this.getPosition().getY()].getPheroNourriture() - Simulation.getGrille()[i][j].getPheroNourriture() == -1 || Math.abs(Simulation.getGrille()[this.getPosition().getX()][this.getPosition().getY()].getPheroNourriture() - Simulation.getGrille()[i][j].getPheroNourriture()) <= 19){
						return Simulation.getGrille()[i][j].getPosition();
					}
				}
			}
		}
		return null;

	}
	

/**
 * Cette fonction code les differentes actions successives de la fourmi transporteuse
 * Elle cherche les ennemis, depose des pheromones d'alerte et la nourriture qu'elle transporte si 
 * elle detecte un ennemi avant d'aller l'attaquer
 * Si elle detecte une pheromone de danger, elle se deplace vers elle,
 * Si elle est en retour, elle suit le chemin en memoire dans le sens inverse pour deposer sa nourriture a la fourmiliere
 * Si elle detecte de la nourriture, elle se deplace vers elle et la ramasse si elle est adjacente
 * Elle passe en retour des qu'elle ramasse de la nourriture
 * Si elle detecte des pheromones de nourriture, elle se dirige vers elles
 * Sinon, elle se deplace aleatoirement
 */

	public void action() {

		Coordonnee coordEnnemi = this.rechercheEnnemi();
		Coordonnee coordNourriture = this.rechercheNourriture();
		Coordonnee coordPheroDanger = this.recherchePheromoneDanger();
		Coordonnee coordFourmi = this.getPosition();
		Coordonnee pisteNourr = suivrePheromone();

		int x = coordFourmi.getX();
		int y = coordFourmi.getY();

		if (coordEnnemi != null) {
			if (Simulation.getGrille()[x][y].getPheroDanger() == 0) {
				this.poserPheromoneDanger();
			}
			if (this.nourriture > 0) {
				Simulation.getGrille()[x][y].addNourriture(this.nourriture);
				this.nourriture = 0;
				this.setRetour(false);
				this.poserPheromoneDanger();
			}
			if (this.getPosition().distance(coordEnnemi) == 1) {
				this.attaquer(coordEnnemi);
			} else {
				Coordonnee position = this.allerA(coordEnnemi);
				this.deplacement(position);
			}
		}

		else if (coordPheroDanger != null) {
			Coordonnee position = this.allerA(coordPheroDanger);
			this.deplacement(position);
		}

		else if (this.getRetour() == true) {
			if (Simulation.getGrille()[x][y].getType() == TypeCase.Fourmiliere) {
				this.deposerNourriture();
			} else {
				this.poserPheromoneNourriture();
				this.setChemin(this.lisserChemin());
				this.deplacement(this.getChemin().get(this.getChemin().size()-1));
				this.getChemin().remove(this.getChemin().size()-1);
			}
		}

		else if (coordNourriture != null) {
			if (this.getPosition().distance(coordNourriture) <= 1) {
				this.recupererNourriture(coordNourriture);
			} else {
				Coordonnee position = this.allerA(coordNourriture);
				this.deplacement(position);
			}	
		} 
		
		else if (pisteNourr != null) {
			this.deplacement(allerA(pisteNourr));
		}
		
		else {
			Coordonnee position = allerAleatoire();
			this.deplacement(position);
		}

	}
	

/**
 * Cette fonction permet de recuperer grace a un toString le nom et le prnom de la fourmi
 */

	@Override
	public String toString() {
		String s = "Transporteuse " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
