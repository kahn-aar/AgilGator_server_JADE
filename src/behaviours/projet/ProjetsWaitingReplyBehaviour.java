package behaviours.projet;

import java.io.IOException;
import java.util.List;

import Agents.UtilisateursAgent;
import Datas.Utilisateur;
import Messages.UserListMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
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
		ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchConversationId(conversationId)), MessageTemplate.MatchSender(getBDDAgent())));
		if (message != null) {
			// Il récupère le résultat de la requête.
			
			// Récupère la liste des utilisateurs du projets
			System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
			ObjectMapper omap = new ObjectMapper();
			try {
				UserListMessage userListMsg = omap.readValue(message.getContent(),UserListMessage.class);
				List<Utilisateur> userList = userListMsg.getUserList();
				if(userList!=null){
					myAgent.addBehaviour(new ProjetsSendingUserListBehaviour(userList, conversationId));
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public boolean done() {
		if (step == 1) {
			return true;
		}
		return false;
	}
	
	private AID getBDDAgent() {
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
