package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.tools.sniffer.Message;
import Messages.BDDRequestMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BDDWaitingRequestBehaviour extends CyclicBehaviour {
	/**
	 * Behaviour de base, attends les requêtes
	 * 
	 */
	private static final long serialVersionUID = 1L;


		@Override
		public void action() {
			ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			if (message != null) {
				// Déséréalisation JSON
				ObjectMapper omap = new ObjectMapper();
				BDDRequestMessage msg = null;
				try {
					msg = omap.readValue(message.getContent(), BDDRequestMessage.class);
				}
				catch (Exception e) {
					
				}
				
				switch(msg.getType()) {
					case INSERT:
						myAgent.addBehaviour(new BDDLunchUpdateRequestBehaviour(message.getConversationId(), msg.getRequest()));
						break;
					case SELECT:
						myAgent.addBehaviour(new BDDLunchSelectRequestBehaviour(message.getConversationId(), msg.getRequest(), message.getSender()));
						break;
					case UPDATE:
						break;
					default:
						break;
				
				}
				
			}
		}
		
	}

