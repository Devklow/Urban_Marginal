package controleur;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

/*
 * Interface Globale, implémenté par Controle
 * Permet de stocker toutes les constantes
 * 
 */
public interface Global {
	
	public static String pseudoText = "pseudo";
	public static String messageText = "message";
	public static String splitSymbol = "~";
	public static String persoEmplacement = "personnages/perso";
	public static String wallEmplacement ="murs/mur.gif";
	public static String bouleEmplacement ="boules/boule.gif";
	public static String fondArene = "fonds/fondarene.jpg";
	public static String fondChoix = "fonds/fondchoix.jpg";
	public static String persoStyle = "marche1d0.gif";
	public static String erreurMessagePseudo = "La saisie du pseudo est obligatoire";
	public static String startLbl = "Start a server : ";
	public static String startButtonText = "Start";
	public static String connectLbl = "Connect to an existing server :";
	public static String ipLbl = "IP Adress :";
	public static List<Integer> lstKey = Arrays.asList(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
	public static int arenaWitdh = 655;
	public static int arenaHeight = 502;
	public static String deplacementText = "deplacement";
	public static String closeText = "fermeture";
	public static String welcomeSound = "sons/welcome.wav";
	public static String goSound = "sons/go.wav";
	public static String previousSound = "sons/precedent.wav";
	public static String nextSound = "sons/suivant.wav";
	public static String ambianceSound = "sons/ambiance.wav";
	public static List<String> lstSon = Arrays.asList("sons/fight.wav","sons/hurt.wav","sons/death.wav");
	public static int pas = 10;
}