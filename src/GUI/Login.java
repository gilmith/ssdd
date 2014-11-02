package GUI;

/**
 * Ventana de login principal del cliente
 * Jacobo Geada Ansino
 */

import java.awt.EventQueue;
import javax.swing.JFrame;


public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3742044401001510307L;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea el frame que contendra los elemento en JPanel.
	 */
	public Login() {
		setTitle("Trinos by Jacobo Geada");
		setBounds(300, 300, 300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new PanelGeneral("titulo"));
	}

}
