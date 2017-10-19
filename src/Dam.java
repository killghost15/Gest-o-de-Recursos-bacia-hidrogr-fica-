import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class Dam extends Agent{
	private AID id;
	private String name;
	private float x2,x1,a2,b2,c2,S,Q1,sumob,sumpen; //x1 is water withdrawn for the city
	protected void setup(){
		
		Object[] args = getArguments();
		 if (args != null && args.length > 0){
			 name=(String)args[0];
			 id = new AID("Dam"+name, AID.ISLOCALNAME);
			 
			 
			a2=(float)args[1];
			b2=(float)args[2];
			c2=(float)args[3];
			
			x2=0;
			 
			
			 //n está operacional ainda, só em conceito
			 addBehaviour(new Dam_Management_water());
		 }
		 else{
			 doDelete();
			 System.out.println("usage City <name><min water necessary>");
		 }
	}
	protected void takeDown() {
		
	}
	
	public class Dam_Management_water extends CyclicBehaviour{
		private String state="start";

		@Override
		public void action() {
			
			
			ACLMessage answer = myAgent.receive();
			if (answer != null) {
				
				x1=Float.parseFloat(answer.getContent().split(" ")[0]);
				sumob=Float.parseFloat(answer.getContent().split(" ")[1]);
				sumpen=Float.parseFloat(answer.getContent().split(" ")[2]);
			}
			else{
				block();
			}
			
			
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("Farm", AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setOntology("Value sharing");
			msg.setContent(""+x2+" "+(fobjectiveDam()+sumob)+" "+(fpenalty()+sumpen));
			send(msg);
			
			

			
		}
		
		
	}
	
	public float fobjectiveDam(){
		return a2*x2*x2+b2*x2+c2;
	}
	public float fpenalty(){
		float res=0;
		if((x2-S-Q1 + x1)<=0){
			return res;
		}
		else{
			
			
			res+=1000*(x2-S-Q1+x1);
			return res;
		}
		
	
	}
	
	
}
