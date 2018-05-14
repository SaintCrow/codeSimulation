package pkg_fourmi;

public class Soldate extends Fourmi{
	
	public Soldate(Coordonnee position, Colonie colonie) {
		super(position, 2, 2, colonie);
	}
	
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
			System.out.println(this.toString()+this.getPosition().toString());
		}
		
	}
	
	@Override
	public String toString(){
		String s = "Soldate " + this.getPrenom() + " " + this.getNom();
		return s;
	}

}
