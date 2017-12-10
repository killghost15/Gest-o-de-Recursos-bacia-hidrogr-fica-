package agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;



public class Farm2 extends Agent{
	private AID id;
	
	private float minM,x3,minF2,x6,x2,a6,b6,c6,sumob,sumpen; //x1 is water withdrawn for the city
	protected void setup(){
		
		Object[] args = getArguments();
		 if (args != null && args.length > 0){
			 
			id = new AID("Dam", AID.ISLOCALNAME);
			x6=0;
			minF2=Float.parseFloat((String)args[0]);
			minM=Float.parseFloat((String)args[1]);
			a6=Float.parseFloat((String)args[0]);
			b6=Float.parseFloat((String)args[1]);
			c6=Float.parseFloat((String)args[2]);
			
			 
			
			 //n está operacional ainda, só em conceito
			 addBehaviour(new Farm2_Management_water());
		 }
		 else{
			 doDelete();
			 System.out.println("usage Dam <name><min water necessary>");
		 }
	}
	protected void takeDown() {
		
	}
	
	public class Farm2_Management_water extends Behaviour{
		private String state="receive1";
		private float reward=0;
		private float best_x=0;
		private int loop_counter=1;
		private float first_x=x6;
		private float second_x=x6;
		@Override
		public void action() {
			
			if(state.equals("receive1")){
			ACLMessage answer = receive();
			if (answer != null) {
				//System.out.println(answer.getContent());
				
				//parsing the information that comes from farm1
				x2=Float.parseFloat(answer.getContent().split(" ")[0]);
				
				state="receive2";
			}
			else{
				block();
			}
			
			}
			
			if(state.equals("receive2")){ 
				ACLMessage answer2 = receive();
				if (answer2 != null) {
					//System.out.println(answer2.getContent());
					//System.out.println("loop:"+loop_counter);
					//parsing the information that comes from farm1
					x3=Float.parseFloat(answer2.getContent().split(" ")[0]);
					sumob=Float.parseFloat(answer2.getContent().split(" ")[1]);
					sumpen=Float.parseFloat(answer2.getContent().split(" ")[2]);
					//x3 is dependent on other variables:
					if(Float.parseFloat(answer2.getContent().split(" ")[3])>reward){
						reward=Float.parseFloat(answer2.getContent().split(" ")[3]);
						best_x=x6;
						
					}
					state="send";
				}
				else{
					block();
				}
				
			}
			if(state.equals("send")){
				
			//System.out.println("enviar");
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("Eco2", AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setOntology("Value sharing");
			msg.setContent(""+x6+" "+x3+" "+(fobjective(x6)+sumob)+" "+(fpenalty(x6)+sumpen));
			send(msg);
			
			
			
			if((fobjective(second_x)-fpenalty(second_x))>=(fobjective(first_x)-fpenalty(first_x)))
			{
				first_x=x6;
				x6++;
				second_x=x6;
			}
			loop_counter++;
			state="receive1";
			}
			
		}
		public boolean done() {
			if(loop_counter==100){
			System.out.println("terminou melhor solução x6:"+best_x+"solução obejctivo:");
			return loop_counter==100;
			}
			else return false;
		}
		
		
	}
	
	public float fobjective(float x6){
		return a6*x6*x6+b6*x6+c6;
	}
	public float fpenalty(float x6){
		float res=0;
		if(minF2-x6 <=0 && minM-x2-x3+x6<=0){
			return res;
		}
		else{
			if(minF2-x6>0){
				res=1000*(minF2-x6+1);
			}
			if(minM-x2-x3+x6>0){
				res+=1000*(minM-x2-x3+x6+1);
			}
			return res;
		}
		}
}
