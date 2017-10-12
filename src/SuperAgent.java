import java.util.HashMap;
import java.util.Map;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;


public class SuperAgent extends Agent {
private float a1,b1,c1,a2,b2,c2,a3,b3,c3,a4,b4,c4,a5,b5,c5,a6,b6,c6;
private Map<String,Float> scenario= new HashMap<String, Float>();
protected void setup(){
	//catalogar os scenarios para depois aceder às variaveis facilmente
	scenario.put("Q1S1", (float) 160);
	scenario.put("Q2S1", (float) 65);
	scenario.put("SS1", (float) 15);
	scenario.put("Q1S2", (float) 115);
	scenario.put("Q2S2", (float) 50);
	scenario.put("SS2", (float) 12);
	scenario.put("Q1S3", (float) 80);
	scenario.put("Q2S3", (float) 35);
	scenario.put("SS3", (float) 10);
	
	addBehaviour(new Water_Management());
	
}
public class Water_Management extends CyclicBehaviour{

	@Override
	public void action() {
		 ACLMessage msg = myAgent.receive();
		 if (msg != null) {
			 
			 float value= Integer.parseInt(msg.getContent());
			 String var=msg.getOntology();
			 switch(var){
			 case "x1":
				 break;
			 case "x2":
				 break;
			 case "x3":
				 break;
			 case "x4":
				 break;
				 
			 }
			
		 }
		 else{
			 block();
		 }

		
	}
	}

public float fobjectivecity(float x1){
	return a1*x1*x1+b1*x1+c1;
}
public float fobjectiveFarm(float x2){
	return a2*x2*x2+b2*x2+c2;
}
public float fobjective(float x3){
	return a3*x3*x3+b3*x3+c3;
}
}
