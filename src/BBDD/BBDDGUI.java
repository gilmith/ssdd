package BBDD;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import util.Interfaces.ServicioDatosInterface;

public class BBDDGUI extends JFrame implements ActionListener {

	/**
	 * Clase para mostar la interfaz grafica de la bse de datos.
	 * Para mostrar los datos usa un patron GRASP de controlador. 
	 * El controlador actionPerformed es el que se ocupa de coordinar las acciones de
	 * cada boton definido. 
	 * Cerrar la ventana no cierra la base de datos, para cerrar la base de datos tiene que 
	 * pulsarse el boton de cerrar.
	 * @author Jacobo Geada Ansino
	 */
	private static final long serialVersionUID = 1636058328388706832L;
	private JPanel contentPane;
	private JTextArea textoInfo, textoListadoDeUsuarios, txtrListadoDeTodos;
	private String registro;

	public BBDDGUI(String registro) {
		this.registro = registro;
		setTitle("GUI BBDD TRINOS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		textoInfo = new JTextArea();
		JScrollPane jsp1 = new JScrollPane(textoInfo);
		textoInfo.setToolTipText("Clicka en informacion y obtenla");
		textoInfo.setText("Informacion del servidor");
		textoInfo.setEditable(false);
		jsp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_textoInfo = new GridBagConstraints();
		gbc_textoInfo.insets = new Insets(0, 0, 5, 5);
		gbc_textoInfo.fill = GridBagConstraints.BOTH;
		gbc_textoInfo.gridx = 0;
		gbc_textoInfo.gridy = 0;
		contentPane.add(jsp1, gbc_textoInfo);
		
		JButton botonInfo = new JButton("informacion");
		botonInfo.setToolTipText("obteer informacion de la base de datos");
		botonInfo.setActionCommand("info");
		botonInfo.addActionListener(this);
		GridBagConstraints gbc_botonInfo = new GridBagConstraints();
		gbc_botonInfo.insets = new Insets(0, 0, 5, 5);
		gbc_botonInfo.gridx = 0;
		gbc_botonInfo.gridy = 1;
		contentPane.add(botonInfo, gbc_botonInfo);
		
		textoListadoDeUsuarios = new JTextArea();
		JScrollPane jsp2 = new JScrollPane(textoListadoDeUsuarios);
		jsp2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		textoListadoDeUsuarios.setToolTipText("Clicka y obten el listado de todos los usarios");
		textoListadoDeUsuarios.setEditable(false);
		textoListadoDeUsuarios.setText("Listado de usuarios registrados");
		GridBagConstraints gbc_textoListadoDeUsuarios = new GridBagConstraints();
		gbc_textoListadoDeUsuarios.insets = new Insets(0, 0, 5, 5);
		gbc_textoListadoDeUsuarios.fill = GridBagConstraints.BOTH;
		gbc_textoListadoDeUsuarios.gridx = 0;
		gbc_textoListadoDeUsuarios.gridy = 2;
		contentPane.add(jsp2, gbc_textoListadoDeUsuarios);
		
		JButton botonListado = new JButton("listar usuario");
		botonListado.setToolTipText("listar todos los usuarios y claves de la aplicacion");
		botonListado.setActionCommand("listado");
		botonListado.addActionListener(this);
		GridBagConstraints gbc_botonListado = new GridBagConstraints();
		gbc_botonListado.insets = new Insets(0, 0, 5, 5);
		gbc_botonListado.gridx = 0;
		gbc_botonListado.gridy = 3;
		contentPane.add(botonListado, gbc_botonListado);
		
		JButton exportarListado = new JButton("exportarListado");
		exportarListado.setToolTipText("Exportar el contenido del listado a archivo");
		exportarListado.setActionCommand("exportar usuarios");
		exportarListado.addActionListener(this);
		GridBagConstraints gbc_exportarListado = new GridBagConstraints();
		gbc_exportarListado.insets = new Insets(0, 0, 5, 0);
		gbc_exportarListado.gridx = 1;
		gbc_exportarListado.gridy = 3;
		contentPane.add(exportarListado, gbc_exportarListado);
		
		txtrListadoDeTodos = new JTextArea();
		JScrollPane jsp3 = new JScrollPane(txtrListadoDeTodos);
		jsp3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		txtrListadoDeTodos.setText("Listado de todos los mensajes almacenados");
		GridBagConstraints gbc_txtrListadoDeTodos = new GridBagConstraints();
		gbc_txtrListadoDeTodos.insets = new Insets(0, 0, 5, 5);
		gbc_txtrListadoDeTodos.fill = GridBagConstraints.BOTH;
		gbc_txtrListadoDeTodos.gridx = 0;
		gbc_txtrListadoDeTodos.gridy = 4;
		contentPane.add(jsp3, gbc_txtrListadoDeTodos);
		
		JButton botonListarTrinos = new JButton("listar trinos");
		botonListarTrinos.setToolTipText("Listar todos los Trinos enviados");
		botonListarTrinos.setActionCommand("listar trinos");
		botonListarTrinos.addActionListener(this);
		GridBagConstraints gbc_botonListarTrinos = new GridBagConstraints();
		gbc_botonListarTrinos.insets = new Insets(0, 0, 5, 5);
		gbc_botonListarTrinos.gridx = 0;
		gbc_botonListarTrinos.gridy = 5;
		contentPane.add(botonListarTrinos, gbc_botonListarTrinos);
		
		JButton botonCerrar = new JButton("cerrar servidor de base de datos");
		botonCerrar.setToolTipText("Cerrar conexion con la base de datos");
		GridBagConstraints gbc_botonCerrar = new GridBagConstraints();
		gbc_botonCerrar.insets = new Insets(0, 0, 5, 5);
		gbc_botonCerrar.gridx = 0;
		gbc_botonCerrar.gridy = 6;
		botonCerrar.setActionCommand("Cerrar");
		botonCerrar.addActionListener(this);
		
		JButton exportarTrinos = new JButton("exportarTrinos");
		exportarTrinos.setToolTipText("Exportar Trinos a un archivo de texto plano");
		exportarTrinos.setActionCommand("exportar Trinos");
		exportarTrinos.addActionListener(this);
		GridBagConstraints gbc_exportarTrinos = new GridBagConstraints();
		gbc_exportarTrinos.insets = new Insets(0, 0, 5, 0);
		gbc_exportarTrinos.gridx = 1;
		gbc_exportarTrinos.gridy = 5;
		contentPane.add(exportarTrinos, gbc_exportarTrinos);
		contentPane.add(botonCerrar, gbc_botonCerrar);
		
		JButton exportarInfo = new JButton("exportarInfo");
		exportarInfo.setActionCommand("exportar Info");
		exportarInfo.addActionListener(this);
		GridBagConstraints gbc_exportarInfo = new GridBagConstraints();
		gbc_exportarInfo.insets = new Insets(0, 0, 5, 0);
		gbc_exportarInfo.gridx = 1;
		gbc_exportarInfo.gridy = 1;
		contentPane.add(exportarInfo, gbc_exportarInfo);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand() == "listado"){
			ListaUsuarios lista = new ListaUsuarios();
			textoListadoDeUsuarios.setText(lista.getTodosUsuarios());
			textoListadoDeUsuarios.setEditable(false);
		} else if(arg0.getActionCommand() == "listar trinos"){
			ListarTrinos lt = new ListarTrinos();
			txtrListadoDeTodos.setText(lt.getTrinos());
			txtrListadoDeTodos.setEditable(false);
		} else if(arg0.getActionCommand() == "exportar Info"){
			ExportFile export = new ExportFile("exportarinfo.txt");
			export.export(textoInfo.getText());
		} else if (arg0.getActionCommand() == "Cerrar"){
				try {
					Naming.unbind("rmi://" + registro  + ":" + ServicioDatosInterface.PUERTO + "/" + ServicioDatosInterface.NAME);
				} catch (RemoteException | MalformedURLException
						| NotBoundException e) {
					e.printStackTrace();
				}
				System.exit(0);
		} else if (arg0.getActionCommand() == "exportar usuarios"){
			ExportFile export = new ExportFile("exportusuarios.txt");
			export.export(textoListadoDeUsuarios.getText());
		} else if (arg0.getActionCommand() == "exportar Trinos"){
			ExportFile export = new ExportFile("exportTrinos.txt");
			export.export(txtrListadoDeTodos.getText());
		} else if ( arg0.getActionCommand() == "info"){
			StringBuilder sb = new StringBuilder();
			sb.append("INFORMACION DEL SERVIDOR\n");
			sb.append("SISTEMA OPERATIVO = " + System.getProperty("os.name") + "\n");
			sb.append("VERSION DE TRINOS = 1.0\n");
			sb.append("CARPETA DE CONFIGURACION = practica/bbdd.txt");
			sb.append("ALUMNO = JACOBO GEADA ANSINO\n");
			sb.append("DNI = 53450445D");
			textoInfo.setText(sb.toString());
		}
	}

}
