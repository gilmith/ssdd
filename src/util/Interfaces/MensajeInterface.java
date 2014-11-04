package util.Interfaces;

/**
 * La clase de Mensaje no tiene los metodos de envio y recepcion, tiene lo metodos para añadir
 * texto, usuario etc etc.
 * 
 * 
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

public interface MensajeInterface extends Remote{
	
	
	public void leido(String acuse) throws RemoteException;
	
	public String getEmisor() throws RemoteException;
	
	public String getTexto() throws RemoteException;
	
	public void setEmisor(String nickName) throws RemoteException;
	
	public void setTexto(String texto) throws RemoteException;
	
	public String getReceptor() throws RemoteException;
	
	public String getHora() throws RemoteException;

	public void setHora(SimpleDateFormat hora) throws RemoteException;
	
	public boolean isCallback() throws RemoteException;
	
	public String toString();
}
