package pkg_fourmi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AffichageCase extends JPanel {
	int[][][] grille = new int[100][100][3]; // grille contenant les couleurs
	ArrayList<int[]> postionEnnemis = new ArrayList<int[]>();
	ArrayList<int[]> postionFourmis = new ArrayList<int[]>();
	public AffichageCase() {
		setSize(1000, 1000);
		setBackground(Color.blue);
	}

	@Override // la fonction marche avec un call repaint()
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D dessin = (Graphics2D) g;

		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				dessin.setPaint(new Color(grille[i][j][0], grille[i][j][1], grille[i][j][2]));
				dessin.fill(new Rectangle2D.Double(i * 10, j * 10, 10, 10));

			}
		}
		dessin.setPaint(Color.red);
		for(int[] xy:this.postionEnnemis){
			dessin.fill(new Ellipse2D.Double(xy[0]*10, xy[1]*10, 10, 10));
		}
		dessin.setPaint(Color.black);
		for(int[] xy:this.postionFourmis){
			dessin.fill(new Ellipse2D.Double(xy[0]*10, xy[1]*10, 10, 10));
		}
	}

	public void setGrilleR(Case[][] c) {
		int Max = 240;
		int Min = 0;
		int truc = 0;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				for (int a = 0; a < 3; a++) {
					truc = (int) (Math.random() * (Max - Min));
					this.grille[i][j][a] = truc;
					System.out.println(truc);

				}
			}
		}
		this.postionEnnemis.clear();
		this.postionFourmis.clear();
		this.postionEnnemis.add(new int[] {0,20});
		this.postionFourmis.add(new int[] {15,70});
	}
	public void setGrille(Case[][] cases) {
		this.postionEnnemis.clear();
		this.postionFourmis.clear();
		
		int valeurMaxNouriture = 1000;
		int valeurMaxPherNouriture =1000;
		int valeurMaxPherDanger = 1000;
		
		int x = 0;
		int y = 0;
		
		for(Case[] colones: cases){
			for(Case c: colones){
				if(c.getInsecte()!=null){
					if(c.getInsecte() instanceof Fourmi){
						this.postionFourmis.add(new int[] {x,y});
					}
					else{
						this.postionEnnemis.add(new int[] {x,y});
					}
				}
				x=c.getPosition().getX();
				y=c.getPosition().getY();
				
				this.grille[x][y][0] = (int) ( 255*(c.getNourriture()/valeurMaxNouriture));
				this.grille[x][y][1] = (int) ( 255*(c.getPheroDanger()/valeurMaxPherDanger));
				this.grille[x][y][2] = (int) ( 255*(c.getPheroNourriture()/valeurMaxPherNouriture));
				for(int i=0;i<3;i++){
					if(this.grille[x][y][i]>255){
						this.grille[x][y][i]=255;
					}
				}
			}
		}
	}


	public static void main(String[] args) {
		JFrame fenetre = new JFrame();
		AffichageCase cases = new AffichageCase();
		fenetre.add(cases);
		fenetre.setSize(1000, 1000);
		cases.setGrilleR(null);
		cases.repaint();
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
	}

}
