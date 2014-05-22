package behaviours;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Datas.Constantes.ConstantesTables;

/**
 * Ce behaviour s'occupe d'une demande à la base de données.
 * Il envoie les messages, puis attend les réponses
 * 
 * @author Nicolas
 *
 */
public class SynchroTransactionToBDDBehaviour extends Behaviour {

	private int step;
	private String conversationId;
	private int userId;
	private int timeStamp;
	private int nbMessages;
	
	public SynchroTransactionToBDDBehaviour(String conversationId2, int userId2, int timeStamp2) {
		this.conversationId = conversationId2;
		this.userId = userId2;
		this.timeStamp = timeStamp2;
		this.step = 0;
	}
	
	@Override
	public void action() {
		// Step 0 => envoi des messages
		if(step == 0) {
			myAgent.addBehaviour(new SynchroSendRequestBehaviour(conversationId, writeRequest()));
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
