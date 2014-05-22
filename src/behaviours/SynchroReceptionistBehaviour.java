package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Messages.SynchroMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Ce behaviour attend et lit les messages reçus, et transmet la requête à la BDD
 * 
 * @author Nicolas
 *
 */
public class SynchroReceptionistBehaviour extends CyclicBehaviour {

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
			
			
		}
	}
	
	private String writeRequest() {
		//On va écrire toutes les requêtes possibles sur la BDD.
		return null;
	}
	
}

