package BBDD;
/**
 * Implementacion de los metodos de Base De Datos orientada a ficheros.
 * Al arrancar carga en su interior la lista de los usuarios y las passwords
 * Mantiene un registro de los usuarios activos, facilita los metodos para 
 * crear nuevos usuarios, validar las entradas.
 * 
 * Se arranca como un hilo al arrancar el servidor de Trinos. 
 * El log del hilo es BBDD.log. 
 * 
 * @author Jacobo Geada Ansino
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import util.Interfaces.Trino;
import util.Interfaces.MensajeInterface;
import util.Interfaces.ServicioDatosInterface;
import util.Loaders.FileLoader;
import util.Loaders.serverLoader;
import util.LogManager.LogObj;
import GUI.AltaObj;

public class BasedeDatos extends UnicastRemoteObject implements ServicioDatosInterface, Runnable{

	private static void setSecurity(){
		if(System.getSecurityManager() == null){
			System.setProperty("java.security.policy", "rmi.policy");
		}
	}
	private static final long serialVersionUID = 3783789071355963737L;
	private static ListaUsuarios lista;
	private static int numConectados = 0;
	private static List<String> listaConectados;
	private static Registry registro;
	private static String ip;
	private static LogObj log = new LogObj("bbdd.log");
	
	/**
	 * Constructor de la clase. 
	 * Setea el objeto serializable que es el encargado de realizar el alta de los usuarios.
	 * Tambien crea como campo el listado de los usuarios registrados en la aplicacion como un hash
	 * @param reg2 se le pasa como parametro el registro asociado para el servicio de red
	 * @throws RemoteException en el caso de no poder alocar el puerto o conflicto de red
	 */
	
	public BasedeDatos(Registry reg2) throws RemoteException {
		super();
		setSecurity();
		listaConectados = new ArrayList<String>();
		serverLoader.setCodeBase(AltaObj.class);
		lista = new ListaUsuarios();
		registro = reg2;
		log.write("base de datos arrancada");
	}
	public BasedeDatos(String ip) throws RemoteException {
		super();
		listaConectados = new ArrayList<String>();
		serverLoader.setCodeBase(AltaObj.class);
		lista = new ListaUsuarios();
		this.ip = ip;
		log.write("base de datos arrancada");
	}
	/**
	 * Añadir un usuario a la lista de contactos.
	 * @param nombre del usuario nuevo
	 */
	
	@Override
	public void addUsuario(String nickName, String nuevo) throws RemoteException {
		log.write("operacion de carga de usuario");
		CargaUsuario carga = new CargaUsuario(nickName);
		carga.addUsuario(nickName, nuevo);
		
	}

	/**
	 * metodo para escribir los mensajes en un archivo global
	 * @param Trino enviado
	 */

	@Override
	public void almacenTrinos(Trino mensaje) throws RemoteException {
		FileWriter file;
		try {
			file = new FileWriter(FileLoader.getRuta() + "bbdd.txt",true);
			PrintWriter pw = new PrintWriter(file);
			pw.append(mensaje.toString());
			pw.close();
			log.write("Trino almacenado");
		} catch (IOException e) {
			log.setLevelSEVERE();
			log.write("error grave en el almacenamiento \n" + e.getCause());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Este metodo es casi como el de comprobarDisponibilidadUsuario trabaja sobre el mismo objeto
	 * busca un contacto y lo muestra en una lista.
	 * @return nombre del usuario, puede estar incompleto
	 * @param nombre del usuario, puede estar incompleto
	 */
	@Override
	public List<String> buscarUsuario(String nickName) throws RemoteException {
		log.write("buscando usuario " + nickName);
		return lista.getUsuario(nickName);
	}

	/**
	 * comprueba si el usuario esta conectado actualmente a la aplicacion. 
	 * @return respuesta booleana al nombre de usuario 
	 * @param nickName del usuario
	 */

	@Override
	public boolean buscarUsuarioConectado(String nickName)
			throws RemoteException {
		log.write("buscando si el usuario " + nickName + "esta conectado");
		return listaConectados.contains(nickName);
	}
	
	/**
	 * Metodo para comprobar si un usuario esta dado de alta en la aplicacion.
	 * @param nickName del usuario que se quiere comprobar si esta dado de alta, el nickName es unico, seria el Primary Key en una base de datos relacional
	 * @return resultado de la busqueda
	 * @throws RemoteException si tiene problemas durante el acceso al objeto remoto
	 */
	
	public boolean comprobarDisponibilidadUsuario(String nickname) throws RemoteException {
		log.write("comprobar si el " + nickname + " nick esta disponible");
		return lista.comprobarDisponibilidadUsuario(nickname.trim());
	}
	
	/**
	 * Comprobacion de la validez de la password del usuario en cuestion
	 * @param nickName y password como String, este metodo a su vez llama al metodo de comprobarLogin del 
	 * objeto lista de usuarios.
	 * 
	 */
	@Override
	public boolean comprobarLogin(String nickName, String password) throws RemoteException {
		log.write("comprobando los datos del usuario " + nickName);
		if (lista.comprobarLogin(nickName.trim(), password.trim())){
			numConectados++;
			listaConectados.add(nickName.trim());
			return true;
		} else {
			log.setLevelSEVERE();
			log.write("error en la autentificacion");
			return false;
		}
		
	}

	/**
	 * Creacion de un nuevo usuario, se le pasa como parametro un objeto del tipo AltaObj que tiene los 
	 * campos para un nuevo usuario de la aplicacion. Tengo que revisar si al pasarle el objeto como argumento
	 * y al ser a su vez serializable que es capaz de contener este objeto los metodos del alta y ejecutarlo 
	 * en el servidor
	 * @param AltaObj contiene los campos necesarios para el alta de un nuevo usuario
	 * @throws RemoteException si tuviera problemas con RMI
	 */
	
	@Override
	public void crearUsuario(AltaObj usuario) throws RemoteException {
		log.write("creacion del nuevo usuario");
		lista.addUserPass(usuario.getNick(), usuario.getPasswd());
		CreaUsuario creaUser = new CreaUsuario();
		creaUser.exe(usuario);
	}

	/**
	 * metodo de desconexion de la aplicacion, aun no tiene concurrencia
	 * @param nombre del usuario que se desconecta
	 */

	public synchronized void desconexion(String nickName){
		log.write("desconectado el usuario");
		listaConectados.remove(nickName);
		numConectados--;
	}

	/**
	 * Cargador de los mensajes offline que son almacenados en un fichero
	 * @param nombre del usuario
	 */

	@Override
	public List<String> getMensajesOffline(String nickName)	throws RemoteException {
		log.write("obteniendo los mensajes offline");
		MensajeOffline mensaje = new MensajeOffline(nickName);
		return mensaje.getListaMensajes();
	}

	/**
	 * Metodo para obtener todos los usuarios conectados a la apliacion
	 * @return Object[] array con los nombres de usuario 
	 */

	@Override
	public Object[] getUsuariosConectados() throws RemoteException {
		log.write("usuarios conectados");
		return listaConectados.toArray();
	}
	
	/**
	 * Loader de la lsita de contactos del usuario logado, este metodo debe ser llamado desde GestorMensajes para evitar que el 
	 * usuario acceda a la base de datos. 
	 * @param nickName del usuario
	 * @return List<String> con el nombre de todos los usuarios añadidos por el usuario que se acab de logar en la aplicacion
	 */
	
	@Override
	public List<String> loadContactos(String nickName) throws RemoteException {
		log.write("carga de contactos");
		CargaUsuario carga = new CargaUsuario(nickName);
		return carga.getList();
	}
	
	/**
	 * Sobreescritura del run para poder implementarlo como hilo del servidor, no como proceso independiente.
	 * quedara como un hilo dentro del servidor de trinos esperando peticiones de los hilos de GestorMensajes y Autenticar
	 */
	@Override
	public void run() {
		System.out.println("hilo de bbdd arrancado");		
		BBDDGUI bbddgui = new BBDDGUI(ip);
		bbddgui.setVisible(true);
	}
	
	/**
	 * Escribe el mensaje offline para el usuario
	 * @param contacto nombre del contacto que esta offline
	 * @param Trino enviado
	 */

	@Override
	public void setMensajeOffline(String contacto, Trino mensaje)
			throws RemoteException {
		log.write("mensaje offline");
		MensajeOffline mensajeOffline = new MensajeOffline(contacto);
		mensajeOffline.setMensajeOffline(contacto, mensaje.toString());
		
	}
	
	/**
	 * Metodo sobreescrito para el envio del mensaje al usuario offline
	 * @param nombre del usuario
	 * @param texto del mensaje
	 */
	
	@Override
	public void setMensajeOffline(String nickName, String texto) throws RemoteException {
		MensajeOffline mensajeOffline = new MensajeOffline(nickName);
		mensajeOffline.setMensajeOffline(nickName, texto);
	}
	
}
