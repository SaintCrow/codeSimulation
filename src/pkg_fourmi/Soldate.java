package pkg_fourmi;

public class Soldate extends Fourmi{
	
	public Soldate(Coordonnee position, Colonie colonie) {
		super(position, 2, 2, colonie);
	}
	
	public void action(){
		
		Coordonnee coordEnnemi = this.rechercheEnnemi();
		
		if (coordEnnemi != null){
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
	
	@Override
	public String toString(){
		String s = "Soldate " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
