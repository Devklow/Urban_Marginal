package vue;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Arene extends JFrame {
	
	private Controle controle;
	private JPanel contentPane;
	private JPanel jpnMurs;
	private JTextField textField;
	private JPanel jpnJeu;
	private JTextArea msgText;
	
	
	/**
	 * Methode permettant d'ajouter les murs
	 * @param mur de type Object mais transtypé en type Jpanel
	 */
	public void ajoutMurs(Object mur) {
		jpnMurs.add((JLabel)mur);
		jpnMurs.repaint();
	}
	/**
	 * Methode permettant d'ajouer un label
	 * @param label de type Jlabel
	 */
	public void ajoutJLabelJeu(JLabel label) {
		jpnJeu.add(label);
		jpnJeu.repaint();
	}
	/**
	 * Methode permettant de supprimer un label
	 * @param label de type Jlabel
	 */
	public void suppLabelJeu(JLabel label) {
		jpnJeu.remove(label);
		jpnJeu.repaint();
	}
	/**
	 * Getter sur le Jpanel des murs
	 * @return jpnMurs de type Jpanel
	 */
	public JPanel getMurs() {
		return jpnMurs;
	}
	/**
	 * Getter sur le Jpanel du Jeu (Joueurs, pseudo etc...)
	 * @return jpnJeu de type Jpanel
	 */
	public JPanel getJeu() {
		return jpnJeu;
	}
	/**
	 * Methode permettant de placer les Murs du côté Client
	 * @param panel de type Jpanel
	 */
	public void setMurs(JPanel panel) {
		this.jpnMurs.add(panel);
		this.jpnMurs.repaint();
	}
	
	/**
	 * Methode permettant d'actualiser le jeu par rapport au serveur
	 * @param panel de type Jpanel
	 */
	public void setJeu(JPanel panel) {
		this.jpnJeu.removeAll();
		this.jpnJeu.revalidate();
		this.jpnJeu.add(panel);
		this.jpnJeu.repaint();
		this.contentPane.requestFocus();
	}
	
	/**
	 * Methode permettant d'ajouter un message au tchat
	 * @param message de type chaine de texte
	 */
	public void setMessages(String message) {
		msgText.setText(msgText.getText()+message+"\r\n");
		this.msgText.setCaretPosition(this.msgText.getDocument().getLength());
	}
	/**
	 * Getter sur text les messages
	 */
	public String getMessages() {
		return msgText.getText();
	}
	
	/**
	 * Methode permettant de fermer l'application
	 */
	public void close() {
		System.exit(0);
	}
	
	/**
	 * Créer la fenêtre
	 * @param controleur de type Controle
	 */
	public Arene(Controle controleur) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controle.evenementQuitter();
			}
		});
		setResizable(false);
		setAutoRequestFocus(false);
		this.controle = controleur;
		int width = 815;
		int height = 810;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, height);
		contentPane = new JPanel();
		new Son(getClass().getClassLoader().getResource(Global.ambianceSound)).play();
		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(Global.lstKey.contains(e.getKeyCode())) {
					controle.evenementArene(e.getKeyCode());
				}
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setFocusable(true);
		URL bg = getClass().getClassLoader().getResource(Global.fondArene);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					if(!textField.getText().isBlank()) {
						System.out.println("Envoie d'un message");
						controle.evenementArene((String)textField.getText());
						textField.setText("");
						contentPane.requestFocus();
					}
				}
			}
		});
		textField.setBounds(0, 603, width-15, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 624, width-15, 135);
		contentPane.add(scrollPane);
		
		this.msgText = new JTextArea();
		msgText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				contentPane.requestFocus();
			}
		});
		msgText.setLineWrap(true);
		scrollPane.setViewportView(msgText);
		msgText.setEditable(false);
		
		jpnMurs = new JPanel();
		jpnMurs.setBackground(new Color(255, 255, 255));
		jpnMurs.setBounds(0, 0, 799, 602);
		jpnMurs.setOpaque(false);
		jpnMurs.setBorder(null);
		jpnMurs.setLayout(null);
				
		
		jpnJeu = new JPanel();
		jpnJeu.setBounds(0, 0, 799, 602);
		jpnJeu.setBackground(new Color(255, 255, 255));
		jpnJeu.setOpaque(false);
		jpnJeu.setBorder(null);
		jpnJeu.setLayout(null);
		contentPane.add(jpnMurs);
		contentPane.add(jpnJeu);
		
		
		JLabel lblBackGround = new JLabel("");
		lblBackGround.setBounds(0, 0, width-15, height-200);
		lblBackGround.setIcon(new ImageIcon(bg));
		contentPane.add(lblBackGround);
		contentPane.requestFocus();
	}
}
