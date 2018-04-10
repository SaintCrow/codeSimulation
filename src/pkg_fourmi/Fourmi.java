package pkg_fourmi;

public abstract class Fourmi extends Insecte{
	
	private boolean combat;

	public Fourmi(Coordonnee position, Nom nom, Prenom prenom, int champvision, boolean combat) {
		super(position, nom, prenom, champvision);
		this.combat = combat;
	}
		
	public boolean getCombat() {
		return combat;
	}

	public void setCombat(boolean combat) {
		this.combat = combat;
	}

	public Coordonnee rechercheEnnemi(){
		
		return ;
	}
	
	public Coordonne recherchePheromoneDanger(){
		
		return ;
		
	}
	
	public void attaquer(){
		
	}

}
