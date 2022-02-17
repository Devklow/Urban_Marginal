package modele;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;

/**
 * Gestion des joueurs
 *
 */
public class Joueur extends Objet {
	

	/**
	 * vie de départ pour tous les joueurs
	 */
	private static final int MAXVIE = 10 ;
	/**
	 * gain de points de vie lors d'une attaque
	 */
	private static final int GAIN = 1 ; 
	/**
	 * perte de points de vie lors d'une attaque
	 */
	private static final int PERTE = 2 ; 
	/**
	 * Label du Pseudo
	 */
	private JLabel pseudoLbl;
	/**
	 * pseudo saisi
	 */
	private String pseudo ;
	/**
	 * n° correspondant au personnage (avatar) pour le fichier correspondant
	 */
	private int numPerso ; 
	/**
	 * instance de JeuServeur pour communiquer avec lui
	 */
	private JeuServeur jeuServeur ;
	/**
	 * numéro d'étape dans l'animation (de la marche, touché ou mort)
	 */
	private int etape ;
	/**
	 * la boule du joueur
	 */
	private Boule boule ;
	/**
	 * vie restante du joueur
	 */
	private int vie ; 
	/**
	 * tourné vers la gauche (0) ou vers la droite (1)
	 */
	private int orientation ;
	
	/**
	 * Getter sur l'orientation du personnage
	 * @return l'orientation de type int
	 */
	public int getOrientation(){
		return this.orientation;
	}
	
	/**
	 * Constructeur
	 */
	public Joueur() {
	}

	/**
	 * Getter sur le label du Pseudo
	 * @return pseudoLbl de type Label
	 */
	public JLabel getpseudoLbl(){
		return this.pseudoLbl;
	}
	
	/**
	 * Getter sur le pseudop
	 * @return pseudo de type chaine de texte
	 */
	
	public String getpseudo() {
		return this.pseudo;
	}

