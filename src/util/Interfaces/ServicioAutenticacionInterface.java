package util.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import GUI.AltaObj;

public interface ServicioAutenticacionInterface extends Remote {
	
	public final static String PUERTO="8888";
	public final static String NAME="autentificacion";
	
	/**
	 * interfaz que comprueba si el servidor responde
	 * @return true si responde sino devuelve un false
	 * @throws RemoteException
	 */
	public boolean conectaServidor() throws RemoteException;
	
	/**
	 * conecta con el servidor y con el servidor de autentificacion, devolvera 
	 * un inicio de sesion correcto o incorrecto
	 * @param nick
	 * @param password
	 * @return resultado de la autentificacion, pero trabaja sobre la base de datos
	 * @throws RemoteException
	 */
	
	public boolean autentificacion(String nick, String password) throws RemoteException;

	
	public boolean makeUser(AltaObj nuevoUsuario) throws RemoteException;

	
	
}
