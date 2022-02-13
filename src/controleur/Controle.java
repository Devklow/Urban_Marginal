package controleur;

import vue.*;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.*;
import outils.connexion.*;
import outils.son.*;

/**
 * Contr�leur et point de d�marrage de l'applicaton 
 * @author Joshua Labb�
 *
 */
public class Controle implements AsyncResponse, Global{

	private EntreeJeu frmEntreeJeu ;
	private ChoixJoueur frmChoixJoueur;
	private Arene frmArene;
	private ServeurSocket server;
	private ClientSocket client;
	private Jeu lejeu;
	private int port = 80;

	/**
	 * M�thode de d�marrage
	 * @param args non utilis�
	 */
	public static void main(String[] args) {
		new Controle();
	}
	
	/**
	 * M�thode de cr�ation d'instance Serveur ou Client appel�e par la classe vue.Entr�eJeu
	 * @param info de type chaine de texte
	 * envoie l'information si c'est un client ou un serveur
	 */

	public void evenementEntreeJeu(String info){
		this.frmArene = new Arene(this);
		this.frmEntreeJeu.dispose();
		if (info == "serveur") {
			this.server = new ServeurSocket(this, port);
			this.frmArene.setVisible(true);
			this.lejeu = new JeuServeur(this);
			((JeuServeur) lejeu).constructionMurs();
		} 
		else {
			this.client = new ClientSocket(this, info, port);
			this.frmChoixJoueur = new ChoixJoueur(this);
			frmChoixJoueur.setVisible(true);
			}
		}
	
	/**
	 * M�thode appel�e par la classe Arene
	 * @param info de type Entier ou chaine de texte
	 */

	public void evenementArene(Object info) {
		if(lejeu instanceof JeuClient) {
			if(info instanceof String) {
				((JeuClient)lejeu).envoi(Global.messageText+Global.splitSymbol+info);
			}
			else if(info instanceof Integer) {
				((JeuClient)lejeu).envoi(Global.deplacementText+Global.splitSymbol+info);
			}
			}
		else if(info instanceof String){
			((JeuServeur)lejeu).envoieMessageATous("Console"+" > "+info);
		}
	}
	
	/**
	 * M�thode appel�e par la classe Arene
	 * Si la fen�tre est ferm� et que c'est un serveur, cela d�connecte et ferme tout les clients
	 */
	public void evenementQuitter() {
		if(lejeu instanceof JeuServeur) {
			((JeuServeur)lejeu).deconnectAll();
		}
	}
	
	/**
	 * M�thode qui g�re l'envoie des informations aux clients
	 * @param ordre de type Chaine de texte
	 * @param objet de type Object
	 */
	public void evenementJeuServeur(String ordre, Object info){
		if(ordre=="ajout mur") {
			frmArene.ajoutMurs(info);
		}
		else if (ordre=="ajout panel murs") {
			((JeuServeur)lejeu).envoi((Connection)info, this.frmArene.getMurs());
		}
		else if (ordre=="ajout label") {
			frmArene.ajoutJLabelJeu((JLabel)info);
		}
		else if (ordre=="ajout panel label") {
			((JeuServeur)lejeu).envoi((Connection)info, this.frmArene.getJeu());
		}
		else if (ordre=="ajout message") {
			((JeuServeur)lejeu).envoi((Connection)info, this.frmArene.getMessages());
		}
		else if (ordre=="supp label") {
			frmArene.suppLabelJeu((JLabel)info);
		}
	}
	/**
	 * M�thode qui g�re la r�ception des informations du clients
	 * @param ordre de type chaine de texte 
	 * @param info de type objet
	 */
	
	public void evenementJeuClient(String ordre, Object info){
		if(ordre=="ajout mur") {
			frmArene.setMurs((JPanel)info);
		}
		else if(ordre=="ajout jeu") {
			frmArene.setJeu((JPanel)info);
		}
		else if(ordre=="ajout message") {
			frmArene.setMessages((String)info);
		}
		else if(ordre=="jouer son") {
			new Son((getClass().getClassLoader().getResource(Global.lstSon.get((Integer)info)))).play();
		}
		else if(ordre=="fermer") {
			frmArene.close();
		}
	}
	/**
	 * M�thode qui g�re l'envoie des informations de personnage au serveur
	 * @param pseudo de type chaine de texte 
	 * @param nbPerso de type entier
	 */
	public void evenementChoixJoueur(String pseudo, int nbPerso){
		if(pseudo.isBlank()) {
			this.frmChoixJoueur.ErrMsg();
		}
		else {
			this.frmChoixJoueur.dispose();
			this.frmArene.setVisible(true);
			((JeuClient)lejeu).envoi(Global.pseudoText+Global.splitSymbol+pseudo+Global.splitSymbol+nbPerso);
		}
	}
	/**
	 * M�thode qui permet d'envoyer d'une instance � une autre instance un objet
	 * @param connection de type Connection 
	 * @param objet de type Object
	 */
	public void envoie(Connection connection, Object objet) {
		connection.envoi(objet);
	}
	
	/**
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this) ;
		this.frmEntreeJeu.setVisible(true);
	}

	/**
	 * M�thode de r�ception d'un objet provenant d'une connection avec un ordre
	 * @param connection de type Connection
	 * @param ordre de type chaine de texte
	 * @param objet de type Object
	 */
	@Override
	public void reception(Connection connection, String ordre, Object objet) {
		switch(ordre) {
			case "connexion":
				if(!(lejeu instanceof JeuServeur)) {
				this.lejeu = new JeuClient(this);
				lejeu.connexion(connection);
			}
			else {
				lejeu.connexion(connection);
			}
				break;
			case "r�ception":
				lejeu.reception(connection, objet);
				break;
			case "d�connexion" :
				lejeu.deconnexion(connection);
				break;
		}
	}
}
