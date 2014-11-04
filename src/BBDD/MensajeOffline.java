/**
 * 
 */
package BBDD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.Loaders.FileLoader;

/**
 * @author Jacobo Geada Ansino
 * 
 * Leera el fichero de offline y lo devolvera como un List<String>
 *
 */
public class MensajeOffline {
	
	private String ruta;
	private File file;
	private Scanner sc;
	private List<String> listaMensajes;
	
	/**
	 * Constructor de la clase inicializa los mensajes offline de la aplicacion. 
	 * @param usuario
	 */
	
	public MensajeOffline(String usuario){
		listaMensajes = new ArrayList<String>();
		ruta = FileLoader.getRuta() + usuario + ".msg";
		file = new File(ruta);
		if(file.length() == 0){
			listaMensajes.add("no hay mensajes nuevos");
		} else {
			try {
				sc = new Scanner(file);
				while (sc.hasNext()){
					listaMensajes.add(sc.nextLine());
				}
				PrintWriter pr = new PrintWriter(file);
				pr.write("");
				pr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * obtencion del texto de los mensajes offline aun no ve los detalles solo lee lineas
	 * @return List<String> del contenido del fichero
	 */

	public List<String> getListaMensajes(){
		
		return listaMensajes;
	}
	
	public void setMensajeOffline(String usuario, String texto){
		FileWriter file;
		try{
			file = new FileWriter(FileLoader.getRuta() + usuario + ".msg" ,true);
			PrintWriter pw = new PrintWriter(file);
			pw.append(texto);
			pw.close();
		}catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
	
}
