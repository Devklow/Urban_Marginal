package vue;
import controleur.Controle;
import controleur.Global;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class EntreeJeu extends JFrame {

	private JPanel contentPane;
	private JTextField txtIP;
	private Controle controle;
	private Arene frmArene ;
	private ChoixJoueur frmChoixJoueur ;
	
	/**
	 * Methode permettant de demarrer le serveur
	 */
	private void start(){
		this.controle.evenementEntreeJeu("serveur");

	}
	/**
	 * Methode permettant de se connecter à un Serveur
	 */
	private void connect(){
		this.controle.evenementEntreeJeu(this.txtIP.getText());
	}


	/**
	 * Créer la fenêtre
	 */
	
	public EntreeJeu(Controle controle) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 329, 189);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(Global.startLbl);
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 173, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblConnectToAn = new JLabel(Global.connectLbl);
		lblConnectToAn.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		lblConnectToAn.setBounds(10, 50, 253, 28);
		contentPane.add(lblConnectToAn);
		
		JLabel lblNewLabel_1 = new JLabel(Global.ipLbl);
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 89, 73, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnStart = new JButton(Global.startButtonText);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		btnStart.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		btnStart.setBounds(220, 15, 89, 23);
		contentPane.add(btnStart);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		btnConnect.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		btnConnect.setBounds(220, 85, 89, 23);
		contentPane.add(btnConnect);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		btnExit.setBounds(220, 119, 89, 23);
		contentPane.add(btnExit);
		
		txtIP = new JTextField();
		txtIP.setText("127.0.0.1");
		txtIP.setToolTipText("");
		txtIP.setBounds(81, 87, 131, 20);
		contentPane.add(txtIP);
		txtIP.setColumns(10);
		
		this.controle = controle;
	}
}
