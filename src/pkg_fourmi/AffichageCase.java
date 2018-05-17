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
	private int[][][] grille = new int[100][100][3]; // grille contenant les
														// couleurs
	private ArrayList<int[]> positionEnnemis = new ArrayList<int[]>();
	private ArrayList<int[]> positionFourmis = new ArrayList<int[]>();
	private ArrayList<int[]> positionEclaireuse = new ArrayList<int[]>();
	private ArrayList<int[]> positionTransporteuse = new ArrayList<int[]>();
	private Font f;
	private ArrayList<int[]> positionSoldate = new ArrayList<int[]>();
	private static String message[] = {" "," "," "};
	/**
	 * fonction definissant la taille de la grille et la couleur du fond
	 */
	public AffichageCase() {
		setSize(1000, 1000);
		setBackground(Color.blue);
	}

	public static void ecrire(Graphics2D dessin, Font f) {
		dessin.setPaint(Color.white);
		FontRenderContext frc = dessin.getFontRenderContext(); // contains
																// measurement
																// _info
		String s = new String("tour " + Simulation.getTour());
		TextLayout textlayout = new TextLayout(s, f, frc);
		textlayout.draw(dessin, 10, 30); // use TextLayout's draw method
		s = new String("Stock de nourriture :" + Simulation.getColonie().getStockNourriture());
		textlayout = new TextLayout(s, f, frc);
		textlayout.draw(dessin, 15, 47);
		s = new String("Nombre de fourmis :" + Simulation.getColonie().getMembres().size());
		textlayout = new TextLayout(s, f, frc);
		textlayout.draw(dessin, 15, 64);
		s = new String("Nombre de d'ennemis :" + Simulation.getListEnnemi().size());
		textlayout = new TextLayout(s, f, frc);
		textlayout.draw(dessin, 15, 81);
		s = message[2];
		textlayout = new TextLayout(s, f, frc);
		textlayout.draw(dessin, 15, 980);
		s = message[1];
		textlayout = new TextLayout(s, f, frc);
		textlayout.draw(dessin, 15, 960);
		s = message[0];
		textlayout = new TextLayout(s, f, frc);
		textlayout.draw(dessin, 15, 940);
	}

	/**
	 * fonction affichant les ennemis en rouge et les fourmis en noir
	 * 
	 * @param g
	 *            dessin pour les insectes
	 */
	@Override // la fonction marche avec un call repaint()
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D dessin = (Graphics2D) g;
		this.f = new Font("Helvetica", Font.BOLD, 17);
		try {
			f = new Font("Minecraft", Font.BOLD, 17);
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				dessin.setPaint(new Color(grille[i][j][0], grille[i][j][1], grille[i][j][2]));
				dessin.fill(new Rectangle2D.Double(i * 10, j * 10, 10, 10));

			}
		}
		dessin.setPaint(Color.red);
		for (int[] xy : this.positionEnnemis) {
			dessin.fill(new Ellipse2D.Double(xy[0] * 10, xy[1] * 10, 10, 10));
		}
		dessin.setPaint(Color.black);
		for (int[] xy : this.positionFourmis) {
			dessin.fill(new Ellipse2D.Double(xy[0] * 10, xy[1] * 10, 10, 10));
		}

		dessin.setPaint(Color.blue);
		for (int[] xy : this.positionEclaireuse) {
			dessin.fill(new Ellipse2D.Double(xy[0] * 10 + 2, xy[1] * 10 + 2, 5, 5));
		}
		dessin.setPaint(Color.green);
		for (int[] xy : this.positionTransporteuse) {
			dessin.fill(new Ellipse2D.Double(xy[0] * 10 + 2, xy[1] * 10 + 2, 5, 5));
		}
		dessin.setPaint(Color.magenta);
		for (int[] xy : this.positionSoldate) {
			dessin.fill(new Ellipse2D.Double(xy[0] * 10 + 2, xy[1] * 10 + 2, 5, 5));
		}
		if (Simulation.isContinuer()) {
			ecrire(dessin, f);
		} else {
			dessin.setPaint(Color.white);
			Font f2 = new Font("Helvetica", Font.BOLD, 100);
			FontRenderContext frc = dessin.getFontRenderContext();
			String s = new String("PERDUE");
			TextLayout textlayout = new TextLayout(s, f2, frc);
			textlayout.draw(dessin, 250, 500);
		}
	}

	/**
	 * fonction utilisee pour faire des tests sur l'affichage graphique a l'aide
	 * d'une fonction random pour tester les differentes teintes de couleur
	 * 
	 * @param c
	 *            les cases de la grille
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
		this.positionEnnemis.clear();
		this.positionFourmis.clear();
		this.positionEnnemis.add(new int[] { 0, 20 });
		this.positionFourmis.add(new int[] { 15, 70 });
	}

	/**
	 * fonction fixant une valeur maximale pour la nourriture et les pheromones
	 * afin de creer une echelle de teintes elle recupere la position des
	 * insectes et fixe les couleurs pour les cases occupees par des pheromones
	 * ou de la nourriture
	 * 
	 * @param cases
	 *            les cases de la grille
	 */
	public void setGrille(Case[][] cases) {
		this.positionEnnemis.clear();
		this.positionFourmis.clear();
		this.positionEclaireuse.clear();
		this.positionTransporteuse.clear();
		this.positionSoldate.clear();

		float valeurMaxNouriture = 100;
		float valeurMaxPherNouriture = 100;
		float valeurMaxPherDanger = 100;

		int x = 0;
		int y = 0;

		for (Case[] colones : cases) {
			for (Case c : colones) {
				x = c.getPosition().getX();
				y = c.getPosition().getY();
				if (c.getInsecte() != null) {
					if (c.getInsecte() instanceof Fourmi) {
						this.positionFourmis.add(new int[] { x, y });
						if (c.getInsecte() instanceof Transporteuse) {
							this.positionTransporteuse.add(new int[] { x, y });
						} else if (c.getInsecte() instanceof Eclaireuse) {
							this.positionEclaireuse.add(new int[] { x, y });
						} else if (c.getInsecte() instanceof Soldate) {
							this.positionSoldate.add(new int[] { x, y });
						}
					} else {
						this.positionEnnemis.add(new int[] { x, y });
					}
				}
				// x=c.getPosition().getX();
				// y=c.getPosition().getY();
				float nourriture = (float) (c.getNourriture());
				float pheroD = (float) (c.getPheroDanger());
				float pheroN = (float) (c.getPheroNourriture());
				int typeTerritoire;
				if (c.getType() == TypeCase.Fourmiliere) {
					typeTerritoire = 0;
				} else if (c.getType() == TypeCase.Territoire) {
					typeTerritoire = 1;
				} else {
					typeTerritoire = 2;
				}
				this.grille[x][y][1] = (int) (100 + typeTerritoire * 33 + 150 * (nourriture / valeurMaxNouriture));
				this.grille[x][y][0] = (int) (100 + typeTerritoire * 33 + 150 * (pheroD / valeurMaxPherDanger));
				this.grille[x][y][2] = (int) (100 + typeTerritoire * 33 + 150 * (pheroN / valeurMaxPherNouriture));
				for (int i = 0; i < 3; i++) {
					if (this.grille[x][y][i] > 255) {
						this.grille[x][y][i] = 255;
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Simulation.initialisation(1, 1, 1, 1, 0);
		JFrame fenetre = new JFrame();
		AffichageCase cases = new AffichageCase();
		fenetre.add(cases);
		fenetre.setSize(1000, 1000);
		cases.setGrilleR(null);
		cases.repaint();
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
	}

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message[0] = this.message[1];
		this.message[1] = this.message[2];		
		this.message[2] = message;
	}

}
