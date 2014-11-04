package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.Interfaces.Trino;
import util.Interfaces.MensajeInterface;
import util.Interfaces.ServicioDatosInterface;
import util.Interfaces.ServicioGestorInterface;
import util.Loaders.serverLoader;

public class PanelesMensajeria extends JSplitPane implements ActionListener, MouseListener {
	
	/**
	 * Panel de Mensajeria principal de a aplicacion
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orientacion;
	private JTextArea areaContactos, areaMensajes;
	private String nickName, ip;
	private ServicioGestorInterface interfaz;
	private JTextField buscador;	
	private List<String> listaContactos;
	
	
	public PanelesMensajeria(String orientacion){
		this.orientacion = orientacion;
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		setComponentes();
	}
	
	public PanelesMensajeria(String orientacion, String nickName, String ip) {
		this.orientacion = orientacion;
		this.nickName = nickName;
		this.ip = ip;
		try {
			interfaz = serverLoader.getGestor(ip);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		setComponentes();
		getContactos1(nickName);
	}

	private void setComponentes(){
		JPanel panelArriba = new JPanel();
		this.setLeftComponent(panelArriba);
		if(orientacion.equals("izquierda")){
			areaContactos = new JTextArea(getContactos(nickName));
			areaContactos.setEditable(false);
			areaContactos.setRows(8);
			areaContactos.setColumns(8);
			JScrollPane jsp = new JScrollPane(areaContactos);
			jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			panelArriba.add(jsp);
		} else {
			JScrollPane jsp;
			areaMensajes = new JTextArea();
			areaMensajes.setText(getMensajesOffline(nickName));
			areaMensajes.setEditable(false);
			areaMensajes.setRows(10);
			areaMensajes.setColumns(30);
			areaMensajes.addMouseListener(this);
			jsp = new JScrollPane(areaMensajes);
			jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			panelArriba.add(jsp);
		}
		
		JPanel panelAbajo = new JPanel();
		this.setRightComponent(panelAbajo);
		buscador = new JTextField();
		buscador.setColumns(10);
		panelAbajo.add(buscador);
		JButton boton = new JButton(getMensajeOrientacion());
		boton.setActionCommand(getMensajeOrientacion());
		boton.addActionListener(this);
		panelAbajo.add(buscador);
		panelAbajo.add(boton);
	}
	


	private String getMensajeOrientacion(){
		if(orientacion.equals("izquierda")){
			return "Buscar";
		} else {
			return "Enviar";
		}
	}
	
	/**
	 * primera prueba de la carga de la lista de contactos para el panel izquierdo
	 * Lo hago accediendo directamente a la base de datos sin pasar por el gestor
	 * tengo que hacerlo independiente del usuario
	 * @param nickName2 
	 */
	
	private String getContactos(String nickName2){
		try {
			return interfaz.getContactos(nickName2);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private void getContactos1(String nickName2){
		try {
			listaContactos = interfaz.getContactos1(nickName2);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  Metodo para la recepcion de los mensajes offline que han sido enviados al usuario
	 * @param nickName2 
	 * @return string de mensajes pero tendria que ser formateado
	 */
	private String getMensajesOffline(String nickName2){		
		try {
			return interfaz.getMensajesOffline(nickName2);
		} catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 *  Controlador de las acciones de los botones.
	 * 
	 */

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand() == "Buscar"){
			System.out.println("busca un usuario");
			try {
				List<String> parecidos = interfaz.buscarUsuario(buscador.getText());
				Iterator it = parecidos.iterator();
				Object[] array = new Object[parecidos.size()];
				int indice = 0;
				while(it.hasNext()) {
					array[indice] = it.next();
					indice++;
				}
				Object seleccion = JOptionPane.showInputDialog(
						   getParent(),
						   "Seleccione nick",
						   "Selector de nicks",
						   JOptionPane.QUESTION_MESSAGE,
						   null,  
						   array,
						   array[0]);
				interfaz.addUsuario(nickName, seleccion.toString());
				areaContactos.setText(interfaz.getContactos(nickName));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else if (arg0.getActionCommand() == "Enviar"){
			try {
				Trino mensaje = new Trino();
				mensaje.setEmisor(nickName);
				mensaje.setTexto(buscador.getText() + "\n");
				interfaz.send(mensaje, listaContactos);
				buscador.setText("");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		
		}
		
	}
	
	
	private void recargar() throws RemoteException{
		StringBuilder sb = new StringBuilder();
		List<Trino> lista = interfaz.receive(nickName);
		if(lista == null){
			String mensaje = areaMensajes.getText();
			mensaje += "no hay mensajes nuevos\n";
			areaMensajes.setText(mensaje);
		}else{
			for(Trino mensaje : lista){
				sb.append(mensaje.getEmisor() + "\n" + mensaje.getHora() + "\n" + mensaje.getTexto() + "\n");
			}
			lista = null;
			areaMensajes.setText(sb.toString());
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		try {
			recargar();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}
