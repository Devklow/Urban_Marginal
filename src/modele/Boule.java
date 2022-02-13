package modele;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;

/**
 * Gestion de la boule
 *
 */
public class Boule extends Objet implements Runnable{

	/**
	 * instance de JeuServeur pour la communication
	 */
	private JeuServeur jeuServeur ;
	private ArrayList<Mur> murs;
	private Joueur joueur;
	
	/**
	 * Constructeur
	 */
	
	public Boule(JeuServeur jeuServeur, ArrayList<Mur> lesMurs) {
		this.murs = lesMurs;
		this.jeuServeur = jeuServeur;
		this.jLabel = new JLabel();
		jeuServeur.controle.evenementJeuServeur("ajout label",this.jLabel);
		URL img = getClass().getClassLoader().getResource(Global.bouleEmplacement);
		this.jLabel.setIcon(new ImageIcon(img));
		this.jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.jLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.jLabel.setBounds(0,0,this.jLabel.getIcon().getIconWidth(), this.jLabel.getIcon().getIconHeight());
		this.jLabel.setVisible(false);
	}

	/**
	 * Tire d'une boule
	 */
	public void tireBoule(Joueur joueur) {
		this.joueur = joueur;
		new Thread(this).start();
	}
	/**
	 * Methode run permettant le multithreading
	 */
	@Override
	public void run() {
		joueur.affiche("marche", 1);
		Joueur victime = null;
		Integer lePas;
		this.posY = joueur.getPosY()+((int)joueur.jLabel.getIcon().getIconHeight()/2-this.jLabel.getIcon().getIconHeight()/2);
		if(joueur.getOrientation()==1) {
			this.posX = joueur.getPosX()+((int)joueur.jLabel.getBounds().width)+1;
			this.jLabel.setBounds(this.posX, this.posY,this.jLabel.getIcon().getIconWidth(), this.jLabel.getIcon().getIconHeight());
			lePas = +5;
		}
		else {
			this.posX = joueur.getPosX()-((int)joueur.jLabel.getBounds().width)-1;
			this.jLabel.setBounds(this.posX, this.posY,this.jLabel.getIcon().getIconWidth(), this.jLabel.getIcon().getIconHeight());
			lePas = -5;
		}
		this.jLabel.setVisible(true);
		jeuServeur.envoieSonATous(0);
		do {
			this.posX+=lePas;
			this.jLabel.setBounds(this.posX, this.posY,this.jLabel.getIcon().getIconWidth(), this.jLabel.getIcon().getIconHeight());
			jeuServeur.envoieJeuATous();
			Collection<Objet>lesjoueurs = (Collection)jeuServeur.getJoueurs();
			victime = (Joueur)toucheCollectionObjets(lesjoueurs);
		}while(inArena() && toucheCollectionObjets((Collection)murs)==null && victime==null);
		if(victime!=null) {
			if(!victime.estMort()) {
				jeuServeur.envoieSonATous(1);
				victime.perteVie();
				joueur.gainVie();
				for(int k=1; k<=2;k++) {
					victime.affiche("touche",k);
					pause(80,0);
				}
				if(victime.estMort()) {
					jeuServeur.envoieSonATous(2);
					for(int k=1; k<=2;k++) {
						victime.affiche("mort",k);
						pause(80,0);
					}
				}
				else {
					pause(80,0);
					victime.affiche("marche",1);
				}
			}
		}
		this.jLabel.setVisible(false);
		jeuServeur.envoieJeuATous();
		
	}
	
	/**
	 * Methode pause permettant la pause quelque temps du thread
	 * @param ms de type Entier long
	 * @param ns de type Entier
	 */
	public void pause(long ms, int ns){
		try {
			Thread.sleep(ms, ns);
		}catch (Exception e) {}
	}
}
