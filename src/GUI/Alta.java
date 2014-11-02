package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Alta extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 * @param string 
	 */
	public Alta(String string) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(string);
		setBounds(100, 100, 450, 300);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
