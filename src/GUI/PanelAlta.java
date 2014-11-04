/**
 * 
 */
package GUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.Interfaces.ServicioAutenticacionInterface;
import util.Loaders.serverLoader;


/**
 * Paneles de la ventana de alta.
 * @author Jacobo Geada Ansino
 *
 */
public class PanelAlta extends JPanel implements ActionListener{
	

	private static final long serialVersionUID = -1755199291122229031L;
	private JLabel etiNombre, etiApellidos, etiMail, etiNick, etiPassword;
	private JTextField textNombre, textApellidos, textMail, textNick;
	private JPasswordField password;
	private JButton btnAlta;
	private JPanel pnlNombre, pnlApellidos, pnlMail, pnlPassword, pnlNick;
	private AltaObj datosAlta;
	private String ip;
	
	public PanelAlta(AltaObj datosAlta2, String ip){
		this.ip = ip;
		this.setLayout(new GridLayout(3,2));
		initComponentes();
		initPaneles();
		this.add(pnlNombre);
		this.add(pnlApellidos);
		this.add(pnlMail);
		this.add(pnlPassword);
		this.add(pnlNick);
		this.add(btnAlta);
	}
	
	public PanelAlta(AltaObj datosAlta) {
		this.setLayout(new GridLayout(3,2));
		this.datosAlta = datosAlta;
		initComponentes();
		initPaneles();
		this.add(pnlNombre);
		this.add(pnlApellidos);
		this.add(pnlMail);
		this.add(pnlPassword);
		this.add(pnlNick);
		this.add(btnAlta);
	}

	private void initComponentes(){
		etiNombre = new JLabel("nombre de usuario");
		etiApellidos = new JLabel("apellidos");
		etiMail = new JLabel("e-mail");
		etiPassword = new JLabel("password");
		etiNick = new JLabel("nickname");
		textNombre = new JTextField(15);
		textApellidos = new JTextField(15);
		textMail = new JTextField(15);
		textNick = new JTextField(10);
		password = new JPasswordField(10);
		btnAlta = new JButton("Alta");
		btnAlta.addActionListener(this);
	}
	
	private void initPaneles(){
		pnlNombre = new JPanel();
		pnlNombre.setLayout(new FlowLayout());
		pnlNombre.add(etiNombre);
		pnlNombre.add(textNombre);
		pnlApellidos = new JPanel();
		pnlApellidos.setLayout(new FlowLayout());
		pnlApellidos.add(etiApellidos);
		pnlApellidos.add(textApellidos);
		pnlMail = new JPanel();
		pnlMail.setLayout(new FlowLayout());
		pnlMail.add(etiMail);
		pnlMail.add(textMail);
		pnlPassword = new JPanel();
		pnlPassword.setLayout(new FlowLayout());
		pnlPassword.add(etiPassword);
		pnlPassword.add(password);
		pnlNick = new JPanel();
		pnlNick.setLayout(new FlowLayout());
		pnlNick.add(etiNick);
		pnlNick.add(textNick);
		
	}
	
	/**
	 * Patron controlador GRASP mediante actionPerformed para el uso de los botones
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAlta){
			System.out.println("rellena el objeto alta");
			datosAlta = new AltaObj(); 
			datosAlta.setNombre(textNombre.getText());
			datosAlta.setApellido(textApellidos.getText());
			datosAlta.setNick(textNick.getText());
			datosAlta.setMail(textMail.getText());
			datosAlta.setPasswd(String.valueOf(password.getPassword()));
			System.out.println("aqui hace el rmi");
			try {
				ServicioAutenticacionInterface auth1 = serverLoader.getAutentificacion(ip);
				if(auth1.makeUser(datosAlta)){
					System.out.println("usuario registrado");
					JOptionPane.showMessageDialog(getParent(), "USUARIO YA REGISTRADO", "USUARIO REGISTRADO", 
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(getParent(), "USUARIO REGISTRADO CON EXITO", 
							"ALTA OK", JOptionPane.PLAIN_MESSAGE);
				
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
	

}
