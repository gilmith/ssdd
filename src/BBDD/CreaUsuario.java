package BBDD;

import java.io.File;
import java.io.IOException;
import util.Loaders.FileLoader;
import GUI.AltaObj;

public class CreaUsuario {
	
	private static final String CODEBASE="java.rmi.server.codebase";
	
	/**
	 * Clase para crear un nuevo usuario en la base de datos, para ello hace uso de la serializacion de clases
	 * Pasa la clase AltaObj y ejecuta el metodo de alta de usuario en el servidor en lugar de en el cliente
	 * @author Jacobo Geada Ansino
	 */
	
	
	/**
	 * Constructor de la clase, configura la propiedad java.rmi.server.codebase para no tener que especificar
	 * para cada clase la ruta desde donde ser realiza la carga dinamica
	 */
	
	public CreaUsuario(){
		System.setProperty(CODEBASE, AltaObj.class.getProtectionDomain().getCodeSource().getLocation().toString());
	}
	
	/**
	 * Ejecuta la clase AltaObje el metodo para añadir el usuario
	 * Y crea el archivo de mensajes vacio.
	 * @param usuario
	 */
	
	public void exe(AltaObj usuario){
		usuario.add();
		archivoMensajesOffline(usuario.getNombre());
	}
	
	/**
	 * metodo privado para crear el archivo de mensajes vacio
	 * @param usuario
	 */
	
	private void archivoMensajesOffline(String usuario){
		File mensajesOffine = new File(FileLoader.getRuta() + usuario + ".msg");
		try {
			mensajesOffine.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
