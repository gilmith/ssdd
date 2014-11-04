package util.Interfaces;

/**
 * Interfaz para el uso de la base de  datos proporciona los metodos para acceder a los datos 
 * almacenados en ella
 * @author Jacobo Geada Ansino
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import GUI.AltaObj;


public interface ServicioDatosInterface extends Remote {
	
	public static final String PUERTO="9999";
	public static final String NAME="bbdd";
			
	public boolean buscarUsuarioConectado(String nickName) throws RemoteException; 
	
	public List<String> buscarUsuario(String nickName) throws RemoteException;

	public boolean comprobarDisponibilidadUsuario(String nickName) throws RemoteException;
	
	public void crearUsuario(AltaObj usuario) throws RemoteException;
	
	public boolean comprobarLogin(String nick, String password) throws RemoteException;
	
	public void desconexion(String nickName) throws RemoteException;

	public List<String> getMensajesOffline(String nickName) throws RemoteException;

	public List<String> loadContactos(String nickName) throws RemoteException;
	
	void setMensajeOffline(String nickName, String texto) throws RemoteException;
	
	public void addUsuario(String nickName, String nuevo) throws RemoteException;
	
	void almacenTrinos(Trino mensaje) throws RemoteException;

	public void setMensajeOffline(String contacto, Trino mensaje) throws RemoteException;
	
	public Object[] getUsuariosConectados() throws RemoteException;
	
}
