package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class Farm1 extends Agent{
	private AID id;
	private float minF,minT,a4,b4,c4,x4,Q2;
	protected void setup(){
		
		Object[] args = getArguments();
		 if (args != null && args.length > 0){
			 id = new AID("Farm1", AID.ISLOCALNAME);
			 x4=0;
			 minF=Float.parseFloat((String)args[0]);
			 minT=Float.parseFloat((String)args[1]);
			 a4=Float.parseFloat((String)args[2]);
			 b4=Float.parseFloat((String)args[3]);
			 c4=Float.parseFloat((String)args[4]);
			 Q2=Float.parseFloat((String)args[5]);
			
			 
			 
			 addBehaviour(new Farm_Management_water());
		 }
		 else{
			 doDelete();
		 }
	}
	protected void takeDown() {
		
	}
	
	public class Farm_Management_water extends Behaviour{
		private String state="receive";
		private float best_x=0;
		private float reward=0;
		private float first_x=x4;
		private float second_x=x4;
		private int loop_counter=1;
		@Override
		public void action() {
			
			if(state.equals("receive")){
			ACLMessage answer = receive();
			if (answer != null) {
				if(loop_counter==2){
					best_x=x4;
					reward=Float.parseFloat(answer.getContent());
				}
				if(Float.parseFloat(answer.getContent())>reward){
				best_x=x4;
				reward=Float.parseFloat(answer.getContent());
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
			msg.addReceiver(new AID("Eco1", AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setOntology("Value sharing");
			msg.setContent(""+x4+" "+minT+" "+Q2+" "+ fobjective(x4)+" "+fpenalty(x4)+" "+reward);
			send(msg);
			
				
			if((fobjective(second_x)-fpenalty(second_x))>=(fobjective(first_x)-fpenalty(first_x)))
			{
				first_x=x4;
				x4+=0.1;
				second_x=x4;
			}
			loop_counter++;
			state="receive";
			}
			
		}
		public boolean done() {
			if(loop_counter==10000){
			System.out.println("terminou melhor solução x4:"+best_x+"solução obejctivo:");
			return true;
			}
			else return false;
		}
			

			
		
		
	}
	public float fobjective(float x4){
		return a4*x4*x4+b4*x4+c4;
	}
	public float fpenalty(float x4){
		float res=0;
		if(minF-x4 <=0 && minT-Q2+x4<=0){
			return res;
		}
		else{
			if(minF-x4>0){
				res=1000*(minF-x4+1);
			}
			if(minT-Q2+x4>0){
				res+=1000*(minT-Q2+x4+1);
			}
			return res;
		}
		
	
	}

}