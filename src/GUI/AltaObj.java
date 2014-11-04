package GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import util.Loaders.FileLoader;

public class AltaObj implements Serializable {
	
	/**
	 * Clase serializable con los datos del alta, sera enviada al servidor para que se ejecute en remoto.
	 * @author Jacobo Geada Ansino
	 */
	private static final long serialVersionUID = 4646958858229403513L;
	private String nombre, apellido, nick, mail, passwd;
	
	public AltaObj(){
		
	}
	
	/**
	 * Getter el nombre de usuario
	 * @return nombre
	 */

	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Setter del nombre de usuario
	 * @param nombre
	 */

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Getter del apellido de usuario
	 * @return apellido
	 */

	public String getApellido() {
		return apellido;
	}
	
	/**
	 * Setter del apellido del usuario
	 * @param apellido
	 */

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	/**
	 * Getter del nickName del usuario
	 * @return nickName
	 */

	public String getNick() {
		return nick;
	}
	
	/**
	 * Setter del nick del usuario
	 * @param nick
	 */

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Getter del mail del usuario
	 * @return mail
	 */

	public String getMail() {
		return mail;
	}
	
	/**
	 * Setter del mail del usuario
	 * @param mail
	 */

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	/**
	 * Getter del password del usuario
	 * @return
	 */

	public String getPasswd() {
		return passwd;
	}
	
	/**
	 * Setter del password del usuario
	 * @param passwd
	 */

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	/**
	 * Metodo que se ejecutara en remoto y es el que escribe en la base de datos el registro del nuevo usuario
	 */
	
	public void add() {
		File archivoUsuario = new File(FileLoader.getRuta() + this.getNick());
		try {
			PrintWriter pw = new PrintWriter(archivoUsuario);
			pw.write("NOMBRE DE USUARIO=" + nombre+ "\n");
			pw.write("APELLIDOS DE USUARIO=" + apellido + "\n");
			pw.write("NICK DE USUARIO=" + nick + "\n");
			pw.write("EMAIL=" + mail + "\n");
			pw.write("CONTACTOS=" + "\n");
			pw.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
	
	

}
