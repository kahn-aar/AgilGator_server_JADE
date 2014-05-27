package behaviours.bdd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Messages.BDDAnwserMessage;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Behaviour gérant les requêtes en "select"
 * 
 * @author Nicolas
 *
 */
public class BDDLunchSelectRequestBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private String conversationId;
	private String query;
	private AID receiver;
	
	public BDDLunchSelectRequestBehaviour(String conversationId, String query, AID replyTo) {
		this.conversationId = conversationId;
		this.query = query;
		this.receiver = replyTo;
	}
	
	@Override
	public void action() {
		System.out.println("BDD reçu = " + query);
		
	}
	
	private ACLMessage createMessage() {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(receiver);
		message.setContent(writeContent());
		message.setConversationId(conversationId);
		
		return message;
	}
	
	private String writeContent() {
		BDDAnwserMessage corps = new BDDAnwserMessage();
		
		ObjectMapper omap = new ObjectMapper();
		String messageCorps = null;
		try {
			messageCorps = omap.writeValueAsString(corps);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return messageCorps;
	}
	
	private Object select() {
		return null;
	}

}
