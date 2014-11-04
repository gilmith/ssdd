package util.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServicioGestorInterface extends Remote {
	
	public final static String PUERTO="7777";
	public final static String NAME="interfaz";
	
	public String getContactos(String nick) throws RemoteException;
	
	public String getMensajesOffline(String nick) throws RemoteException;
			
	public void desconectar(String nickName) throws RemoteException;

	public void send(Trino mensaje) throws RemoteException;

	public List<String> buscarUsuario(String nickName) throws RemoteException;
	
	public List<Trino> receive(String nickName) throws RemoteException;
	
	public void addUsuario(String nickName, String nuevo) throws RemoteException;

	public List<String> getContactos1(String nickName2) throws RemoteException;

	public void send(Trino mensaje, List<String> listaContactos) throws RemoteException;


}
