package GUI;

import javax.swing.JFrame;

public class Alta extends JFrame {

	/**
	 * Clase para lanzar la ventana con el alta del usuario.
	 * 
	 */
	private static final long serialVersionUID = 9073836937014589985L;
	private PanelAlta panelAlta;
	private static AltaObj datosAlta;
	private String ip;
	/**
	 * Create the frame.
	 * @param string 
	 * @param ip 
	 */
	public Alta(String string, String ip) {
		this.ip = ip;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(string);
		datosAlta = new AltaObj();
		setBounds(100, 100, 1075, 245);
		panelAlta = new PanelAlta(datosAlta, ip);
		this.add(panelAlta);
	}


}
