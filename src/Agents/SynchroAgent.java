package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Datas.Constantes.ConstantesTables;
import Messages.SynchroMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Gère la synchronisation des utilisateurs lorsqu'ils se connectent
 * 
 * @author Nicolas
 *
 */
public class SynchroAgent extends Agent {

	@Override
	public void setup() {
		super.setup();
	}
	
	/**
	 * Ce behaviour attend et lit les messages reçus, et transmet la requête à la BDD
	 * Il génère de plus le behaviour qui s'occupe d'une transaction.
	 * 
	 * @author Nicolas
	 *
	 */
	public class ReceptionistBehaviour extends CyclicBehaviour {

		private String conversationId;
		private int userId;
		private int timeStamp;
		
		@Override
		public void action() {
			ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			if (message != null) {
				// Déséréalisation JSON
				ObjectMapper omap = new ObjectMapper();
				SynchroMessage msg = null;
				try {
					msg = omap.readValue(message.getContent(), SynchroMessage.class);
				}
				catch (Exception e) {
					
				}
				
				conversationId = message.getConversationId();
				userId = msg.getUserId();
				timeStamp = msg.getTimeStampLast();
				
				
				
				myAgent.addBehaviour(new TransactionToBDDBehaviour(conversationId, userId, timeStamp));
			}
		}
		
		
		
	}
	
	/**
	 * Ce behaviour s'occupe d'une demande à la base de données.
	 * Il envoie les messages, puis attend les réponses
	 * 
	 * @author Nicolas
	 *
	 */
	public class TransactionToBDDBehaviour extends Behaviour {

		private int step;
		private String conversationId;
		private int userId;
		private int timeStamp;
		private int nbMessages;
		
		public TransactionToBDDBehaviour(String conversationId2, int userId2, int timeStamp2) {
			this.conversationId = conversationId2;
			this.userId = userId2;
			this.timeStamp = timeStamp2;
			this.step = 0;
		}
		
		@Override
		public void action() {
			// Step 0 => envoi des messages
			if(step == 0) {
				myAgent.addBehaviour(new SendRequestBehaviour(conversationId, writeRequest()));
				nbMessages = 1;
				step++;
			}
			else if (step == 1) {
				//Step 1 => Attente de reception des messages.
				ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchConversationId(conversationId)));
				if (message != null) {
					//On récupère la réponse de la BDD, et on l'enregistre
					nbMessages--;
					
				}
				if (nbMessages == 0) {
					step++;
				}
			}
			else if (step == 2) {
				//Ecriture de la réponse au serveur
				
				step++;
			}
			
		}
		
		private String writeRequest() {
			//On va écrire toutes les requêtes possibles sur la BDD.
			//Tout d'abord le sprint actuel
			String querySprint = "SELECT * " +
					"FROM " + ConstantesTables.TABLE_SPRINT +
					" WHERE " + "dateLastUpdate > " + timeStamp + ";";
			return querySprint;
		}

		@Override
		public boolean done() {
			//On détruit ce behaviour a la fin de son travail
			if (step == 3) {
				return true;
			}
			return false;
		}
		
	}
	
	/**
	 * Ce behaviour attend transmet la requête à la BDD
	 * 
	 * @author Nicolas
	 *
	 */
	public class SendRequestBehaviour extends OneShotBehaviour {

		private String conversationId;
		private String message;
		

		public SendRequestBehaviour(String conversationId2, String message) {
			this.conversationId = conversationId2;
			this.message = message;
		}

		@Override
		public void action() {
			ACLMessage requestSprintToBdd = new ACLMessage(ACLMessage.REQUEST);
			requestSprintToBdd.addReceiver(getBDDAgent());
			requestSprintToBdd.setContent(message);
			requestSprintToBdd.setConversationId(conversationId);
			myAgent.send(requestSprintToBdd);
		}
		
		private AID getBDDAgent() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("BDD");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template);
				return result[0].getName();
			} catch(FIPAException fe) {
				fe.printStackTrace();
			}
			return null;
		}

		
		
	}
	
}
