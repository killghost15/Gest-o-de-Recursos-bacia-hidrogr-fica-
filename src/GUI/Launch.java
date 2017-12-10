package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class Launch extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launch frame = new Launch();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Launch() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JButton b=new JButton("cenario 1");
		/*b.setBounds(0, 0, 50, 30);
		b.setLocation(0, 0);*/
		JButton b1=new JButton("cenario 2");
		JButton b2=new JButton("cenario 3");
		/*b1.setBounds(0, 0,50, 30);
		
		*/
		
		contentPane.add(b,BorderLayout.PAGE_START);
		contentPane.add(b1,BorderLayout.CENTER);
		contentPane.add(b2,BorderLayout.PAGE_END);
	}

}
