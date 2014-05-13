package Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

/**
 * Agent gérant la base de données. Lui seul en a l'accès.
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
	 * Behaviour de base, attends les requêtes
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
	 * Behaviour lançant une requête sur la base de données, qui n'attend pas de 
	 * résultats en retour, uniquement un message de succès ou de fail
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
	 * Behaviour lançant une requête sur la base de données, qui attends des 
	 * résultats en retour.
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
