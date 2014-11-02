/**
 * 
 */
package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import util.LogManager.LogObj;


/**
 * añado los elementos del panel, se llaman por mvc modelo vista controlador
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
	
	
	public PanelGeneral(String titulo){
		setLayout(new GridLayout(3,3));
		logUsuario = new LogObj("/tmp/usuario.log");
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


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok){
			logUsuario.write("el usuario " + usuario.getText() + "intenta entrar al server");
		} else if (e.getSource() == nuevo_usuario){
			logUsuario.write("alta de nuevo usuario");
			Alta alta = new Alta("ventana de alta de nuevo usuario");
		}
		
	}

}
