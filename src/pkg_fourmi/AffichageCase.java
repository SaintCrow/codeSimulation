package pkg_fourmi;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AffichageCase extends JPanel {
	/**
	 * fonction affichant la grille
	 */
	private static final long serialVersionUID = 1L;
	int[][][] grille = new int[100][100][3]; // grille contenant les couleurs
	ArrayList<int[]> postionEnnemis = new ArrayList<int[]>();
	ArrayList<int[]> postionFourmis = new ArrayList<int[]>();
	ArrayList<int[]> postionEclaireuse = new ArrayList<int[]>();
	ArrayList<int[]> postionTransporteuse = new ArrayList<int[]>();
	
	
	
	/**
	 * fonction definissant la taille de la grille et la couleur du fond
	 */
	public AffichageCase() {
		setSize(1000, 1000);
		setBackground(Color.blue);
	}

	/**
	 * fonction affichant les ennemis en rouge et les fourmis en noir
	 * @param g dessin pour les insectes
	 */
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
		
		dessin.setPaint(Color.blue);
		for(int[] xy:this.postionEclaireuse){
			dessin.fill(new Ellipse2D.Double(xy[0]*10+2, xy[1]*10+2, 5, 5));
		}
		dessin.setPaint(Color.green);
		for(int[] xy:this.postionTransporteuse){
			dessin.fill(new Ellipse2D.Double(xy[0]*10+2, xy[1]*10+2, 5, 5));
		}
		dessin.setPaint(Color.white);
		InputStream is = AffichageCase.class.getResourceAsStream("/Minecraft.ttf");
		Font f = new Font("Helvetica",Font.BOLD, 24);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		try {
			f = new Font("Minecraft",Font.BOLD, 24);
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		FontRenderContext frc = dessin.getFontRenderContext(); //contains measurement _info
		String s = new String("tour "+Simulation.getTour());
		TextLayout textlayout = new TextLayout(s, f, frc);
		textlayout.draw(dessin, 10,30);  //use TextLayout's draw method
	}
	
	/**
	 * fonction utilisee pour faire des tests sur l'affichage graphique a l'aide d'une fonction random
	 * pour tester les differentes teintes de couleur 
	 * @param c les cases de la grille
	 */

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
	
	/**
	 * fonction fixant une valeur maximale pour la nourriture et les pheromones afin de creer une echelle de teintes
	 * elle recupere la position des insectes et fixe les couleurs pour les cases occupees par des pheromones ou de la nourriture
	 * @param cases les cases de la grille
	 */
	public void setGrille(Case[][] cases) {
		this.postionEnnemis.clear();
		this.postionFourmis.clear();
		this.postionEclaireuse.clear();
		this.postionTransporteuse.clear();
		
		float valeurMaxNouriture = 100;
		float valeurMaxPherNouriture =100;
		float valeurMaxPherDanger = 100;
		
		int x = 0;
		int y = 0;
		
		for(Case[] colones: cases){
			for(Case c: colones){
				x=c.getPosition().getX();
				y=c.getPosition().getY();
				if(c.getInsecte()!=null){
					if(c.getInsecte() instanceof Fourmi){
						this.postionFourmis.add(new int[] {x,y});
						if(c.getInsecte() instanceof Transporteuse){
							this.postionTransporteuse.add(new int[] {x,y});
						}
						else if(c.getInsecte() instanceof Eclaireuse){
							this.postionEclaireuse.add(new int[] {x,y});
						}
					}
					else{
						this.postionEnnemis.add(new int[] {x,y});
					}
				}
				//x=c.getPosition().getX();
				//y=c.getPosition().getY();
				float nourriture = (float)(c.getNourriture());
				float pheroD = (float)(c.getPheroDanger());
				float pheroN = (float)(c.getPheroNourriture());
				int typeTerritoire;
				if (c.getType() == TypeCase.Fourmiliere){
					typeTerritoire = 0;
				}
				else if(c.getType() == TypeCase.Territoire){
					typeTerritoire = 1;
				}
				else{
					typeTerritoire = 2;
				}
				this.grille[x][y][1] = (int) (100+typeTerritoire*33 + 150*(nourriture/valeurMaxNouriture));
				this.grille[x][y][0] = (int) (100+typeTerritoire*33 + 150*(pheroD/valeurMaxPherDanger));
				this.grille[x][y][2] = (int) (100+typeTerritoire*33 + 150*(pheroN/valeurMaxPherNouriture));
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
