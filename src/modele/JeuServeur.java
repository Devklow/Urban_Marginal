package modele;
import java.util.ArrayList;
import java.util.HashMap;
import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu c�t� serveur
 *
 */
public class JeuServeur extends Jeu {

	/**
	 * Collection de murs
	 */
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>() ;
	/**
	 * Collection de joueurs
	 */
	private HashMap<Connection, Joueur> lesJoueurs = new HashMap<Connection, Joueur>() ;
	
	/**
	 * Set de connection d�j� �tablies
	 */

	/**
	 * Constructeur
	 */
	public JeuServeur(Controle controle) {
		this.controle = controle;
	}
	/**
	 * Fonction qui envoie le panel comportent tout les labels a tout les clients
	 */
	public void envoieJeuATous() {
		lesJoueurs.forEach((connection, joueur) -> {
			controle.evenementJeuServeur("ajout panel label",connection);
		});
	}
	/**
	 * Fonction qui envoie l'odre du son a jouer dans l'ar�ne
	 * @son de type Entier
	 * Re�oit un int en param�tre correspondant a un son enregistr� dans la liste des sons
	 */
	public void envoieSonATous(Integer son) {
			lesJoueurs.forEach((connection, joueur) -> {
				envoi(connection, son);
			});
		}
	/**
	 * Fonction qui envoie un message sur tout les clients
	 * @param message de type chaine de texte
	 * Re�oit le message en param�tre et l'envoie sur tout les clients et l'ajoute aussi sur l'instance serveur
	 */	
	public void envoieMessageATous(String message) {
		lesJoueurs.forEach((connection, joueur) -> {
			envoi(connection, message);
		});
		controle.evenementJeuClient("ajout message", message);
	}
	/**
	 * Fonction qui ajoute un joueur en valeur et sa connection en cl� dans la hashmap lesJoueurs
	 * @param connection de type Connection
	 * Re�oit la connection en param�tre et l'ajoute en cr�ant l'instance joueur li�e en l'ajoutant dans la hashmap
	 */
	@Override
	public void connexion(Connection connection) {
		lesJoueurs.putIfAbsent(connection, new Joueur());
	}
	/**
	 * Fonction qui effectue l'ordre re�u par le client
	 * @param connection de type Connection
	 * @param info de type Object
	 * Re�oit l'odre en chaine de charac�re effectue l'action li�e
	 */
	@Override
	public void reception(Connection connection, Object info) {
		if(info!=null) {
			String[] message = ((String)info).split(Global.splitSymbol);
			switch(message[0]){
			case("pseudo"):
				controle.evenementJeuServeur("ajout panel murs", connection);
				lesJoueurs.get(connection).initPerso(message[1], Integer.parseInt(message[2]),new ArrayList<Joueur>(this.lesJoueurs.values()),this.lesMurs, this);
				envoieMessageATous("*** "+lesJoueurs.get(connection).getpseudo()+" vient de se connecter ***");
				break;
			case("message"):
				envoieMessageATous(lesJoueurs.get(connection).getpseudo()+" > "+message[1]);
				break;
			case("deplacement"):
				lesJoueurs.get(connection).action(Integer.parseInt(message[1]),new ArrayList<Joueur>(this.lesJoueurs.values()),this.lesMurs);
				break;
			default:
				break;
		}
		}
	}
	/**
	 * Fonction qui g�re la d�connection/fermeture d'un joueur/client
	 * @param connection de type connection
	 * Re�oit la connection en param�te et enl�ve le joueur li�e du jeu
	 */
	@Override
	public void deconnexion(Connection connection) {
		lesJoueurs.get(connection).departJoueur();
		this.lesJoueurs.get(connection).departJoueur();
		this.lesJoueurs.remove(connection);
		this.lesJoueurs.remove(connection);
		envoieJeuATous();
	}
	/**
	 * Fonction d�connecte tout les clients
	 */
	public void deconnectAll() {
		lesJoueurs.forEach((connection, joueur) -> {
			envoi(connection, true);
		});
	}

	/**
	 * Envoi d'une information vers tous les clients
	 * @param connection de type Connection
	 * @param objet de type Object
	 * fais appel plusieurs fois � l'envoi de la classe Jeu
	 */
	public void envoi(Connection connection, Object objet) {
		super.envoi(connection, objet);
	}

	/**
	 * G�n�ration des murs
	 */
	public void constructionMurs() {
		for(int k= 0;k<20; k++) {
			lesMurs.add(new Mur());
			controle.evenementJeuServeur("ajout mur", lesMurs.get(k).getjLabel());
		}
		
	}
	/**
	 * Fonction qui renvoie les valeurs de la Hashmap de lesJoueurs sous forme d'ArrayList
	 */
	public ArrayList<Joueur> getJoueurs() {
		return new ArrayList<Joueur>(this.lesJoueurs.values());
	}
	
}