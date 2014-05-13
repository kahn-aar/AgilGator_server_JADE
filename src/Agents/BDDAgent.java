package Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

/**
 * Agent g�rant la base de donn�es. Lui seul en a l'acc�s.
 * 
 * @author Nicolas
 *
 */
public class BDDAgent extends Agent {
	
	@Override
	public void setup() {
		super.setup();
		
	}
	
	/**
	 * Behaviour de base, attends les requ�tes
	 * 
	 * @author Nicolas
	 *
	 */
	private class WaitingRequestBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			
		}
		
	}
	
	/** 
	 * Behaviour lan�ant une requ�te sur la base de donn�es, qui n'attend pas de 
	 * r�sultats en retour, uniquement un message de succ�s ou de fail
	 * 
	 * @author Nicolas
	 *
	 */
	private class LunchUpdateRequestBehaviour extends OneShotBehaviour {

		private int conversationId;
		
		@Override
		public void action() {
			
		}
		
	}
	
	/**
	 * Behaviour lan�ant une requ�te sur la base de donn�es, qui attends des 
	 * r�sultats en retour.
	 * 
	 * @author Nicolas
	 *
	 */
	private class LunchRequestBehaviour extends OneShotBehaviour {

		private int conversationId;
		
		@Override
		public void action() {
			
		}
		
	}
}
