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
import Datas.RequestTypes;
import Messages.RequestMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjetsAgent extends Agent {

	@Override
	public void setup() {
		super.setup();
		
		this.addBehaviour(new SendingRequestBehaviour(50));
		
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
	 * Behaviour posant des questions à l'agent BDD
	 * 
	 * @author Nicolas
	 *
	 */
	private class SendingRequestBehaviour extends OneShotBehaviour {

		private int projectId;
		private String conversationId;
		
		public SendingRequestBehaviour(int id) {
			this.projectId = id;
			this.conversationId = String.valueOf(id);
			System.out.println("yo projets agent");
		}
		
		@Override
		public void action() {
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(getBddAgent());
			message.setContent(createContent());
			message.setConversationId(conversationId);
			message.setLanguage("JSON");
			myAgent.send(message);
			
			myAgent.addBehaviour(new WaitingReplyBehaviour(conversationId));
		}
		
		private String createContent() {
			RequestMessage message = new RequestMessage();
			message.setRequest(createRequest());
			message.setType(RequestTypes.SELECT);
			
			// Séréalisation JSON
			ObjectMapper omap = new ObjectMapper();
			String messageCorps = null;
			try {
				messageCorps = omap.writeValueAsString(message);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			return messageCorps;
		}
		
		private String createRequest() {
			StringBuilder request = new StringBuilder();
			request.append("SELECT user FROM t_user_projet WHERE projet_id = ")
					.append(projectId)
					.append(";");
			
			return request.toString();
		}
		
		private AID getBddAgent() {
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
	
	
	/**
	 * Behaviour attendant une réponse à sa request
	 * 
	 * @author Nicolas
	 *
	 */
	private class WaitingReplyBehaviour extends Behaviour {

		private int step = 0;
		private String conversationId;
		
		public WaitingReplyBehaviour(String conversationId) {
			this.conversationId = conversationId;
		}
		
		@Override
		public void action() {
			ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchConversationId(conversationId)));
			if (message != null) {
				// Il récupère le résultat de la requête.
			}
		}

		@Override
		public boolean done() {
			if (step == 1) {
				return true;
			}
			return false;
		}
		
	}
	
	
}
