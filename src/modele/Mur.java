package modele;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;


/**
 * Gestion des murs
 *
 */
public class Mur extends Objet {
	
	
	/**
	 * Constructeur
	 */
	public Mur() {
		this.jLabel = new JLabel();
		URL wall = getClass().getClassLoader().getResource(Global.wallEmplacement);
		this.posX = (int) Math.round(Math.random()*Global.arenaWitdh+18);
		this.posY = (int) Math.round(Math.random()*Global.arenaHeight+18);
		this.jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.jLabel.setIcon(new ImageIcon(wall));
		this.jLabel.setBounds(this.getPosX(),this.posY, this.jLabel.getIcon().getIconWidth(), this.jLabel.getIcon().getIconHeight());
	}
	
}
