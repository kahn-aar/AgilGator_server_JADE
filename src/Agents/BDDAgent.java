package Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Messages.RequestMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		
		this.addBehaviour(new WaitingRequestBehaviour());
		
		//Enregistrement de l'agent auprès du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("BDD");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
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
			ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			if (message != null) {
				// Déséréalisation JSON
				ObjectMapper omap = new ObjectMapper();
				RequestMessage msg = null;
				try {
					msg = omap.readValue(message.getContent(), RequestMessage.class);
				}
				catch (Exception e) {
					
				}
				
				switch(msg.getType()) {
					case INSERT:
						break;
					case SELECT:
						myAgent.addBehaviour(new LunchUpdateRequestBehaviour(message.getConversationId(), msg.getRequest()));
						break;
					case UPDATE:
						break;
					default:
						break;
				
				}
				
			}
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

		private String conversationId;
		private String query;
		
		public LunchUpdateRequestBehaviour(String conversationId, String query) {
			this.conversationId = conversationId;
			this.query = query;
		}
		
		@Override
		public void action() {
			System.out.println("BDD reçu = " + query);
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
