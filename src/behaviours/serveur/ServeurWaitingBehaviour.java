package behaviours.serveur;

import java.util.List;

import behaviours.compte.CompteSendingRequestBehaviour;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Attens les retours de projet, tache, sous-tâche, compte, sprint
 * @author Léa
 *
 */
public class ServeurWaitingBehaviour extends CyclicBehaviour{

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
		if(message!=null){
			System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
			String content = message.getContent();
			String conversationId = message.getConversationId();
			// A CORRIGER !!
			List<AID> destinataires = null;
			myAgent.addBehaviour(new ServeurSendToLiaisonBehaviour(conversationId, destinataires, content));
		}
		
	}

}
