package modele;

import controleur.Controle;
import outils.connexion.Connection;

/**
 * Informations et méthodes communes aux jeux client et serveur
 *
 */
public abstract class Jeu {

	/**
	 * Réception d'une connexion (pour communiquer avec un ordinateur distant)
	 * @param connection de type Connection
	 */
	public abstract void connexion(Connection connection) ;
	
	protected Controle controle;
	
	/**
	 * Réception d'une information provenant de l'ordinateur distant
	 * @param connection de type Connection
	 * @param objet de type Object
	 */
	public abstract void reception(Connection connection, Object objet) ;
	
	/**
	 * Déconnexion de l'ordinateur distant
	 */
	public abstract void deconnexion(Connection connection);
	
	/**
	 * Envoi d'une information vers un ordinateur distant
	 * @param connection de type Connection
	 * @param objet de type Object
	 */
	public void envoi(Connection connection, Object objet) {
		controle.envoie(connection, objet);
	}
	
}
