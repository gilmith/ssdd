package Servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import util.Interfaces.Trino;
import util.Interfaces.MensajeInterface;
import util.Interfaces.ServicioDatosInterface;
import util.Interfaces.ServicioGestorInterface;
import util.Loaders.BasedeDatosLoader;

public class ImplGestorMensajes extends UnicastRemoteObject implements ServicioGestorInterface, Runnable{

	/**
	 * Implementacion del servicio de Gestion de Trinos de la aplicacion. 
	 * Incluye el CallBack de usuario. Esta clase es una capa para impedir el acceso directo por parte
	 * de los clientes a la base de datos
	 * @author Jacobo Geada Ansino
	 */
	private static final long serialVersionUID = -3432389503240105659L;
	private static ServicioDatosInterface bbdd;
	private HashMap<String, List<Trino>> memoria;
	private String ip;

	protected ImplGestorMensajes(String ip) throws RemoteException {
		super();
		this.ip = ip;
		bbdd = BasedeDatosLoader.getBBDD(ip);
	}
	
	/**
	 * Metodo de desconexion de la base de datos, solo llama al metodo de la base de datos para realizar la 
	 * operacion
	 * @param nickName
	 * @throws RemoteException
	 */

	@Override
	public void desconectar(String nickName) throws RemoteException {
		bbdd.desconexion(nickName);
	}
	
	/**
	 * Getter de los contactos del usuario
	 * @param nickName
	 * @return
	 * @throws RemoteException
	 */

	@Override
	public String getContactos(String nickName) throws RemoteException {
		StringBuilder sb = new StringBuilder();
		List<String> lista = bbdd.loadContactos(nickName);
		for(String nombre : lista){
			sb.append(nombre + "\n");
		}
		return sb.toString();
	}
	
	/**
	 * Getter de los mensajes offline del usuario
	 * @param nickName
	 * @return
	 * @throws RemoteException
	 */

	@Override
	public String getMensajesOffline(String nickName) throws RemoteException {
		StringBuilder sb = new StringBuilder();
		List<String> lista = bbdd.getMensajesOffline(nickName);
		for(String nombre : lista){
			sb.append(nombre + "\n");
		}
		return sb.toString();
					
	}
	
	/**
	 * Metodo de envio por RMI de los mensajes al usuario, para ello usara un objeto de la clase Trino. 
	 * Este mensaje seria para enviar a un solo usuario, fue desarrollado en primer lugar, pero el texto de la
	 * practica indica que debe de recibirse por todos los usuarios. Se deja como ejemplo del desarrollo 
	 * @param mensaje
	 * @throws RemoteException
	 */

	@Override
	public void send(Trino mensaje) throws RemoteException {
		bbdd.almacenTrinos(mensaje);
		String receptor = mensaje.getReceptor();
		if(bbdd.buscarUsuarioConectado(receptor)){
			if(memoria.isEmpty()){
				ArrayList<Trino> lista = new ArrayList<Trino>();
				lista.add(mensaje);
				memoria.put(receptor, lista);
			}else{
				if(memoria.get(receptor) == null){
					ArrayList<Trino> lista = new ArrayList<Trino>();
					lista.add(mensaje);
					memoria.put(receptor, lista);
				} else{
					List<Trino> list = memoria.get(receptor);
					list.add(mensaje);
					memoria.replace(receptor, (ArrayList<Trino>) list);
				}
			}
			
			
		} else {
			bbdd.setMensajeOffline(mensaje.getReceptor(), mensaje.getTexto());
		}
		
		
	}
	
	/**
	 * Callback del usuario, cuando el usuario pase el raton por encima del texto de mensajes
	 * consultara una tabla de dispersion del tipo HashMap<String, List<Trino>> para recbir los mensajes online
	 * Esta tabla es cargada en el gestor de mensajeria en el run
	 */
	
	@Override
	public List<Trino> receive(String nickName) throws RemoteException {
		return memoria.get(nickName);
		
	}

	/**
	 * Arranque del hilo de gestion del mensajes
	 */
	
	@Override
	public void run() {
		System.out.println("servicio gestor arrancado");
		memoria = new HashMap<String, List<Trino>>();
	}

	/**
	 * Busqueda de los usuarios en la base de datos por nickname
	 */
	
	@Override
	public List<String> buscarUsuario(String nickName) throws RemoteException{
		try {
			return bbdd.buscarUsuario(nickName.trim());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
	/**
	 * Metodo para añadir un nuevo usuario a los contactos
	 * @param nickName
	 * @param nuevo
	 * @throws RemoteException
	 */

	@Override
	public void addUsuario(String nickName, String nuevo) throws RemoteException {
		bbdd.addUsuario(nickName, nuevo);		
	}

	/**
	 * Metodo para obtener todos los contactos del usuario
	 * @param nickName2
	 * @return
	 * @throws RemoteException
	 */
	
	@Override
	public List<String> getContactos1(String nickName2) throws RemoteException {
		return bbdd.loadContactos(nickName2);
	}
	
	/**
	 * Metodo de envio de mensajes, comprobara si el usuario esta online para hacer el envio
	 * @param mensaje
	 * @param listaContactos
	 * @throws RemoteException
	 */

	@Override
	public void send(Trino mensaje, List<String> listaContactos) throws RemoteException{
		bbdd.almacenTrinos(mensaje);
		for(String contacto : listaContactos){
			 synchronized (contacto) {
				 if(bbdd.buscarUsuarioConectado(contacto)){
						if(memoria.isEmpty()){
							ArrayList<Trino> lista = new ArrayList<Trino>();
							lista.add(mensaje);
							memoria.put(contacto, lista);
						}else{
							if(memoria.get(contacto) == null){
								ArrayList<Trino> lista = new ArrayList<Trino>();
								lista.add(mensaje);
								memoria.put(contacto, lista);
							} else{
								List<Trino> list = memoria.get(contacto);
								list.add(mensaje);
								memoria.replace(contacto, (ArrayList<Trino>) list);
							}
						}
						
					} else {
						bbdd.setMensajeOffline(contacto, mensaje.getTexto());
						bbdd.setMensajeOffline(contacto, mensaje);
					}
				}
			}		
	}
	
	/**
	 * Metodo para obtener los usuarios conectados
	 * @return
	 * @throws RemoteException
	 */

	public String getUsuariosConectados() throws RemoteException {
		StringBuilder sb = new StringBuilder();
		for(Object objeto : bbdd.getUsuariosConectados()){
			sb.append((String) objeto + "\n");
		}
		return sb.toString();
		
	}
}
