import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;


public class Farm extends Agent{
	private AID id;
	private float a2,b2,c2,x2;
	protected void setup(){
		
		Object[] args = getArguments();
		 if (args != null && args.length > 0){
			 id = new AID("Farm"+(String)args[0], AID.ISLOCALNAME);
			 a2=(float)args[1];
			 b2=(float)args[2];
			 c2=(float)args[3];
			
			 
			 
			 addBehaviour(new Farm_Management_water());
		 }
		 else{
			 doDelete();
		 }
	}
	protected void takeDown() {
		
	}
	
	public class Farm_Management_water extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			

			
		}
		
		
	}
	public float fobjective(float x2){
		return a2*x2*x2+b2*x2+c2;
	}

}