package behaviours.serveur;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Messages.ServerLiaisonMessage;

/**
 * Behaviour permettant de transmettre les informations à l'agent de liaison
 * 
 * @author Nicolas
 *
 */
public class ServeurSendToLiaisonBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private String conversationId;
	// Tous les utilisateurs connectés liés au projets
	private List<AID> destinataires;
	private String content;
	
	public ServeurSendToLiaisonBehaviour(String conversationId, List<AID> destinataires, String content) {
		this.conversationId = conversationId;
		this.destinataires = destinataires;
		this.content = content;
	}
	
	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.PROPAGATE);
		message.addReceiver(getLiasonAgent());
		message.setContent(writeMessage());
		message.setConversationId(conversationId);
		myAgent.send(message);
	}
	
	private String writeMessage() {
		ServerLiaisonMessage message = new ServerLiaisonMessage();
		message.setListeDestinataires(destinataires);
		message.setContent(content);
		
		ObjectMapper omap = new ObjectMapper();
		String messageCorps = null;
		try {
			messageCorps = omap.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return messageCorps;
	}
	
	
	
	
	private AID getLiasonAgent() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Liaison");
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
