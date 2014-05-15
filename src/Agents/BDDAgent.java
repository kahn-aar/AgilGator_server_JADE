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
 * Agent g�rant la base de donn�es. Lui seul en a l'acc�s.
 * 
 * @author Nicolas
 *
 */
public class BDDAgent extends Agent {
	
	@Override
	public void setup() {
		super.setup();
		
		this.addBehaviour(new WaitingRequestBehaviour());
		
		//Enregistrement de l'agent aupr�s du DF
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
	 * Behaviour de base, attends les requ�tes
	 * 
	 * @author Nicolas
	 *
	 */
	private class WaitingRequestBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			if (message != null) {
				// D�s�r�alisation JSON
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
	 * Behaviour lan�ant une requ�te sur la base de donn�es, qui n'attend pas de 
	 * r�sultats en retour, uniquement un message de succ�s ou de fail
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
			System.out.println("BDD re�u = " + query);
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
