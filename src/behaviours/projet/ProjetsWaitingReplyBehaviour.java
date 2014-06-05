package behaviours.projet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Agents.UtilisateursAgent;
import Datas.Utilisateur;
import Datas.Constantes.ConstantesTables;
import Messages.BDDAnswerMessage;
import Messages.ServerLiaisonMessage;
import Messages.UserListMessage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
		ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchConversationId(conversationId),MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchSender(getBDDAgent()))));
		if (message != null) {
			System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
			// Il récupère le résultat de la requête.
			ObjectMapper omap = new ObjectMapper();
			try {
				BDDAnswerMessage answer = omap.readValue(message.getContent(),BDDAnswerMessage.class);
				if(answer !=null){
					switch(answer.getDemande()){
						case CREE_PROJET:
							this.createMessage(answer);
							break;
						case MODIFIE_PROJET:
							this.createMessage(answer);
							break;
						case AJOUT_MANAGER:
							this.createMessage(answer);
							break;
						case AJOUT_MEMBRE:
							this.createMessage(answer);
							break;
						case RETRAIT_MEMBRE:
							this.createMessage(answer);
							break;
						case EFFACE_PROJET:
							this.createMessage(answer);
							break;
						case MEMBRES_DU_PROJET:
							// Récupère la liste des utilisateurs du projet
							List<Utilisateur> userListResult = answer.getMesUsers();
							myAgent.addBehaviour(new ProjetsSendingUserListBehaviour(userListResult, conversationId));
							break;
						default:
							break;
							
					}
				}
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void createMessage(BDDAnswerMessage answer){
		// ServeurLiaison message model
		ObjectMapper omapSL = new ObjectMapper();
		ServerLiaisonMessage sl = new ServerLiaisonMessage();
		sl.setContent(String.valueOf(answer.getId()));
		String content ="";
		try {
			content = omapSL.writeValueAsString(sl);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Ecriture du message
		ACLMessage reply = new ACLMessage(ACLMessage.CONFIRM);
		reply.addReceiver(getServeurAgent());
		reply.setContent(content);
		reply.setConversationId(conversationId);
		myAgent.send(reply);
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
