package pkg_fourmi;

public class Transporteuse extends Eclaireuse {

	private int nourriture;

	public Transporteuse(Coordonnee position, Colonie colonie) {
		super(position, colonie);
		this.nourriture = 0;
	}

	public int getNourriture() {
		return this.nourriture;
	}

	public void setNourriture(int nourriture) {
		this.nourriture = nourriture;
	}

	public void recupererNourriture(Coordonnee position) {
		int x = position.getX();
		int y = position.getY();
		int nourritureRecup = Math.min(10, Simulation.getGrille()[x][y].getNourriture());
		Simulation.getGrille()[x][y].addNourriture(-nourritureRecup);
		this.nourriture = nourritureRecup;
		this.setRetour(true);
		Simulation.getCasesGr().setMessage(this.toString() + " a recupere de la nourriture.");
	}

	public void deposerNourriture() {
		this.getColonie().setStockNourriture(this.getColonie().getStockNourriture() + this.nourriture);
		this.nourriture = 0;
		this.setRetour(false);
		Simulation.getCasesGr().setMessage(this.toString() + " a rapporte de la nourriture a la fourmiliere.");
	}

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

	@Override
	public String toString() {
		String s = "Transporteuse " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
