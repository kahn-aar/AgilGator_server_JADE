package Agents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Messages.SynchroMessage;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Agent de coordination sur le serveur,
 * toutes les informations transitent par lui.
 * 
 * @author Nicolas
 *
 */
public class ServeurAgent extends Agent {

	@Override
	public void setup() {
		super.setup();
		
		//Ajout du behaviour de base
		this.addBehaviour(new ReceptionistBehaviour());
		
		//Enregistrement de l'agent auprès du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Serveur");
		sd.setName("Serveur " + getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	
	/**
	 * Ce behaviour attend et lit les messages reçus, et définit la chose à faire
	 * 
	 * @author Nicolas
	 *
	 */
	public class ReceptionistBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE));
			if (message != null) {
				System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
			}
			
		}
		
	}
	
	/**
	 * Ce behaviour envoie une requete de synchronisation
	 * 
	 * @author Nicolas
	 *
	 */
	public class SynchronistRequestBehaviour extends OneShotBehaviour {

		private String conversationId;
		private int userId;
		private int timeStamp;
		
		public SynchronistRequestBehaviour(int userId, int timeStamp) {
			this.userId = userId;
			this.timeStamp = timeStamp;
			conversationId = userId + "sync"+ timeStamp;
		}
		
		@Override
		public void action() {
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.setContent(writeMessage());
			message.setConversationId(conversationId);
			//Envoi de message vers Sync
		}
		
		private String writeMessage() {
			// Séréalisation JSON
			SynchroMessage corps = new SynchroMessage();
			corps.setUserId(userId);
			corps.setTimeStampLast(timeStamp);
			
			ObjectMapper omap = new ObjectMapper();
			String messageCorps = null;
			try {
				messageCorps = omap.writeValueAsString(corps);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			return messageCorps;
		}
		
	}
	
}
