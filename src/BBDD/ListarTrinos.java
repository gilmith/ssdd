package BBDD;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import util.Loaders.FileLoader;

public class ListarTrinos {
	
	/**
	 * Clase para obtener todos los Trinos que han enviado 
	 */
	
	private File file;
	private Scanner sc;
	
	/**
	 * Constructo de la clase leer el archivo de trinos bbdd.txt
	 */
	
	public ListarTrinos(){
		file = new File(FileLoader.getRuta() + "bbdd.txt");
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Obtencion de los trinos linea a linea
	 * @return cadena con los trinos enviados
	 * 
	 */
	
	public String getTrinos(){
		StringBuilder sb = new StringBuilder();
		while(sc.hasNext()){
			sb.append(sc.nextLine() + "\n");
		}
		return sb.toString();
	}

}
