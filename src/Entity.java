import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;


public class Entity extends Agent{
	private String name;
	private int water_taking;
	protected void setup(){
		
		Object[] args = getArguments();
		 if (args != null && args.length > 0){
			
			 this.name=(String)args[0];
			 this.water_taking=(int)args[1];
			 
			 addBehaviour(new Management_water());
		 }
		 else{
			 doDelete();
		 }
	}
	protected void takeDown() {
		
	}
	public class Management_water extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
		}
		
		
	}

}
