package vue;
import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.net.URL;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChoixJoueur extends JFrame {

	private JPanel contentPane;
	private JTextField txtPseudo;
	private JLabel lblCharacter;
	private JButton btnGo;
	private JButton btnPrevious;
	private int nbChar = 1;
	private Controle controle;
	
	private void start(){
		controle.evenementChoixJoueur(txtPseudo.getText(), nbChar);
	}
	
	private void updateChar() {
		URL chara = getClass().getClassLoader().getResource(Global.persoEmplacement+nbChar+Global.persoStyle);
		lblCharacter.setIcon(new ImageIcon(chara));		
	}
	
	private void previousCharacter(){
		nbChar-=1;
		if(nbChar==0) {
			nbChar=3;
		}
		updateChar();
	}
	private void nextCharacter(){
		nbChar+=1;
		if(nbChar==4){
			nbChar=1;
		}
		updateChar();
	}
	public void ErrMsg() {
		JOptionPane.showMessageDialog(null, Global.erreurMessagePseudo);
		txtPseudo.requestFocus();
	}
	/**
	 * Create the frame.
	 */
	public ChoixJoueur(Controle controle) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 396);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPseudo = new JTextField();
		txtPseudo.setBounds(201, 285, 123, 22);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);
		
		lblCharacter = new JLabel("");
		lblCharacter.setHorizontalAlignment(SwingConstants.CENTER);
		lblCharacter.setBounds(201, 156, 122, 118);
		updateChar();
		contentPane.add(lblCharacter);
		URL bg = getClass().getClassLoader().getResource(Global.fondChoix);
		
		btnGo = new JButton("");
		new Son(getClass().getClassLoader().getResource(Global.welcomeSound)).play();
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Son(getClass().getClassLoader().getResource(Global.goSound)).play();
				start();
			}
		});
		btnGo.setBounds(366, 237, 69, 67);
		btnGo.setOpaque(false);
		btnGo.setFocusPainted(false);
		btnGo.setBorderPainted(false);
		btnGo.setContentAreaFilled(false);
		btnGo.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		contentPane.add(btnGo);
		
		JButton btnNext = new JButton("");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Son(getClass().getClassLoader().getResource(Global.nextSound)).play();
				nextCharacter();
			}
		});
		btnNext.setOpaque(false);
		btnNext.setFocusPainted(false);
		btnNext.setContentAreaFilled(false);
		btnNext.setBorderPainted(false);
		btnNext.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnNext.setBounds(361, 189, 32, 37);
		contentPane.add(btnNext);
		
		btnPrevious = new JButton("");
		btnPrevious.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Son(getClass().getClassLoader().getResource(Global.previousSound)).play();
				previousCharacter();
			}
		});
		btnPrevious.setOpaque(false);
		btnPrevious.setFocusPainted(false);
		btnPrevious.setContentAreaFilled(false);
		btnPrevious.setBorderPainted(false);
		btnPrevious.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnPrevious.setBounds(124, 189, 31, 37);
		contentPane.add(btnPrevious);
		
		JLabel lblBackGround = new JLabel("");
		lblBackGround.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackGround.setForeground(Color.WHITE);
		lblBackGround.setBounds(0, 0, 520, 357);
		lblBackGround.setIcon(new ImageIcon(bg));
		contentPane.add(lblBackGround);
		
		this.controle = controle;
	}
}
