package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Messages.RequestMessage;

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
						myAgent.addBehaviour(new BDDLunchUpdateRequestBehaviour(message.getConversationId(), msg.getRequest()));
						break;
					case UPDATE:
						break;
					default:
						break;
				
				}
				
			}
		}
		
	}

