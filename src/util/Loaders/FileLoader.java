package util.Loaders;

/**
 * Clase para aumentar la compatibilidad de la practica para sistemas windows, linux y unix
 * Para ello buscara la carpeta c:\practica o en su defecto en el $HOME 
 * @author Jacobo Geada Ansino
 *
 */


public class FileLoader {
	
	public static String getRuta(){
		String OS;
		if (System.getProperty("os.name").contains("Windows")) {
			OS = "C:/practica/";
		} else {
			OS = System.getProperty("user.home") + "/practica/";
		}
		return OS;
	}
}
