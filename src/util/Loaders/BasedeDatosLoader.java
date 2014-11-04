/**
 * 
 */
package util.Loaders;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import util.Interfaces.ServicioDatosInterface;

/**
 * Clase para obtener la conexion con la base de datos. Solo contiene un metodo estatico para tal efecto.
 * 
 * @author Jacobo Geada Ansino
 *
 */
public class BasedeDatosLoader {

	public static ServicioDatosInterface getBBDD(String ip){
		try {
			ServicioDatosInterface bbdd =  (ServicioDatosInterface) Naming.lookup("rmi://" + ip + ":" + ServicioDatosInterface.PUERTO +"/" + ServicioDatosInterface.NAME);
			System.out.println(bbdd.toString());
			return bbdd;
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}
	
}
