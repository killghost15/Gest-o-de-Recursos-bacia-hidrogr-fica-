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
import agents.Dam;
import agents.Eco1;
import agents.Eco2;
import agents.Farm1;
import agents.Farm2;
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
				Object[] city = {"12","10","-0.2","6","-5","80"};
				Object[] dam = {"-0.06","2.52","0","10"};
				Object[] eco1 = {"-0.29","6.38","-3"};
				Object[] eco2 = {"-0.055","3.63","-23"};
				Object[] farm1 = {"8","6","-0.13","5.98","-6","35"};
				Object[] farm2 = {"15","10","-0.15","7.5","-15"};
				try {
					launchAgents(city,dam,eco1,eco2,farm1,farm2);
				} catch (StaleProxyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
		});
		
		b1.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				Object[] city = {"12","10","-0.2","6","-5","40"};
				Object[] dam = {"-0.06","2.52","0","8"};
				Object[] eco1 = {"-0.29","6.38","-3"};
				Object[] eco2 = {"-0.055","3.63","-23"};
				Object[] farm1 = {"8","6","-0.13","5.98","-6","20"};
				Object[] farm2 = {"15","10","-0.15","7.5","-15"};
				try {
					launchAgents(city,dam,eco1,eco2,farm1,farm2);
				} catch (StaleProxyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
		});
		
		b2.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				Object[] city = {"12","10","-0.2","6","-5","15"};
				Object[] dam = {"-0.06","2.52","0","3"};
				Object[] eco1 = {"-0.29","6.38","-3"};
				Object[] eco2 = {"-0.055","3.63","-23"};
				Object[] farm1 = {"8","6","-0.13","5.98","-6","8"};
				Object[] farm2 = {"15","10","-0.15","7.5","-15"};
				try {
					launchAgents(city,dam,eco1,eco2,farm1,farm2);
				} catch (StaleProxyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
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
	
	void launchAgents(Object[] city, Object[] dam, Object[] eco1, Object[] eco2, Object[] farm1, Object[] farm2) throws StaleProxyException, InterruptedException {
		
		AgentController damController = ac.createNewAgent("Dam",Dam.class.getName(), dam);
		AgentController eco1Controller = ac.createNewAgent("Eco1",Eco1.class.getName(), eco1);
		AgentController eco2Controller = ac.createNewAgent("Eco2",Eco2.class.getName(), eco2);
		AgentController farm1Controller = ac.createNewAgent("Farm1",Farm1.class.getName(), farm1);
		AgentController farm2Controller = ac.createNewAgent("Farm2",Farm2.class.getName(), farm2);
		AgentController cityController = ac.createNewAgent("City",City.class.getName(), city);

		
		damController.start();
		eco1Controller.start();
		eco2Controller.start();
		farm1Controller.start();
		farm2Controller.start();
		cityController.start();
		
		Thread.sleep(5000);
		
		damController.kill();
		eco1Controller.kill();
		eco2Controller.kill();
		farm1Controller.kill();
		farm2Controller.kill();
		cityController.kill();

	}

}
