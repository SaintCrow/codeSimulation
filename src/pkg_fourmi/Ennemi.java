package pkg_fourmi;

import java.util.ArrayList;

public class Ennemi extends Insecte {
	
	

	/**
	 * constructeur
	 * 
	 * @param posisition
	 */

	public Ennemi(Coordonnee position) {
		super(position, 3, 1);
	}
	
	/**
	 *
	 *Cette fonction code les ations de l'ennemi, c'est-a-dire rechercher des fourmis pour se diriger vers elles et les attaquer 
	 *ou se deplacer aleatoirement si aucune fourmi n'est en vue
	 * 
	 * 
	 */

	public void action() {

		Coordonnee coordFourmi = this.rechercheFourmi();

		if (coordFourmi != null) {
			if (this.getPosition().distance(coordFourmi) == 1) {
				this.attaquer(coordFourmi);
			} else {
				Coordonnee position = this.allerA(coordFourmi);
				this.deplacement(position);
			}

		}

		else {
			Coordonnee position = allerAleatoire();
			this.deplacement(position);
		}
	}
	
	/**
	 * Cette fonction est celle qui permet a l'ennemi de rechercher des fourmis en prenant les coordonnees des cases dans le champ de vision de l'ennemi
     * puis en verifiant s'il s'y trouve des fourmis
     * S'il en trouve, la fonction renvoie les coordonnees de la fourmi la plus proche
     * S'il n'en trouve pas, la fonction ne renvoie rien
	 */

	public Coordonnee rechercheFourmi(){
		
		Coordonnee coordEnnemi = this.getPosition();
		int x = coordEnnemi.getX();
		int y = coordEnnemi.getY();
		ArrayList<Coordonnee> listFourmi = new ArrayList<Coordonnee>();
		for (int i = x-getChampvision(); i <= x + this.getChampvision(); i++){
			for (int j = y-getChampvision(); j <= y + this.getChampvision(); j++){
				if(new Coordonnee(i,j).estCorrecte()){
				if (Simulation.getGrille()[i][j].getInsecte() instanceof Fourmi){					
					listFourmi.add(Simulation.getGrille()[i][j].getPosition());
					}
				}
			}
		}
		if (listFourmi.size() == 0){
			return null;
		}
		else if (listFourmi.size() == 1){
			return listFourmi.get(0);
		}
		else{
			Coordonnee coordPlusProche = listFourmi.get(0);
			for (int i = 1; i < listFourmi.size(); i++){
				if (coordEnnemi.distance(listFourmi.get(i)) < coordEnnemi.distance(coordPlusProche)){
					coordPlusProche = listFourmi.get(i);
				}
			}
			return coordPlusProche;
		}
		
	}
	
	/**
	 * Cette fonction permet a l'ennemi de tuer une fourmi en l'effacant de la liste des membres de la colonie
	 */

	public void tuerFourmi(Fourmi fourmi) {
		fourmi.getColonie().getMembres().remove(fourmi);
	}
	

	/**
	 * Cette fonction nous permet de recuperer facilement le nom et le prenom des ennemis pour les identifier
	 */

	@Override
	public String toString() {
		String s = "Ennemi " + this.getPrenom() + " " + this.getNom();
		return s;
	}
	
	/**
	 * Cette fonction code la mort des ennemis en les supprimant de la simulation (liste des ennemis)
     * et en ajoutant de la nourriture dans la case precedemment occupee
	 */

	@Override
	public void mourir() {
		super.mourir();
		Simulation.getListEnnemi().remove(this);
		Simulation.getGrille()[this.getPosition().getX()][this.getPosition().getY()].addNourriture(100);
	}

}
