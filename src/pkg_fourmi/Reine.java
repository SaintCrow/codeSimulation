package pkg_fourmi;

import java.util.Random;

public class Reine extends Fourmi {

	/**
	 * constructeur On prend en parametre la position de la reine et la colonie
	 * a laquelle elle appartient
	 * 
	 * @param position
	 * @param colonie
	 */

	public Reine(Coordonnee position, Colonie colonie) {
		super(position, 1, 1, colonie);
	}

	/**
	 * La fonction action (appelee par la simulation) fait pondre la reine La
	 * ponte ne se produit que si le stock de nourriture est suffisant (valeur
	 * arbitraire de 50)
	 */

	@Override
	public void action() {

		Coordonnee coordEnnemi = this.rechercheEnnemi();
		Coordonnee coordFourmi = this.getPosition();
		int x = coordFourmi.getX();
		int y = coordFourmi.getY();

		if (this.getColonie().getStockNourriture() > this.getColonie().getMembres().size()) {
			this.pondre();
		}
		if (coordEnnemi != null) {
			if (Simulation.getGrille()[x][y].getPheroDanger() == 0) {
				this.poserPheromoneDanger();
			}
		}
	}

	/**
	 * Cette fonction fait pondre la reine avec une probabilite de 0.8 Ensuite,
	 * la reine a une probabilite egale de 1/3 de pondre chaque type de fourmi
	 * (soldate, transporteuse et eclaireuse) La ponte ajoute une fourmi dans
	 * une case adjacente a la reine (mode de fonctionnement identique au
	 * deplacement, c'est pour cela que l'on utilise la fonction aller aleatoire
	 * pour la faire apparaitre) Chaque ponte consomme de la nourriture et les
	 * soldates necessitent plus de nourriture que les ouvrieres pour etre
	 * pondues Apres la ponte, le nom de la nouvelle fourmi est affichee a
	 * l'aide de sa fonction toString
	 */

	public void pondre() {

		Random rd = new Random();

		if (rd.nextFloat() > 0.8) {

			int choixPonte = rd.nextInt(4);
			Coordonnee position = this.allerAleatoire();
			if (!(position.getX() == this.getPosition().getX() && position.getY() == this.getPosition().getY())) {
				int stockNourriture = this.getColonie().getStockNourriture();

				if (choixPonte == 0) {
					Fourmi fourmi = new Soldate(position, this.getColonie());
					this.getColonie().ajouterFourmi(fourmi);
					this.getColonie().setStockNourriture(stockNourriture - 5);
				}

				else if (choixPonte == 1) {
					Fourmi fourmi = new Eclaireuse(position, this.getColonie());
					this.getColonie().ajouterFourmi(fourmi);
					this.getColonie().setStockNourriture(stockNourriture - 2);
				}

				else {
					Fourmi fourmi = new Transporteuse(position, this.getColonie());
					this.getColonie().ajouterFourmi(fourmi);
					this.getColonie().setStockNourriture(stockNourriture - 2);
				}

			}
		}
	}

	/**
	 * Cette fonction toString nous permet de recuperer plus facilement le nom
	 * et le prenom de la reine
	 */

	@Override
	public String toString() {
		String s = "Reine " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
