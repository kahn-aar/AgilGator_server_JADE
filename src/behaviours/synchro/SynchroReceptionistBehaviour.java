package behaviours.synchro;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Messages.SynchroMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Ce behaviour attend et lit les messages re�us, et transmet la requ�te � la BDD
 * Il g�n�re de plus le behaviour qui s'occupe d'une transaction.
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
			// D�s�r�alisation JSON
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
			
			
			
			myAgent.addBehaviour(new SynchroTransactionToBDDBehaviour(conversationId, userId, timeStamp));
		}
	}
	
}

