import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class City extends Agent{
	private AID id;
	private String name;
	private float Q1,minC,minD,x1,a1,b1,c1; //minC is water withdrawn for the city,minD is minimum water for dam, a,b,c are constants of the problem
	protected void setup(){
		
		Object[] args = getArguments();
		 if (args != null && args.length > 0){
			 name=(String)args[0];
			 id = new AID("City"+name, AID.ISLOCALNAME);
			 
			 
			 minC=(float)args[1];
			 x1=minC;
			 minD=(float)args[2];
			 a1=(float)args[3];
			 b1=(float)args[4];
			 c1=(float)args[5];
			 Q1=(float)args[6];
			 
			
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
		private String state="start";

		@Override
		public void action() {
			// TODO Auto-generated method stub
			// envia-a o 
			//sending x1 and F so it is possible to continue shared problem and calculate the Reward from sum of penalty function and sum of fobjectives
			/*  R =
			SUM i=1 até X=6 de fi(xi) 
			-
			SUM j=1 até X=N de C(|hj + 1|lj )*/
			
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("Dam", AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setOntology("Value sharing");
			msg.setContent(""+x1+" "+fobjectivecity()+" "+fpenalty());
			send(msg);
			ACLMessage answer = myAgent.receive();
			//on reception from Eco2 should restart and increment x1
			if (answer != null) {
				if(state.equals("start")){
					
				if(answer.getContent().equals("1")){
					state="start";
					x1+=0.1;
				}
				else{
					state="stop";
				}
				}
				else{
					
				}
			}
			else{
				block();
			}
			

			
		}
		
		
	}
	
	public float fobjectivecity(){
		return a1*x1*x1+b1*x1+c1;
	}
	public float fpenalty(){
		float res=0;
		if(minC-x1 <=0 && minD-Q1+x1<=0){
			return res;
		}
		else{
			if(minC-x1>0){
				res=1000*minC-x1;
			}
			if(minD-Q1+x1>0){
				res+=100*(minD-Q1+x1);
			}
			return res;
		}
		
	
	}
	
	
}
