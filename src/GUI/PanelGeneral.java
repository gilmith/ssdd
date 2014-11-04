/**
 * 
 */
package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;

import util.Interfaces.ServicioAutenticacionInterface;
import util.Loaders.serverLoader;
import util.LogManager.LogObj;


/**
 * Panel inicial de la aplicacion. 
 * @author Jacobo Geada Ansino
 *
 */
public class PanelGeneral extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6822449872949171399L;
	private JLabel etiUsuario, etiPassword;
	private JTextField usuario;
	private JPasswordField password;
	private JButton ok, nuevo_usuario;
	private LogObj logUsuario;
	private String ip;
	
	public PanelGeneral(String titulo, String ip){
		this.ip = ip;
		setLayout(new GridLayout(3,3));
		logUsuario = new LogObj("usuario.log");
		crearElementos();
	}
	


	private void crearElementos(){
		
		usuario = new JTextField(15);
		password = new JPasswordField(15);
		etiUsuario = new JLabel("Usuario");
		etiUsuario.setHorizontalAlignment(JLabel.CENTER);
		etiPassword = new JLabel("Password");
		etiPassword.setHorizontalAlignment(JLabel.CENTER);
		ok = new JButton("ok");
		ok.setHorizontalAlignment(JButton.CENTER);
		ok.addActionListener(this);
		nuevo_usuario= new JButton("alta");
		nuevo_usuario.setHorizontalAlignment(JButton.CENTER);		
		nuevo_usuario.addActionListener(this);
		this.add(etiUsuario);
		this.add(usuario);
		this.add(etiPassword);
		this.add(password);
		this.add(nuevo_usuario);
		this.add(ok);
		
	}

	/**
	 * Los botones estan controlados por actionPerformed en GRASP controlador
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok){
			logUsuario.write("el usuario " + usuario.getText() + " intenta entrar al server");
			try {
				logUsuario.write("llamada al objeto remoto de autentificacion");
				//ServicioAutenticacionInterface auth =  serverLoader.getAutentificacion(ip).autentificacion(nick, password);
				if (serverLoader.getAutentificacion(ip).autentificacion(usuario.getText(), String.valueOf(password.getPassword()))){
					logUsuario.write("acceso concedido al servidor, arranque de la ventana de mensajeria");
					VentanaAplicacion ventanaAplicacion = new VentanaAplicacion(usuario.getText(), ip);
					ventanaAplicacion.setVisible(true);
				} else {
					logUsuario.write("error en usuario/password");
					JOptionPane.showMessageDialog(getParent(), "Error usuario/password", "Error de autentificacion", JOptionPane.ERROR_MESSAGE);
				}
					
				
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
				
		} else if (e.getSource() == nuevo_usuario){
			logUsuario.write("alta de nuevo usuario");
			Alta alta = new Alta("ventana de alta de nuevo usuario", ip);
			alta.setVisible(true);
		}
		
	}

}
