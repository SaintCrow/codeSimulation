package pkg_fourmi;

import java.util.List;

public class Transporteuse  extends Eclaireuse{
	
	private boolean nourriture;

	public Transporteuse(Coordonnee position, Nom nom, Prenom prenom, int champvision, int endurance, int force,
			boolean combat, boolean nourriture) {
		super(position, nom, prenom, champvision, endurance, force, combat);
		this.nourriture = nourriture;
	}

	public boolean getNourriture() {
		return nourriture;
	}

	public void setNourriture(boolean nourriture) {
		this.nourriture = nourriture;
	}
	
	public void recupererNourriture(){
		
	}
	
}
