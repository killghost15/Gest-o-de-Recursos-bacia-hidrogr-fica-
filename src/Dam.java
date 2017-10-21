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
			 
			 id = new AID("Dam", AID.ISLOCALNAME);
			 
			 
			a2=Float.parseFloat((String)args[0]);
			b2=Float.parseFloat((String)args[1]);
			c2=Float.parseFloat((String)args[2]);
			S=Float.parseFloat((String)args[3]);
			
			x2=0;
			 
			
			 //n está operacional ainda, só em conceito
			 addBehaviour(new Dam_Management_water());
		 }
		 else{
			 doDelete();
			 System.out.println("usage Dam <name><min water necessary>");
		 }
	}
	protected void takeDown() {
		
	}
	
	public class Dam_Management_water extends CyclicBehaviour{
		private String state="receive";
		private float reward=0;
		private float best_x=0;
		@Override
		public void action() {
			
			
			ACLMessage answer = receive();
			if (answer != null) {
				System.out.println(answer.getContent());
				x1=Float.parseFloat(answer.getContent().split(" ")[0]);
				Q1=Float.parseFloat(answer.getContent().split(" ")[1]);
				sumob=Float.parseFloat(answer.getContent().split(" ")[2]);
				sumpen=Float.parseFloat(answer.getContent().split(" ")[3]);
				
				if(Float.parseFloat(answer.getContent().split(" ")[3])>reward){
					reward=Float.parseFloat(answer.getContent().split(" ")[4]);
					best_x=x2;
					x2++;
				}
				state="send";
			}
			else{
				block();
			}
			
			if(state.equals("send")){
				/*
			//System.out.println("enviar");
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("Farm2", AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setOntology("Value sharing");
			msg.setContent(""+x2);
			send(msg);
			
			ACLMessage msg2 = new ACLMessage(ACLMessage.INFORM);
			msg2.addReceiver(new AID("Eco2", AID.ISLOCALNAME));
			msg2.setLanguage("English");
			msg2.setOntology("Value sharing");
			msg2.setContent(""+x2+" "+(fobjective()+sumob)+" "+(fpenalty()+sumpen));
			send(msg2);
			*/
			
			state="receive";
			}
			
		}
		
		
	}
	
	public float fobjective(){
		return a2*x2*x2+b2*x2+c2;
	}
	public float fpenalty(){
		float res=0;
		if((x2-S-Q1 + x1)<=0){
			return res;
		}
		else{
			
			
			res+=1000*(x2-S-Q1+x1+1);
			return res;
		}
		
	
	}
	
	
}
