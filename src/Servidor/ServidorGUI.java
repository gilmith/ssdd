package Servidor;

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
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import util.Interfaces.ServicioAutenticacionInterface;
import util.Interfaces.ServicioGestorInterface;
import BBDD.ExportFile;

public class ServidorGUI extends JFrame implements ActionListener {

	/**
	 * Interfaz grafica del servidor
	 * @author Jacobo Geada Ansino
	 */
	private static final long serialVersionUID = 6466176881257147554L;
	private JPanel contentPane;
	private JTextArea textInfo, textlogin;
	private String reg0, reg1;
	private ImplGestorMensajes gestor;
	/**
	 * Create the frame.
	 * @param reg1 
	 * @param reg0 
	 * @param gestor 
	 * @param auth 
	 */
	public ServidorGUI(ImplGestorMensajes gestor, String reg0, String reg1) {
		this.reg0 = reg0;
		this.reg1 = reg1;
		this.gestor = gestor;
		setTitle("Servidor GUI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		textInfo = new JTextArea();
		textInfo.setToolTipText("CLICKA PARA OBTENER INFORMACION DEL SERVIDOR");
		textInfo.setText("INFORMACION DEL SERVIDOR");
		textInfo.setEditable(false);
		textInfo.setRows(8);
		GridBagConstraints gbc_textInfo = new GridBagConstraints();
		gbc_textInfo.insets = new Insets(0, 0, 5, 5);
		gbc_textInfo.fill = GridBagConstraints.BOTH;
		gbc_textInfo.gridx = 0;
		gbc_textInfo.gridy = 0;
		contentPane.add(textInfo, gbc_textInfo);
		
		JButton botonInfo = new JButton("info");
		botonInfo.addActionListener(this);
		botonInfo.setActionCommand("info");
		GridBagConstraints gbc_botonInfo = new GridBagConstraints();
		gbc_botonInfo.insets = new Insets(0, 0, 5, 5);
		gbc_botonInfo.gridx = 0;
		gbc_botonInfo.gridy = 1;
		contentPane.add(botonInfo, gbc_botonInfo);
		
		textlogin = new JTextArea();
		textlogin.setToolTipText("CLICKA PARA OBTENER LA LISTA DE USUARIOS LOGADOS");
		textlogin.setText("USUARIOS LOGADOS");
		textlogin.setEditable(false);
		GridBagConstraints gbc_textlogin = new GridBagConstraints();
		gbc_textlogin.insets = new Insets(0, 0, 5, 5);
		gbc_textlogin.fill = GridBagConstraints.BOTH;
		gbc_textlogin.gridx = 0;
		gbc_textlogin.gridy = 2;
		contentPane.add(textlogin, gbc_textlogin);
		
		JButton login = new JButton("logins");
		login.addActionListener(this);
		login.setActionCommand("login");
		GridBagConstraints gbc_login = new GridBagConstraints();
		gbc_login.insets = new Insets(0, 0, 5, 5);
		gbc_login.gridx = 0;
		gbc_login.gridy = 3;
		contentPane.add(login, gbc_login);
		
		JButton botonCerrar = new JButton("Cerrar servidor");
		botonCerrar.addActionListener(this);
		
		JButton exportLogin = new JButton("ExportLogins");
		exportLogin.setActionCommand("exportLogins");
		exportLogin.addActionListener(this);
		GridBagConstraints gbc_ExportLogin = new GridBagConstraints();
		gbc_ExportLogin.insets = new Insets(0, 0, 5, 5);
		gbc_ExportLogin.gridx = 1;
		gbc_ExportLogin.gridy = 3;
		contentPane.add(exportLogin, gbc_ExportLogin);
		botonCerrar.setActionCommand("cerrar");
		GridBagConstraints gbc_botonCerrar = new GridBagConstraints();
		gbc_botonCerrar.insets = new Insets(0, 0, 5, 5);
		gbc_botonCerrar.gridx = 0;
		gbc_botonCerrar.gridy = 4;
		contentPane.add(botonCerrar, gbc_botonCerrar);
		
		JButton exportInfo = new JButton("ExportInfo");
		exportInfo.setActionCommand("exportInfo");
		exportInfo.addActionListener(this);
		GridBagConstraints gbc_ExportInfo = new GridBagConstraints();
		gbc_ExportInfo.insets = new Insets(0, 0, 5, 5);
		gbc_ExportInfo.gridx = 1;
		gbc_ExportInfo.gridy = 1;
		contentPane.add(exportInfo, gbc_ExportInfo);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand() == "exportLogins"){
			ExportFile export = new ExportFile("usuariosOnline.txt");
			export.export(textlogin.getText());
		} else if (arg0.getActionCommand() == "exportInfo"){
			ExportFile export = new ExportFile("infoServidor.txt");
			export.export(textInfo.getText());
		} else if (arg0.getActionCommand() == "cerrar"){
			try {
				try {
					Naming.unbind("rmi://" + reg0  + ":" + ServicioAutenticacionInterface.PUERTO + "/" + ServicioAutenticacionInterface.NAME);
					Naming.unbind("rmi://" + reg1  + ":" + ServicioGestorInterface.PUERTO + "/" + ServicioGestorInterface.NAME);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.exit(0);
		} else if (arg0.getActionCommand() == "info"){
			
		} else if (arg0.getActionCommand() == "login"){
			try {
				textlogin.setText(gestor.getUsuariosConectados());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
