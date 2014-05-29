package behaviours.synchro;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

import Datas.Constantes.ConstantesTables;
import Messages.BDDAnswerMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Ce behaviour s'occupe d'une demande à la base de données.
 * Il envoie les messages, puis attend les réponses
 * 
 * @author Nicolas
 *
 */
public class SynchroTransactionToBDDBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;
	private int step;
	private String conversationId;
	private int userId;
	private int timeStamp;
	private int nbMessages;
	private List<String> updates = new ArrayList<>();
	
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
			ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchConversationId(conversationId)), MessageTemplate.MatchSender(getServeurAgent())));
			if (message != null) {
				//On récupère la réponse de la BDD, et on l'enregistre
				ObjectMapper omap = new ObjectMapper();
				BDDAnswerMessage msg = null;
				try {
					msg = omap.readValue(message.getContent(), BDDAnswerMessage.class);
				}
				catch (Exception e) {
					
				}
				
				//Transformation de la réponse de la BDD en liste de requete pour le device
				for (String aRequest : msg.getResults()) {
					String[] eachValues = aRequest.split(" ");
					StringBuilder update = new StringBuilder("UPDATE ")
											.append(msg.getTable())
											.append(" SET ");
					for(String value : eachValues) {
						update.append(value).append(" ");
					}
					update.append(";");
					updates.add(update.toString());
					
				}
				
				
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
				"FROM " + ConstantesTables.SPRINT +
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
	
	private AID getServeurAgent() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Serveur");
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