	/**
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position, affichage, création de la boule)
	 * @param pseudo de type chaine de texte
	 * @param numPerso de type Entier
	 * @param lesJoueurs de type ArrayList de Joueur
	 * @param lesMurs de type ArrayList de Mur
	 * @param jeuServeur de type JeuServeur
	 */
	public void initPerso(String lepseudo, int numPerso, ArrayList<Joueur> lesJoueurs, ArrayList<Mur> lesMurs, JeuServeur jeuServeur) {
		lesJoueurs.remove(this);
		this.boule = new Boule(jeuServeur, lesMurs);
		this.etape = 1;
		this.orientation = 1;
		this.jeuServeur = jeuServeur;
		this.jLabel = new JLabel();
		this.pseudo = lepseudo;
		this.numPerso = numPerso;
		this.vie = Joueur.MAXVIE;
		this.pseudoLbl = new JLabel(pseudo +" : "+this.vie);
		this.pseudoLbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.pseudoLbl.setFont(new Font("Dialog", Font.PLAIN, 9));
		premierePosition(lesJoueurs, lesMurs);
		jeuServeur.controle.evenementJeuServeur("ajout label",this.jLabel);
		jeuServeur.controle.evenementJeuServeur("ajout label",this.pseudoLbl);
		affiche("marche", 1);
	}

	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur ou un mur)
	 * @param lesJoueurs de type ArrayList de type Joueur
	 * @param lesMurs de type ArrayList de Mur
	 */
	private void premierePosition(ArrayList<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		this.posX = (int) Math.round(Math.random()*Global.arenaWitdh+18);
		this.posY = (int) Math.round(Math.random()*Global.arenaHeight+18);
		this.jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.jLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.jLabel.setBounds(this.getPosX(),this.posY, 44, 44);
		while(super.toucheCollectionObjets((Collection)lesJoueurs)!=null || super.toucheCollectionObjets((Collection)lesMurs)!=null) {
			this.posX = (int)Math.round(Math.random()*(Global.arenaWitdh-4)+22);
			this.posY = (int)Math.round(Math.random()*(Global.arenaHeight-4)+22);
			this.jLabel.setBounds(this.getPosX(),this.posY, 44, 44);
		}
		this.pseudoLbl.setHorizontalAlignment(SwingConstants.CENTER);
		this.pseudoLbl.setVerticalAlignment(SwingConstants.CENTER);
		this.pseudoLbl.setBounds(this.getPosX()+22-this.pseudoLbl.getText().length()*4,this.posY+44,this.pseudoLbl.getText().length()*8,9);
	}
	
	/**
	 * Affiche le personnage et son message
	 * @param etant de type chaine de texte
	 * @param etape de type entier
	 */
	public void affiche(String etat, int etape) {
		URL perso = getClass().getClassLoader().getResource(Global.persoEmplacement+numPerso+etat+etape+"d"+orientation+".gif");
		this.jLabel.setIcon(new ImageIcon(perso));
		this.pseudoLbl.setText(pseudo +" : "+this.vie);
		jeuServeur.envoieJeuATous();
	}

	/**
	 * Gère une action reçue et qu'il faut afficher (déplacement, tire de boule...)
	 * @param key de type Entier
	 * @param lesJoueurs de type ArrayList de Joueur
	 * @param lesMurs de type ArrayList de Mur
	 */
	public void action(int key, ArrayList<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		lesJoueurs.remove(this);
		if(!estMort()) {
			if(key==KeyEvent.VK_SPACE && !(this.boule.jLabel.isVisible())) {
				System.out.println("Lancement de la boule");
				this.boule.tireBoule(this);
			}
			else{
				deplace(key, lesJoueurs, lesMurs);
			}
		}

	}

	/**
	 * Gère le déplacement du personnage
	 * @param key de type Entier
	 * @param lesJoueurs de type ArrayList de Joueur
	 * @param lesMurs de type ArrayList de Mur
	 */
	private void deplace(int key, ArrayList<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) { 
		//deplacement haut
				if(key==KeyEvent.VK_UP) {
					this.jLabel.setBounds(this.getPosX(),this.posY-Global.pas, 44, 44);
					this.etape+=1;
				}
				//deplacement bas
				else if(key==KeyEvent.VK_DOWN) {
					this.jLabel.setBounds(this.getPosX(),this.posY+Global.pas, 44, 44);
					this.etape+=1;
				}
				//deplacement gauche
				else if(key==KeyEvent.VK_LEFT) {
					if(this.orientation==0) {
						this.etape+=1;
					}
					else {
						this.orientation = 0;
						this.etape=2;
					}
					
					this.jLabel.setBounds(this.getPosX()-Global.pas,this.posY, 44, 44);
				}
				//deplacement droite
				else if(key==KeyEvent.VK_RIGHT) {
					if(this.orientation==1) {
						this.etape+=1;
					}
					else {
						this.orientation = 1;
						this.etape=2;
					}
					this.jLabel.setBounds(this.getPosX()+Global.pas,this.posY, 44, 44);
					}
				if(this.etape==5) {
					this.etape=2;
				}
				if(super.toucheCollectionObjets((Collection)(lesMurs))==null && super.toucheCollectionObjets((Collection)(lesJoueurs))==null && inArena()) {
					this.posX = this.jLabel.getBounds().x;
					this.posY = this.jLabel.getBounds().y;
					this.pseudoLbl.setBounds(this.getPosX()+((int)this.jLabel.getIcon().getIconWidth()/2-this.pseudoLbl.getText().length()*4),this.posY+this.jLabel.getIcon().getIconHeight(),this.pseudoLbl.getText().length()*8,9);
					affiche("marche",this.etape);
					}

				else {
					this.jLabel.setBounds(this.getPosX(),this.posY, this.jLabel.getIcon().getIconWidth(), this.jLabel.getIcon().getIconHeight());
					affiche("marche",this.etape);
				}
	}



	/**
	 * Gain de points de vie après avoir touché un joueur
	 */
	public void gainVie() {
		this.vie+=Joueur.GAIN;
		if(this.vie>Joueur.MAXVIE) {
			this.vie = Joueur.MAXVIE;
		}
		this.pseudoLbl.setText(pseudo +" : "+this.vie);
		jeuServeur.envoieJeuATous();
	}
	
	/**
	 * Perte de points de vie après avoir été touché 
	 */
	public void perteVie() {
		if(this.vie>Joueur.PERTE) {
			this.vie-=Joueur.PERTE;
		}
		else {
			this.vie=0;
		}
	}
	
	/**
	 * vrai si la vie est à 0
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
			if(this.vie==0) {
			return true;
			}
		return false;
	}
	
	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
		jeuServeur.controle.evenementJeuServeur("supp label",this.jLabel);
		jeuServeur.controle.evenementJeuServeur("supp label",this.pseudoLbl);
	}
	
}
