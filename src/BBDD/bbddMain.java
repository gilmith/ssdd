/**
 * 
 */
package BBDD;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import util.Interfaces.ServicioDatosInterface;

/**
 * Arrancador de la base de datos
 * @author Jacobo Geada Ansino
 *
 */
public class bbddMain {

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException {
		String ip;
		if (args.length == 0){
			ip = "127.0.0.1";
		} else {
			ip = args[0];
		}
		BasedeDatos datos;
		try {
			final Registry reg2 = LocateRegistry.createRegistry(9999);
			datos = new BasedeDatos(ip);
			Naming.rebind("rmi://" + ip  + ":" + ServicioDatosInterface.PUERTO + "/" + ServicioDatosInterface.NAME, datos);
			System.setProperty("java.rmi.server.hostname", ip);
			new Thread(datos).start();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
