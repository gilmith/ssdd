package util.Loaders;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import util.Interfaces.ServicioAutenticacionInterface;
import util.Interfaces.ServicioGestorInterface;

public class serverLoader {

	/**
	 * Clase para la obtencion de las conexiones con el servidor
	 * @author Jacobo Geada Ansino
	 */
	private static final String CODEBASE="java.rmi.server.codebase";
	
	
	public serverLoader(){
		
	}
	
	public static void setCodeBase(Class<?> c){
		System.setProperty(CODEBASE, c.getProtectionDomain().getCodeSource().getLocation().toString());
	}
	
	public static ServicioAutenticacionInterface getAutentificacion(String ip) throws RemoteException{
		try {
			return (ServicioAutenticacionInterface) Naming.lookup("rmi://" + ip +":" + ServicioAutenticacionInterface.PUERTO +"/" + ServicioAutenticacionInterface.NAME);
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		};
		return null;
	}
	
	
	public static ServicioGestorInterface getGestor(String ip) throws RemoteException{
		try{
			return (ServicioGestorInterface) Naming.lookup("rmi://"+ip + ":" + ServicioGestorInterface.PUERTO + "/" + ServicioGestorInterface.NAME);
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		};
		return null;
	}

	
}
