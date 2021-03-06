/**
 * 
 */
package util.LogManager;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Log manager para el seguimiento de la aplicacion.
 * @author Jacobo Geada Ansino
 *
 */
public class LogObj{
	
	private static Logger log;
	
	/**
	 * Constructor de log para cualquiera de las clases que conforman el cliente
	 * El parametro que se le pasa es el nombre del proceso que esta llamando al Log, creara
	 * un fichero proceso.txt en /tmp en linux y en C:/practica en windows
	 * @param ruta
	 */
	
	public LogObj(String ruta) {
		String OS;
		if (System.getProperty("os.name").contains("Windows")) {
			OS = "C:/practica/";
		} else {
			OS = System.getProperty("user.home") + "/practica/";
		}
		ruta = OS + ruta;
		log = Logger.getLogger(ruta);
		log.setLevel(Level.INFO);
		try {
			FileHandler manejadorArchivo = new FileHandler(ruta);
			log.addHandler(manejadorArchivo);
		} catch (IOException |SecurityException e) {
			log.setLevel(Level.SEVERE);
			log.severe("error en logger");
			e.printStackTrace();
		}
	}
	
	public void setLevelINFO(){
		log.setLevel(Level.INFO);
	}
	
	public void setLevelSEVERE(){
		log.setLevel(Level.SEVERE);
	}
	
	public void write(String cadena){
		if(log.getLevel() == Level.INFO){
			log.info(cadena);
		} else if (log.getLevel() == Level.SEVERE){
			log.severe(cadena);
		}
	}
	

	
	

}
