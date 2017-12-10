package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import agents.City;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Launch extends JFrame {
	Runtime rt;
	Profile pMain;
	AgentContainer mc;
	ProfileImpl pContainer;
	AgentController rma;
	Profile p;
	AgentContainer ac;
	
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
	 * @throws StaleProxyException 
	 */
	public Launch() throws StaleProxyException {
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
		b.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				String[] city = {"arg0","arg1","arg2","arg3","arg4","arg5"};
				String[] dam = {"arg0","arg1","arg2","arg3"};
				String[] eco1 = {"arg0","arg1","arg2"};
				String[] eco2 = {"arg0","arg1","arg2"};
				String[] farm1 = {"arg0","arg1","arg2","arg3","arg4","arg5"};
				String[] farm2 = {"arg0","arg1","arg2"};
				try {
					launchAgents(city,dam,eco1,eco2,farm1,farm2);
				} catch (StaleProxyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
		});
		
		b1.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				String[] city = {"arg0","arg1","arg2","arg3","arg4","arg5"};
				String[] dam = {"arg0","arg1","arg2","arg3"};
				String[] eco1 = {"arg0","arg1","arg2"};
				String[] eco2 = {"arg0","arg1","arg2"};
				String[] farm1 = {"arg0","arg1","arg2","arg3","arg4","arg5"};
				String[] farm2 = {"arg0","arg1","arg2"};
				try {
					launchAgents(city,dam,eco1,eco2,farm1,farm2);
				} catch (StaleProxyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
		});
		
		b2.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				String[] city = {"arg0","arg1","arg2","arg3","arg4","arg5"};
				String[] dam = {"arg0","arg1","arg2","arg3"};
				String[] eco1 = {"arg0","arg1","arg2"};
				String[] eco2 = {"arg0","arg1","arg2"};
				String[] farm1 = {"arg0","arg1","arg2","arg3","arg4","arg5"};
				String[] farm2 = {"arg0","arg1","arg2"};
				try {
					launchAgents(city,dam,eco1,eco2,farm1,farm2);
				} catch (StaleProxyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
		});
		

		contentPane.add(b,BorderLayout.PAGE_START);
		contentPane.add(b1,BorderLayout.CENTER);
		contentPane.add(b2,BorderLayout.PAGE_END);

		//Launch Container and RMA
		rt = Runtime.instance();

		pMain = new ProfileImpl(null, 8888, null);

		System.out.println("Launching a whole in-process platform..." + pMain);
		mc = rt.createMainContainer(pMain);

		pContainer = new ProfileImpl(null, 8888, null);
		System.out.println("Launching the agent container ..." + pContainer);

		System.out.println("Launching the rma agent on the main container ...");
		rma = mc.createNewAgent("rma", "jade.tools.rma.rma",new Object[0]);
		rma.start();

		// Get a hold on JADE runtime

		p = new ProfileImpl(false);
		ac = rt.createAgentContainer(p);
	}
	
	void launchAgents(String[] city, String[] dam, String[] eco1, String[] eco2, String[] farm1, String[] farm2) throws StaleProxyException {
		AgentController cityController = ac.createNewAgent("City",City.class.getName(), city);
		AgentController damController = ac.createNewAgent("Dam",City.class.getName(), dam);
		AgentController eco1Controller = ac.createNewAgent("Eco1",City.class.getName(), eco1);
		AgentController eco2Controller = ac.createNewAgent("Eco2",City.class.getName(), eco2);
		AgentController farm1Controller = ac.createNewAgent("Farm1",City.class.getName(), farm1);
		AgentController farm2Controller = ac.createNewAgent("Farm2",City.class.getName(), farm2);

		cityController.start();
		damController.start();
		eco1Controller.start();
		eco2Controller.start();
		farm1Controller.start();
		farm2Controller.start();
		
	}

}
