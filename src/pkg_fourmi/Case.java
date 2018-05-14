package pkg_fourmi;

public class Case {
	private Coordonnee position;
	private int pheroNourriture;
	private int pheroDanger;
	private Insecte insecte;
	private int nourriture;
	private TypeCase type;
	
	/**
	 * constructeur de case 
	 * ( la case au début ne contient pas de pheromones)
	 * @param pos position de la case
	 * @param ins insecte sur la case (null si vide)
	 * @param nour quantite de nourriture
	 */
	public Case(Coordonnee pos, Insecte ins, int nour, TypeCase type){
		this.position = pos;
		this.pheroNourriture = 0;
		this.pheroDanger = 0;
		this.insecte = ins;
		this.nourriture = nour;
		this.type = type;
	}
	
	/**
	 * getter type de la case
	 * 
	 * @return type
	 */
	
	
	public TypeCase getType() {
		return type;
	}
	
	/**
	 * getter pheromone de nourriture
	 * 
	 * @return pheromoneNourriture
	 */
	public int getPheroNourriture() {
		return pheroNourriture;
	}
	
	/**
	 * getter pheromone de danger
	 * 
	 * @return pheroDanger
	 */

	public int getPheroDanger() {
		return pheroDanger;
	}
	
	/**
	 * getter insecte
	 * 
	 * @return insecte
	 */

	public Insecte getInsecte() {
		return insecte;
	}
	
	/**
	 * getter nourriture
	 * 
	 * @return nourriture
	 */

	public int getNourriture() {
		return nourriture;
	}
	
	/**
	 * getter position
	 * 
	 * @return position
	 */
	
	public Coordonnee getPosition() {
		return position;
	}


	/**
	 * incremente la nourriture de la case 
	 * de la quantite q
	 * @param q quantite de nourriture
	 */
	public void addNourriture(int q){
		this.nourriture+= q;		
	}
	/**
	 * incremente pheroDanger
	 * @param q
	 */
	public void addPheroDanger(int q){
		this.pheroDanger+= q;		
	}
	/**
	 * incremente pheroNourriture
	 * @param q
	 */
	public void addPheroNourriture(int q){
		this.pheroNourriture+= q;		
	}
	
	/**
	 * set insecte insecte
	 * 
	 * @param insecte
	 */

	public void setInsecte(Insecte insecte) {
		this.insecte = insecte;
		
	}
	
}
