package util.Interfaces;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;

public class Trino implements Serializable  {



	private static final long serialVersionUID = 12817698848882501L;
	private String emisor, receptor, texto;
	private SimpleDateFormat hora = 
			new SimpleDateFormat("dd/MM/yyyy ' a las ' hh:mm:ss");
	private String horas;
	
	
	public Trino() throws RemoteException {
	
			horas = hora.format(System.currentTimeMillis());

	}

	/**
	 * 
	 */

	public void leido(String acuse) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public String getEmisor() throws RemoteException {
		return emisor;
	}

	public String getTexto() throws RemoteException {
		return texto;
	}

	public void setEmisor(String nickName) throws RemoteException {
			this.emisor = nickName;
	}

	public void setTexto(String texto) throws RemoteException {
		this.texto = texto;
		
	}
	
	/**
	 * pendiente un mensaje multidifusion
	 * @return
	 * @throws RemoteException
	 */

	public String getReceptor() throws RemoteException {
		/**
		 * analiza el texto buscando un #
		 */
		String receptor = null;
		String[] analisis = texto.split(" ");
		for(String palabra : analisis){
			if(palabra.endsWith("#\n")){
				receptor = palabra.substring(0, palabra.length() -2);
			} else if (palabra.endsWith("#")){
				receptor = palabra.substring(0, palabra.length() -1);
			}
		} 
		return receptor;
	}

	public String getHora() {
		return horas;
	}

	public void setHora(SimpleDateFormat hora) {
		this.hora = hora;
	}

	
	@Override
	public String toString() {
		return "\n Mensaje [emisor=" + emisor + ", receptor=" + receptor
				+ ", texto=" + texto + ", horas=" + horas + "]";
	}

}
