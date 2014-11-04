package GUI;

/**
 * Ventana de login principal del cliente
 * @author Jacobo Geada Ansino
 */

import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import util.Interfaces.ServicioAutenticacionInterface;
import util.Loaders.serverLoader;


public class Usuario extends JFrame {

	private static final long serialVersionUID = -3742044401001510307L;
	private static String ip;
	/**
	 * Lanzador de la aplicacion, para ello tiene que comprobar si el servidor esta arrancado.
	 */
	public static void main(final String[] args) {
		if( args.length == 0){
			ip = "127.0.0.1";
		} else {
			ip = args[0];
		}
		System.setProperty("java.server.rmi.hostname", ip);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setSecurity();
					System.out.println("Comprueba si el servidor esta arrancado");
					ServicioAutenticacionInterface auth = serverLoader.getAutentificacion(ip);
					if (auth.conectaServidor()){
						System.out.println("servidor arrancado");
					}
					Usuario frame = new Usuario();
					frame.setVisible(true);
				} catch (RemoteException e) {
					System.out.println("servidor no arrancado, RemoteException en el servicio de nombres");
					System.exit(-1);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor de la clase. 
	 * Crea el frame que contendra los elemento en JPanel. Dentro del JPanel va llamando
	 * A los distintos elementos 
	 */
	public Usuario() {
		setTitle("Trinos by Jacobo Geada");
		setBounds(300, 300, 300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new PanelGeneral("titulo",ip));
	}


	/**
	 * Setea la no politica de seguridad de puertos
	 */
	
	private static void setSecurity(){
		if(System.getSecurityManager() == null){
			System.setProperty("java.security.policy", "rmi.policy");
		}
	}

}
