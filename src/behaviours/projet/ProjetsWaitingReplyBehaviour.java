package behaviours.projet;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Behaviour attendant une réponse à sa request
 * 
 * @author Nicolas
 *
 */
public class ProjetsWaitingReplyBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;
	private int step = 0;
	private String conversationId;
	
	public ProjetsWaitingReplyBehaviour(String conversationId) {
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
