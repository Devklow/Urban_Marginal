package modele;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JLabel;

import controleur.Global;
/**
 * Informations communes à tous les objets (joueurs, murs, boules)
 * permet de mémoriser la position de l'objet et de gérer les  collisions
 *
 */
public abstract class Objet {

	/**
	 * position X de l'objet
	 */
	protected Integer posX ;
	/**
	 * position Y de l'objet
	 */
	protected Integer posY ;
	protected JLabel jLabel;
	
	public JLabel getjLabel() {
		return jLabel;
	}
	public Integer getPosX() {
		return posX;
	}
	public Integer getPosY() {
		return posY;
	}
	protected Boolean inArena() {
		if(this.jLabel!=null) {
			if(this.jLabel.getBounds().x<Global.arenaWitdh+84 && this.jLabel.getBounds().x>22 && this.jLabel.getBounds().y<Global.arenaHeight+44 && this.jLabel.getBounds().y>22)  {
				return true;
			}
		}
		return false;
	}
	/**
	 * Contrôle si un Objet touche un autre Objet d'une collection donnée
	 * @return true si deux Objets se touchent
	 */
	public Objet toucheCollectionObjets(Collection<Objet> laCollection) {
		if(!laCollection.isEmpty()) {
			for(Objet objet : laCollection) {
				if(toucheObjet(objet)){
					return objet;
				}
			}
		}
		return null;
	}	
	/**
	 * contrôle si l'objet actuel touche l'objet passé en paramètre
	 * @param objet contient l'objet à contrôler
	 * @return true si les 2 objets se touchent
	 */
	public Boolean toucheObjet (Objet objet) {
		ArrayList<String> stock = new ArrayList<String>();
		if(this.jLabel !=null && objet.jLabel !=null) {
			int X1 = this.jLabel.getBounds().x+((int)this.jLabel.getBounds().width/2);
			int X2 = objet.jLabel.getBounds().x+((int)objet.jLabel.getBounds().width/2);
			int Y1 = this.jLabel.getBounds().y+((int)this.jLabel.getBounds().height/2);
			int Y2 = objet.jLabel.getBounds().y+((int)objet.jLabel.getBounds().height/2);
			//Si X1<X2 alors Partie Droite de this a check
			if(X1 <= X2) {
				//Si Y1<Y2 alors Partie Superieur Droite de this a check
				if(Y1 >= Y2) {
					//Stockage de tout les points de la partie supérieur droite de this
					for(int i=0;i<=this.jLabel.getBounds().width/2;i++){
						for(int j=0;j<=this.jLabel.getBounds().height/2;j++) {
							stock.add((X1+i)+","+(Y1-j));
						}
					}
					//Verification de tout les point de la partie inférieur gauche de objet par rapport au stock
					for(int i=0;i<=objet.jLabel.getBounds().width/2;i++){
						for(int j=0;j<=objet.jLabel.getBounds().height/2;j++) {
							if(stock.contains((X2-i)+","+(Y2+j))) {
								System.out.println("touche");
								return true;
							}
						}
					}
				}
				//Sinon Partie inferieur Droite de this a stocker
				else {
					for(int i=0;i<=this.jLabel.getBounds().width/2;i++){
						for(int j=0;j<=this.jLabel.getBounds().height/2;j++) {
							stock.add((X1+i)+","+(Y1+j));
						}
					}
					for(int i=0;i<=objet.jLabel.getBounds().width/2;i++){
						for(int j=0;j<=objet.jLabel.getBounds().height/2;j++) {
							if(stock.contains((X2-i)+","+(Y2-j))) {
								System.out.println("touche");
								return true;
							}
						}
					}
				}
			}
			//Si non partie gauche à check
			else {
				if(Y1 >= Y2) {
					//Stockage de tout les points de la partie supérieur gauche de this
					for(int i=0;i<=this.jLabel.getBounds().width/2;i++){
						for(int j=0;j<=this.jLabel.getBounds().height/2;j++) {
							stock.add((X1-i)+","+(Y1-j));
						}
					}
					//Verification de tout les point de la partie inférieur gauche de objet par rapport au stock
					for(int i=0;i<=objet.jLabel.getBounds().width/2;i++){
						for(int j=0;j<=objet.jLabel.getBounds().height/2;j++) {
							if(stock.contains((X2+i)+","+(Y2+j))) {
								System.out.println("touche");
								return true;
							}
						}
					}
				}
				//Sinon Partie inferieur gauche de this a stocker
				else {
					for(int i=0;i<=this.jLabel.getBounds().width/2;i++){
						for(int j=0;j<=this.jLabel.getBounds().height/2;j++) {
							stock.add((X1-i)+","+(Y1+j));
						}
					}
					for(int i=0;i<=objet.jLabel.getBounds().width/2;i++){
						for(int j=0;j<=objet.jLabel.getBounds().height/2;j++) {
							if(stock.contains((X2+i)+","+(Y2-j))) {
								System.out.println("touche");
								return true;
							}
						}
					}
				}
			}
			return false;	
		}
		return false;
	}
	
}
