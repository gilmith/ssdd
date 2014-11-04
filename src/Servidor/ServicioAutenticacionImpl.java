package Servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import util.Interfaces.ServicioAutenticacionInterface;
import util.Interfaces.ServicioDatosInterface;
import util.Loaders.BasedeDatosLoader;
import util.LogManager.LogObj;
import GUI.AltaObj;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements
ServicioAutenticacionInterface, Runnable {

	private static final long serialVersionUID = 4888366969921550807L;
	private LogObj logServidor;
	private String ip;
	private static ServicioDatosInterface bbdd;
	/**
	 * Constructor para arrancar como un hilo el servicio de autentificacion.
	 * @param ip 
	 * @throws RemoteException
	 */
	
	public ServicioAutenticacionImpl(String ip) throws RemoteException {
		this.ip = ip;
		bbdd = BasedeDatosLoader.getBBDD(ip);
		setSecurity();
	}

	/**
	 * Metodo para confirmar que el servidor de Autentificacion esta corriendo.
	 * @return boolean, true si esta arrancado.
	 */
	
	@Override
	public boolean conectaServidor() throws RemoteException {
		logServidor.write("respuesta de servidor conectado");
		return true;
	}
	/**
	 * Consulta la base de datos para comprobar si el usuario-password esta o no registrado en la 
	 * base de datos.
	 * Realiza una llamada a un objeto de base de datos para poder realizar la consulta.
	 * @return el contenido de a consulta de la base de datos, true o false
	 */

	@Override
	public boolean autentificacion(String nick, String password) throws RemoteException {

			logServidor.write("llamada al objeto remoto de base de datos, el ServicioAutentificador es cliente del ServicioDatos");
			//ServicioDatosInterface list = BasedeDatosLoader.getBBDD(ip);
			logServidor.write("Comprobacion en base de datos");
			return bbdd.comprobarLogin(nick, password);

	}
	
	/**
	 * Metodo para crear un nuevo usuario en la base de datos. Le pase un objeto Serializable AltaObj 
	 * al servicio de la base de datos. Aqui el Servicio de autentificacion solo es una pasarela al
	 * Servicio de la base de datos 
	 * @return devuelve el resultado del alta del objeto
	 */

	@Override
	public boolean makeUser(AltaObj nuevoUsuario) throws RemoteException {
	
			logServidor.write("llamada al objeto remoto de base de datos");
			ServicioDatosInterface list = BasedeDatosLoader.getBBDD(ip);
			if(list.comprobarDisponibilidadUsuario(nuevoUsuario.getNick())){
				return true;
			} else {
				logServidor.write("paso a los metodo de base de datos");
				list.crearUsuario(nuevoUsuario);
				return false;
			}
	
	}

	/**
	 * Overrride del metodo run de la clase Runnable para poder arrancar el servicio como un hilo particular 
	 * dentro del programa. Asi evito bloqueos del programa completo. 
	 * Arranca el Logger y pinta lineas en el terminal del Servicio de Autentificacion. 
	 */
	
	@Override
	public void run() {
		System.out.println("iniciando el servidor de autentificacion");
		logServidor = new LogObj("autentificador.log");
		logServidor.write("servidor de autentificacion iniciando");
	}
	
	private static void setSecurity(){
		if(System.getSecurityManager() == null){
			System.setProperty("java.security.policy", "rmi.policy");
		}
	}

}
