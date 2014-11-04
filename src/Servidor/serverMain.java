package Servidor;

/**
 * Arrancador del servidor de de autentificacion y de gestion de mensajes
 * @author Jacobo Geada Ansino
 */

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import util.Interfaces.ServicioAutenticacionInterface;
import util.Interfaces.ServicioDatosInterface;
import util.Interfaces.ServicioGestorInterface;
import util.Loaders.BasedeDatosLoader;
import util.Loaders.serverLoader;
import GUI.Usuario;

public class serverMain {
	
	public static void main(String[] args) throws IOException{
		String ip1, ip2;
		setSecurity();
		if (args.length == 0){
			ip1 = "127.0.0.1";
			ip2 = "127.0.0.1";
		} else {
			ip1 = args[0];
			ip2 = args[1];
		}
		ServicioDatosInterface check = BasedeDatosLoader.getBBDD(ip1);
		System.out.println(check.toString());
		if(check == null){
			System.out.println("base de datos no arrancada");
			System.exit(-1);
		} else {
			try {
			// 0  es bbdd 1 es servidor
				final Registry reg1 = LocateRegistry.createRegistry(8888); 
				final Registry reg0 = LocateRegistry.createRegistry(7777);
				ServicioAutenticacionImpl auth = new ServicioAutenticacionImpl(ip2);
				Naming.rebind("rmi://" + ip2  + ":" + ServicioAutenticacionInterface.PUERTO + "/" + ServicioAutenticacionInterface.NAME, auth);
				ImplGestorMensajes gestor = new ImplGestorMensajes(ip2);
				Naming.rebind("rmi://" + ip2  + ":" + ServicioGestorInterface.PUERTO + "/" + ServicioGestorInterface.NAME, gestor);
				new Thread(auth).start();
				new Thread(gestor).start();
				reg1.rebind("autentificacion", auth);
				reg0.rebind("interfaz", gestor);
				ServidorGUI sgui = new ServidorGUI(gestor, ip1, ip2);
				sgui.setVisible(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void setSecurity(){
		if(System.getSecurityManager() == null){
			System.setProperty("java.security.policy", "rmi.policy");
		}
	}

}
