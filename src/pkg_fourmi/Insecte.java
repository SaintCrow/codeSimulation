package pkg_fourmi;

public abstract class Insecte {
	
	private Coordonnee position;
	private Nom nom;
	private Prenom prenom;
	private int champvision;
	private int endurance;
	private int force;
	
	public Insecte(Coordonnee position, Nom nom, Prenom prenom, int champvision, int endurance, int force) {
		this.position = position;
		this.nom = nom;
		this.prenom = prenom;
		this.champvision = champvision;
		this.endurance = endurance;
		this.force = force;
	}

	public Coordonnee getPosition() {
		return position;
	}

	public void setPosition(Coordonnee position) {
		this.position = position;
	}

	public Nom getNom() {
		return nom;
	}

	public void setNom(Nom nom) {
		this.nom = nom;
	}

	public Prenom getPrenom() {
		return prenom;
	}

	public void setPrenom(Prenom prenom) {
		this.prenom = prenom;
	}

	public int getChampvision() {
		return champvision;
	}

	public void setChampvision(int champvision) {
		this.champvision = champvision;
	}
	
	public int getEndurance() {
		return endurance;
	}

	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	public abstract void action();
	

}
