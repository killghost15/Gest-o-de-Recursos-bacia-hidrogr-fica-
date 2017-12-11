package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;



public class Eco1 extends Agent{
	private AID id;
	
	private float x4,x3,minT,a3,b3,c3,Q2,sumob,sumpen; //x1 is water withdrawn for the city
	protected void setup(){
		
		Object[] args = getArguments();
		 if (args != null && args.length > 0){
			 
			 id = new AID("Dam", AID.ISLOCALNAME);
			 
			 
			a3=Float.parseFloat((String)args[0]);
			b3=Float.parseFloat((String)args[1]);
			c3=Float.parseFloat((String)args[2]);
			
			 
			
			 //n está operacional ainda, só em conceito
			 addBehaviour(new Eco_Management_water());
		 }
		 else{
			 doDelete();
			 System.out.println("usage Dam <name><min water necessary>");
		 }
	}
	protected void takeDown() {
		
	}
	
	public class Eco_Management_water extends Behaviour{
		private String state="receive";
		private float reward=0;
		private float best_x=0;
		private int loop_counter=1;
		@Override
		public void action() {
			
			if(state.equals("receive")){
			ACLMessage answer = receive();
			if (answer != null) {
				//System.out.println(answer.getContent());
				
				//parsing the information that comes from farm1
				x4=Float.parseFloat(answer.getContent().split(" ")[0]);
				minT=Float.parseFloat(answer.getContent().split(" ")[1]);
				Q2=Float.parseFloat(answer.getContent().split(" ")[2]);
				sumob=Float.parseFloat(answer.getContent().split(" ")[3]);
				sumpen=Float.parseFloat(answer.getContent().split(" ")[4]);
				//x3 is dependent on other variables:
				x3=Q2-x4;
				if(loop_counter==2){
					reward=Float.parseFloat(answer.getContent().split(" ")[5]);
					best_x=x3;
				}
				
				if(Float.parseFloat(answer.getContent().split(" ")[5])>reward){
					reward=Float.parseFloat(answer.getContent().split(" ")[5]);
					best_x=x3;
				}
				state="send";
			}
			else{
				block();
			}
			}
			if(state.equals("send")){
				
			//System.out.println("enviarEco1");
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("Farm2", AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setOntology("Value sharing");
			msg.setContent(""+x3+" "+(fobjective()+sumob)+" "+(fpenalty()+sumpen)+" "+reward);
			send(msg);
			
			
			
			loop_counter++;
			state="receive";
			}
			
		}
		public boolean done() {
			if(loop_counter==10000){
			System.out.println("terminou melhor solução x3:"+best_x+"solução obejctivo:");
			return true;
			}
			else return false;
		}
		
		
	}
	
	public float fobjective(){
		return a3*x3*x3+b3*x3+c3;
	}
	public float fpenalty(){
		float res=0;
		if((minT-x3)<=0){
			return res;
		}
		else{
			
			
			res+=1000*(minT-x3+1);
			return res;
		}
		
	
	}

}
