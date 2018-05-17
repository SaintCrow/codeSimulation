package pkg_fourmi;

import java.util.ArrayList;

public class Soldate extends Fourmi{
	
	
	/**
	 * constructeur
	 * On prend en parametre la position de la soldate et la colonie a laquelle elle appartient
	 * 
	 * @param position
	 * @param colonie
	 */
	
	public Soldate(Coordonnee position, Colonie colonie) {
		super(position, 2, 2, colonie);
	}
	
	/**
	 * Cette fonction recherche les ennemis a proximite (dans le champ de vision de la fourmi)
	 * Si elle ne trouve pas d'ennemi, elle se deplace aleatoirement 
	 * Si elle detecte un ennemi, elle pose une pheromone de danger sur sa case s'il ne s'en trouve pas deja
	 * une, puis se deplace au contact de l'ennemi si elle n'y est pas deja et l'attaque si elle se trouve au contact
	 */
	
	public void action(){
		
		Coordonnee coordEnnemi = this.rechercheEnnemi();
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
		
		else {
			Coordonnee position = allerAleatoire();
			this.deplacement(position);
		}
		
	}
	
	/**
	 * Cette fonction choisit aleatoirement la destination de la fourmi parmi la liste des destinations
	 * possibles (cases adjacentes)
	 * Les soldates parcourent le territoire et ne s'aventurent pas dans la zone de danger en temps normal
	 */
	
	
	
	@Override
	public Coordonnee allerAleatoire() {
		int insecteX = this.getPosition().getX();
		int insecteY = this.getPosition().getY();
		ArrayList<Coordonnee> posiPossible = new ArrayList<Coordonnee>();
		posiPossible.add(new Coordonnee(insecteX + 1, insecteY));
		posiPossible.add(new Coordonnee(insecteX - 1, insecteY));
		posiPossible.add(new Coordonnee(insecteX, insecteY + 1));
		posiPossible.add(new Coordonnee(insecteX, insecteY - 1));
		ArrayList<Coordonnee> listPosition = new ArrayList<Coordonnee>();
		
		for (Coordonnee position : posiPossible) {
			Case c = Simulation.getGrille()[position.getX()][position.getY()];
			if (position.estCorrecte() && c.getType() != TypeCase.Badlands && c.getInsecte() == null) {
				listPosition.add(position);
			}
			posiPossible = listPosition;
		}

		if (posiPossible.size() != 0) {
			int indice = (int) (Math.random() * listPosition.size());
			return listPosition.get(indice);
		} else {
			return this.getPosition();
		}
	}
	
	
	/**
	 * Cette fonction toString nous permet de recuperer plus facilement le nom et le prenom de la soldate
	 * 
	 * @return s 
	 */
	
	@Override
	public String toString(){
		String s = "Soldate " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
