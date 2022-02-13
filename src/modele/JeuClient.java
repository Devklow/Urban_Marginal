package modele;

import javax.swing.JPanel;

import controleur.Controle;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté client
 *
 */
public class JeuClient extends Jeu {
	
	private Connection connection;
	private boolean MurIsOk;
	
	/**
	 * Constructeur recevant l'instance du controleur
	 * @param controle de type Controle
	 */
	public JeuClient(Controle controle) {
		this.controle = controle;
	}
	
	/**
	 * Methode de connection permettant de l'instancier
	 * @param connection de type Connection
	 */
	@Override
	public void connexion(Connection connection) {
		this.connection = connection;		
	}

	/**
	 * Methode de reception permettant de reçevoir un objet provenant d'une connection
	 * @param connection de type Connection
	 * @param info de type Object
	 */
	@Override
	public void reception(Connection connection, Object info) {
		if(info instanceof JPanel) {
			if(!MurIsOk) {
				this.MurIsOk=true;
				controle.evenementJeuClient("ajout mur", info);			
			}
			else {
			controle.evenementJeuClient("ajout jeu", info);
			}
		}
		else if(info instanceof String) {
			controle.evenementJeuClient("ajout message", info);
		}
		else if(info instanceof Integer) {
			controle.evenementJeuClient("jouer son", info);
		}
		else if(info instanceof Boolean) {
			controle.evenementJeuClient("fermer", info);
		}
	}
	/**
	 * Methode de déconnection non utilisé 
	 */
	@Override
	public void deconnexion(Connection connection) {
	}

	/**
	 * Envoi d'une information vers le serveur
	 * fais appel une fois à l'envoi dans la classe Jeu
	 */
	public void envoi(String info) {
		super.envoi(this.connection, info);
	}

}
