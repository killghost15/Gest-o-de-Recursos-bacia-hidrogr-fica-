import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;



public class Eco2 extends Agent{
private AID id;
	
	private float minM,x6,x2,x5,x3,a5,b5,c5,sumob,sumpen,sumob2,sumpen2; //x1 is water withdrawn for the city
	protected void setup(){
		
		Object[] args = getArguments();
		 if (args != null && args.length > 0){
			 
			id = new AID("Dam", AID.ISLOCALNAME);
			
			a5=Float.parseFloat((String)args[0]);
			b5=Float.parseFloat((String)args[1]);
			c5=Float.parseFloat((String)args[2]);
			
			 
			
			 //n está operacional ainda, só em conceito
			 addBehaviour(new Eco2_Management_water());
		 }
		 else{
			 doDelete();
			 System.out.println("usage Dam <name><min water necessary>");
		 }
	}
	protected void takeDown() {
		
	}
	
	public class Eco2_Management_water extends CyclicBehaviour{
		private String state="receive1";
		private float reward=0;
		private float best_x=0;
		@Override
		public void action() {
			
			if(state.equals("receive1")){
			ACLMessage answer = receive();
			if (answer != null) {
				System.out.println(answer.getContent());
				
				//parsing the information that comes from farm1
				x2=Float.parseFloat(answer.getContent().split(" ")[0]);
				sumob=Float.parseFloat(answer.getContent().split(" ")[1]);
				sumpen=Float.parseFloat(answer.getContent().split(" ")[2]);
				state="receive2";
			}
			else{
				block();
			}
			
			}
			
			if(state.equals("receive2")){ 
				ACLMessage answer2 = receive();
				if (answer2 != null) {
					System.out.println(answer2.getContent());
					
					//parsing the information that comes from farm1
					x6=Float.parseFloat(answer2.getContent().split(" ")[0]);
					x3=Float.parseFloat(answer2.getContent().split(" ")[1]);
					sumob2=Float.parseFloat(answer2.getContent().split(" ")[2]);
					sumpen2=Float.parseFloat(answer2.getContent().split(" ")[3]);
					//x3 is dependent on other variables:
					state="send";
				}
				else{
					block();
				}
				
			}
			if(state.equals("send")){
				x5=x2+x3-x6;
			//System.out.println("enviar");
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("City", AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setOntology("Value sharing");
			msg.setContent(""+RewardFunction());
			send(msg);
			
			
			
			
			state="receive1";
			}
			
		}
		
		
	}
	public float RewardFunction(){
		return (sumob+sumob2+fobjective())-(sumpen+sumpen2+fpenalty());
		
		
	}
	
	public float fobjective(){
		return a5*x5*x5+b5*x5+c5;
	}
	public float fpenalty(){
		float res=0;
		if((minM-x5)<=0){
			return res;
		}
		else{
			
			
			res+=1000*(minM-x5+1);
			return res;
		}
		
	
	}
	

}
