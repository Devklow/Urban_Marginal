package modele;
import java.util.ArrayList;
import java.util.HashMap;
import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté serveur
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
	 * Set de connection déjà établies
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
	 * Fonction qui envoie l'odre du son a jouer dans l'arène
	 * @son de type Entier
	 * Reçoit un int en paramètre correspondant a un son enregistré dans la liste des sons
	 */
	public void envoieSonATous(Integer son) {
			lesJoueurs.forEach((connection, joueur) -> {
				envoi(connection, son);
			});
		}
	/**
	 * Fonction qui envoie un message sur tout les clients
	 * @param message de type chaine de texte
	 * Reçoit le message en paramètre et l'envoie sur tout les clients et l'ajoute aussi sur l'instance serveur
	 */	
	public void envoieMessageATous(String message) {
		lesJoueurs.forEach((connection, joueur) -> {
			envoi(connection, message);
		});
		controle.evenementJeuClient("ajout message", message);
	}
	/**
	 * Fonction qui ajoute un joueur en valeur et sa connection en clé dans la hashmap lesJoueurs
	 * @param connection de type Connection
	 * Reçoit la connection en paramètre et l'ajoute en créant l'instance joueur liée en l'ajoutant dans la hashmap
	 */
	@Override
	public void connexion(Connection connection) {
		lesJoueurs.putIfAbsent(connection, new Joueur());
	}
	/**
	 * Fonction qui effectue l'ordre reçu par le client
	 * @param connection de type Connection
	 * @param info de type Object
	 * Reçoit l'odre en chaine de characère effectue l'action liée
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
	 * Fonction qui gère la déconnection/fermeture d'un joueur/client
	 * @param connection de type connection
	 * Reçoit la connection en paramète et enlève le joueur liée du jeu
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
	 * Fonction déconnecte tout les clients
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
	 * fais appel plusieurs fois à l'envoi de la classe Jeu
	 */
	public void envoi(Connection connection, Object objet) {
		super.envoi(connection, objet);
	}

	/**
	 * Génération des murs
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