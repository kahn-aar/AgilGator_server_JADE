package behaviours.projet;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Agents.UtilisateursAgent;
import Datas.Project;
import Datas.Utilisateur;
import Messages.BDDAnswerMessage;
import Messages.ServerLiaisonMessage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jmx.snmp.Timestamp;

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
	List<Utilisateur> connectedUsers = null;
	private Project projet;
	
	public ProjetsWaitingReplyBehaviour(String conversationId, Project projet) {
		this.conversationId = conversationId;
		this.projet = projet;
	}
	
	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchConversationId(conversationId),MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchSender(getBDDAgent()))));
		if (message != null) {
			System.out.println(myAgent.getLocalName() + " reçu lol -> " + message.getContent());
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
							this.connectedUsers = UtilisateursAgent.getUtilisateursConnectés();
							System.out.println("ajout membre");
							this.createMessageMember(answer);
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
		projet.setId(answer.getId());
		sl.setProjet(projet);
		sl.setDemande(answer.getDemande());
		String content ="";
		try {
			if(connectedUsers != null)
				content = omapSL.writeValueAsString(answer.getProject());
			else
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
	
	private void createMessageMember(BDDAnswerMessage answer){
		// ServeurLiaison message model
		ObjectMapper omapSL = new ObjectMapper();
		ServerLiaisonMessage sl = new ServerLiaisonMessage();
		sl.setProjet(projet);
		System.out.println(sl.getProjet().getTitle());
		sl.setDemande(answer.getDemande());
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
