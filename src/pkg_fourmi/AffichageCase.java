package pkg_fourmi;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AffichageCase extends JPanel {
	int[][][] grille = new int[100][100][3]; // grille contenant les couleurs

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
	}
	public void setGrille(Case[][] c) {
		int valeurMaxNouriture = 1000;
		int valeurMaxPherNouriture =1000;
		int valeurMaxPher
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
