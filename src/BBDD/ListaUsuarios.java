package BBDD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

import util.Loaders.FileLoader;


/**
 * Objeto que realiza la carga de los usuarios dados de alta en la aplicacion, estos usuarios estaran en un fichero
 * llamado lista.txt, este fichero tiene la estructura de una tabla de dispersion USUARIO=PASSWORD, se realizara
 * la lectura del fichero y su carga como un HashMap y ArrayList el HashMap para comprobar si la password es correcta
 * y el ArrayList solo con el nombre de los usuarios, servira para realizar busquedas de disponibilidad de nickName y busquedas 
 * de usuarios para agregar a contactos.
 * @author Jacobo Geada Ansino
 *
 */
public class ListaUsuarios {

	private HashMap<String, String> listaUsuariosClave;
	private ArrayList<String> listaUsuarios;
	private File archivo;
	
	/**
	 * Constructor de la clase, realiza la lectura del fichero hash y los objetos lista
	 * Los deja cargados en memoria en el metodo leerArchivo.
	 */
	
	public ListaUsuarios(){
		listaUsuarios = new ArrayList<String>();
		listaUsuariosClave = new HashMap<String,String>();
		leerArchivo();
	}
	
	/**
	 * Metodo para añadir una nueva entrada en el archivo de lista.txt, una vez añadido al hashMap se 
	 * realiza la escritura del archivo y la recarga de los listados. Es un metodo synchronized para evitar
	 * que varios usuarios accedan al mismo tiempo al objeto y quede incosistente.
	 * @param nick
	 * @param password
	 */
	
	public synchronized void addUserPass(String nickName, String password){
		listaUsuariosClave.put(nickName, password);
		write();
		leerArchivo();
	}

	/**
	 * Busca en el listado de usuarios si el usuario pasado como parametro esta dado o no de alta.
	 * @param usuario
	 * @return
	 */
	
	public boolean comprobarDisponibilidadUsuario(String nickName) {
		if(listaUsuarios.contains(nickName)){
			return true;
		}
		return false;
	}
	
	/**
	 * Comprobacion de la pareja usuario-password metiante del HashMap. Cambiar a un boolean
	 * @param nick
	 * @param password
	 */
	
	public boolean comprobarLogin(String nickName, String password){
		return listaUsuarios.contains(nickName) && password.equals(listaUsuariosClave.get(nickName));
		
	}
	
	/**
	 * Metodo para obtener los nombre de usuario parecidos al que se ha insertado como parametro
	 * @param nickName del usuario a buscar	
	 * @return List<String> de los usuarios con nombres similares
	 */
	
	public List<String> getUsuario(String nickName) {
		List<String> listaparecidos = new ArrayList<String>();
		for(String parecido : listaUsuarios){
			if(parecido.contains(nickName)){
				listaparecidos.add(parecido);
			}
		}
		return listaparecidos;

	}

	/**
	 * Metodo privado para cargar el fichero
	 * Lee el archivo de usuarios y passwords
	 */
	
	private void leerArchivo(){
		archivo = new File(FileLoader.getRuta() + "lista.txt");
		Scanner sc;
		try {
			sc = new Scanner(archivo);
			while(sc.hasNext()){
				String[] usuarioClave = sc.nextLine().split("=");
				listaUsuarios.add(usuarioClave[0]);
				listaUsuariosClave.put(usuarioClave[0], usuarioClave[1]);
				
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fichero de lista de usuarios no encontrado en practica/");
			e.printStackTrace();
		}
		
	}

	/**
	 * Metodo de registro del nuevo usuario en la lista de usuarios passwords
	 */
	
	private void write(){
		File file = new File(FileLoader.getRuta() + "lista.txt");
		try {
			PrintWriter pw = new PrintWriter(file);
			for(Map.Entry<String, String> mapa : listaUsuariosClave.entrySet()){
				pw.println(mapa.getKey()+"="+mapa.getValue());
			}
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("archivo lista.txt no encontrado en practica/");
			e.printStackTrace();
		}

	}
	
	/**
	 * Metodo para obtener todos los usuarios y claves para el mantenimiento de la aplicacion.
	 * Lo realiza leyendo el archivo lista.txt
	 * @return cadena con los nombres de usuarios y claves
	 */
	
	public String getTodosUsuarios(){
		File file = new File(FileLoader.getRuta() + "lista.txt");
		Scanner sc = null;
		StringBuilder sb = new StringBuilder();
		try{
			sc = new Scanner(file);
			while(sc.hasNext()){
				sb.append(sc.nextLine() + "\n");
			}
			sc.close();
		} catch (IOException ioex){
			ioex.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
}
