package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class City extends Agent{
	private AID id;
	
	private int loop_counter=0;
	private float Q1,minC,minD,x1,a1,b1,c1; //minC is water withdrawn for the city,minD is minimum water for dam, a,b,c are constants of the problem
	protected void setup(){
		
		Object[] args = getArguments();
		 if (args != null && args.length > 0){
			// System.out.println(args.length);
			 
			 id = new AID("City", AID.ISLOCALNAME);
			 
			 
			 minC=Float.parseFloat((String) args[0]);
			 
			 x1=0;
			 minD=Float.parseFloat((String)args[1]);
			 a1=Float.parseFloat((String)args[2]);
			 b1=Float.parseFloat((String)args[3]);
			 c1=Float.parseFloat((String)args[4]);
			 Q1=Float.parseFloat((String)args[5]);
			 
			
			 
			 addBehaviour(new City_Management_water());
			 
		 }
		 else{
			 doDelete();
			 System.out.println("usage City <name><min water necessary>");
		 }
	}
	protected void takeDown() {
		
	}
	
	public class City_Management_water extends Behaviour{
		private String state="send";
		private float first_x=0;
		private float second_x=0;
		private int loop_counter=1;
		private float best_x=x1;
		private float reward=0;
		@Override
		public void action() {
			
			// TODO Auto-generated method stub
			// envia-a o 
			//sending x1 and F so it is possible to continue shared problem and calculate the Reward from sum of penalty function and sum of fobjectives
			/*  R =
			SUM i=1 até X=6 de fi(xi) 
			-
			SUM j=1 até X=N de C(|hj + 1|lj )*/
			
			
			if(state.equals("receive")){
				ACLMessage answer = receive();
				//on reception from Eco2 should restart and increment x1
				if (answer != null) {
					
					
					
					if(loop_counter==1){
						best_x=x1;
						reward=Float.parseFloat(answer.getContent());
					}
						
						loop_counter++;
					//System.out.println("reward atual:"+reward+" reward nova:"+Float.parseFloat(answer.getContent()));
					if(Float.parseFloat(answer.getContent())>reward){
						best_x=x1;
						reward=Float.parseFloat(answer.getContent());
						//System.out.println("atualizei o melhor x");
					}
					
					if((fobjective(second_x)-fpenalty(second_x))>=(fobjective(first_x)-fpenalty(first_x)))
						{
						first_x=x1;
						x1+=0.1;
						second_x=x1;
						}
					
					state="send";
					
					/*answer.getContent()
				
					if()*/
				}
				else{
					//System.out.println("à espera de receber");
					block();
				}
				

				}

			
			
			if(state.equals("send")){
				//System.out.println("a enviar");
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("Dam", AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setOntology("Value sharing");
			msg.setContent(x1+" "+Q1+" "+fobjective(x1)+" "+fpenalty(x1)+" "+reward);
			send(msg);
			
			ACLMessage msg2 = new ACLMessage(ACLMessage.INFORM);
			msg2.addReceiver(new AID("Farm1", AID.ISLOCALNAME));
			msg2.setLanguage("English");
			msg2.setOntology("Value sharing");
			msg2.setContent(reward+"");
			send(msg2);
			
			
			
			
			state="receive";
			}
					}

		@Override
		public boolean done() {
			if(loop_counter==10000){
			System.out.println("terminou melhor solução x1:"+best_x+"solução obejctivo:");
			return true;
			}
			else return false;
		}
		
		
	}
	
	public float fobjective(float x1){
		return a1*x1*x1+b1*x1+c1;
	}
	public float fpenalty(float x1){
		float res=0;
		if(minC-x1 <=0 && minD-Q1+x1<=0){
			return res;
		}
		else{
			if(minC-x1>0){
				res=1000*(minC-x1+1);
			}
			if(minD-Q1+x1>0){
				res+=1000*(minD-Q1+x1+1);
			}
			return res;
		}
		
	
	}
	
	
}
