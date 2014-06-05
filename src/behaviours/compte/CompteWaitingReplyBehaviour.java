package behaviours.compte;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Agents.UtilisateursAgent;
import Datas.Utilisateur;
import Datas.Constantes.ConstantesTables;
import Datas.enums.DeviceInfoTypes;
import Messages.BDDAnswerMessage;
import Messages.CompteMessage;
import Messages.ServerLiaisonMessage;
import Messages.UserListMessage;

import com.fasterxml.jackson.core.JsonParseException;
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
public class CompteWaitingReplyBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;
	private int step = 0;
	private String conversationId;
	
	public CompteWaitingReplyBehaviour(String conversationId) {
		this.conversationId = conversationId;
	}
	
	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchConversationId(conversationId), MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchSender(getBDDAgent()))));
		if (message != null) {
			System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
			// Il récupère le résultat de la requête.
			ObjectMapper omap = new ObjectMapper();
			try {
				BDDAnswerMessage answer = omap.readValue(message.getContent(),BDDAnswerMessage.class);
				if(answer !=null){
					switch(answer.getDemande()){
						case CREE_COMPTE:
							// ServeurLiaison message model
							ObjectMapper omapSL = new ObjectMapper();
							ServerLiaisonMessage sl = new ServerLiaisonMessage();
							sl.setContent(String.valueOf(answer.getId()));
							String content = omapSL.writeValueAsString(sl);
							// Ecriture du message
							ACLMessage reply = new ACLMessage(ACLMessage.CONFIRM);
							reply.addReceiver(getServeurAgent());
							reply.setContent(content);
							reply.setConversationId(conversationId);
							myAgent.send(reply);
							break;
						case IS_USER:
							// On vérifie que l'email de l'utilisateur n'existe pas
							Boolean fly = true;
							for(Utilisateur u : answer.getMesUsers()){
								if (u.getEmail().equals(answer.getUser().getEmail())){
									fly=false;
								}
							}
							if(fly){
								myAgent.addBehaviour(new CompteSendingRequestBehaviour(conversationId,DeviceInfoTypes.CREE_COMPTE, answer.getUser(), false));
							}
							else{
								System.out.println("Le compte existe déjà !");
							};
							break;
							
						case ALL_USERS:
							ACLMessage reply2 = new ACLMessage(ACLMessage.CONFIRM);
							reply2.addReceiver(getServeurAgent());
							reply2.setConversationId(conversationId);
							myAgent.send(reply2);
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
