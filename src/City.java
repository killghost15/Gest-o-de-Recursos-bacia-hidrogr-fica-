import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class City extends Agent{
	private AID id;
	private String name;
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
	
	public class City_Management_water extends Behaviour{
		private String state="send";
		private int loop_counter=0;
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
					
					loop_counter++;
					if(Float.parseFloat(answer.getContent())>reward){
						best_x=x1;
						reward=Float.parseFloat(answer.getContent());
					}
					x1++;
					
					state="send";
					
					/*answer.getContent()
				
					if()*/
				}
				else{
					System.out.println("à espera de receber");
					block();
				}
				

				}

			
			
			if(state.equals("send")){
				System.out.println("a enviar");
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("Dam", AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setOntology("Value sharing");
			msg.setContent(x1+" "+Q1+" "+fobjective()+" "+fpenalty()+" "+reward);
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
			if(loop_counter==100){
			System.out.println("terminou melhor solução x1:"+best_x+"solução obejctivo:");
			return loop_counter==100;
			}
			else return false;
		}
		
		
	}
	
	public float fobjective(){
		return a1*x1*x1+b1*x1+c1;
	}
	public float fpenalty(){
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
