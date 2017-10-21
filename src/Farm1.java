import jade.core.AID;
import jade.core.Agent;
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
			
			 
			 
			 addBehaviour(new Farm_Management_water());
		 }
		 else{
			 doDelete();
		 }
	}
	protected void takeDown() {
		
	}
	
	public class Farm_Management_water extends CyclicBehaviour{
		private String state="receive";
		private float best_x=0;
		private float reward=0;
		@Override
		public void action() {
			
			if(state.equals("receive")){
			ACLMessage answer = receive();
			if (answer != null) {
				if(Float.parseFloat(answer.getContent())>reward){
				best_x=x4;
				reward=Float.parseFloat(answer.getContent());
				}
				x4++;
				state="send";
			}
			else{
				block();
			}
			}
			if(state.equals("send")){
				
			System.out.println("enviar");
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("Eco1", AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setOntology("Value sharing");
			msg.setContent(""+x4+" "+minT+" "+Q2+" "+ fobjective()+" "+fpenalty()+" "+reward);
			send(msg);
			
			state="receive";
			}
			
		}
			

			
		
		
	}
	public float fobjective(){
		return a4*x4*x4+b4*x4+c4;
	}
	public float fpenalty(){
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