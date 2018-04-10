package pkg_fourmi;

public class Transporteuse  extends Eclaireuse{
	
	private boolean nourriture;

	public Transporteuse(Coordonnee position, Nom nom, Prenom prenom, int champvision, boolean combat,
			boolean nourriture) {
		super(position, nom, prenom, champvision, combat);
		this.nourriture = nourriture;
	}

	public boolean getNourriture() {
		return nourriture;
	}

	public void setNourriture(boolean nourriture) {
		this.nourriture = nourriture;
	}
	
	
	

}
