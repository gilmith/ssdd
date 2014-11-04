package BBDD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.Loaders.FileLoader;

public class CargaUsuario implements Serializable{
	
	/**
	 * Clase para realizar la carga de usuario desde el fichero de texto del usuario
	 * @author Jacobo Geada Ansino
	 * 
	 */
	private static final long serialVersionUID = 3866000352891689944L;
	private File archivo;
	private List<String> listaContactos;
	
	/**
	 * Constructor de la clase, para realizar la carga tiene que darse el nick del usuario como 
	 * parametro
	 * @param nick
	 */
	
	public CargaUsuario(String nick){
		listaContactos = new ArrayList<String>();
		archivo = new File(FileLoader.getRuta() + nick);
		Scanner sc;
		try {
			sc = new Scanner(archivo);
			String linea;
			while(sc.hasNext()){
				linea = sc.nextLine();
				if(linea.startsWith("CONTACTOS")){
					setList(linea);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo getter para obtener el campo de la clase lista de contactos del usuario 
	 * @return List<String> con los usuarios almecenados. 
	 */

	public List<String> getList() {
		return listaContactos;
	}
	
	/**
	 * Metodo privado para setear la lista de los contactos del usuario logado
	 * @param linea que contiene la palabra CONTACTOS
	 */
	
	private void setList(String linea){
		String[] partida = linea.split("=");
		if(partida.length == 1){
			listaContactos.add("");
		} else{
			for(String nombre : partida[1].split(",")){
				listaContactos.add(nombre);
			}
		}
	}
	
	/**
	 * Añadir un nuevo contacto como usuario
	 * @param nickName del usuario que va a añadir otro contacto	
	 * @param nuevo nombre del contacto
	 */

	
	public void addUsuario(String nickName, String nuevo){
		archivo = new File(FileLoader.getRuta() + nickName);
		Scanner sc;
		try {
			sc = new Scanner(archivo);
			StringBuilder sb = new StringBuilder();
			while(sc.hasNext()){
				sb.append("\n" + sc.nextLine());
			}
			sc.close();
			sb.append("," + nuevo);
			PrintWriter pw = new PrintWriter(archivo);
			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
