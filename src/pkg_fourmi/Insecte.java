package pkg_fourmi;

public abstract class Insecte {
	
	private Coordonnee position;
	private Nom nom;
	private Prenom prenom;
	private int champvision;
	
	public Insecte(Coordonnee position, Nom nom, Prenom prenom, int champvision) {
		this.position = position;
		this.nom = nom;
		this.prenom = prenom;
		this.champvision = champvision;
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
	
	public abstract void action();
	

}
