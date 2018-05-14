package pkg_fourmi;

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
	 * Cette fonction toString nous permet de recuperer plus facilement le nom et le prenom de la soldate
	 */
	
	@Override
	public String toString(){
		String s = "Soldate " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
