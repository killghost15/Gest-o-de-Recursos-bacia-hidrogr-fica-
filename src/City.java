import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class City extends Agent{
	private AID id;
	private String name;
	private float min,x1; //x1 is water withdrawn for the city
	protected void setup(){
		
		Object[] args = getArguments();
		 if (args != null && args.length > 0){
			 name=(String)args[0];
			 id = new AID("City"+name, AID.ISLOCALNAME);
			 
			 
			 min=(float)args[1];
			 
			
			 //n está operacional ainda, só em conceito
			 addBehaviour(new City_Management_water());
		 }
		 else{
			 doDelete();
			 System.out.println("usage City <name><min water necessary>");
		 }
	}
	protected void takeDown() {
		
	}
	
	public class City_Management_water extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			// envia-a o 
			
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("City"+name, AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setOntology("Value sharing");
			msg.setContent(""+x1);
			send(msg);

			
		}
		
		
	}
	

}
