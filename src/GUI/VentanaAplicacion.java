package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import util.Loaders.serverLoader;

public class VentanaAplicacion extends JFrame implements WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5894358124803821496L;
	private JPanel contentPane;
	private String nickName, ip;

	/**
	 * Create the frame. 
	 * @param ip 
	 * @throws RemoteException 
	 */
	public VentanaAplicacion(String nickName, String ip){
		this.nickName = nickName;
		this.ip = ip;
		setTitle("Mensajeria Trinos usuario " + nickName);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		PanelesMensajeria pizq = new PanelesMensajeria("izquierda", nickName, ip);
		PanelesMensajeria pder = new PanelesMensajeria("derecha", nickName, ip);
		splitPane.setLeftComponent(pizq);
		splitPane.setRightComponent(pder);
		this.addWindowListener(this);
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("aqui tiene que hacer la desconexion del servidor y de la base de datos");
		try {
			serverLoader.getGestor(ip).desconectar(nickName);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
