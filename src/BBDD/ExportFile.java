package BBDD;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import util.Loaders.FileLoader;

/**
 * Clase para guardar datos en archivo de texto plao
 * @author Jacobo Geada Ansino
 *
 */



public class ExportFile {
	
	private File file;
	private PrintWriter pw;
	
	/**
	 * Constructor de la clase, se le pasa como paramaetro el nombre del fichero
	 * @param fichero
	 */
	
	public ExportFile(String fichero){
		file = new File(FileLoader.getRuta() + fichero);
		try{
			pw = new PrintWriter(file);
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Metodo para realizar la escritura del archivo
	 * @param datos que contendra el archivo
	 */
	
	public void export(String datos){
		pw.write(datos);
		pw.close();
	}

}
