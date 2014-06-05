package behaviours.liaison;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.List;

import Messages.ServerLiaisonMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LiaisonWaitingServeurMessageBehaviour extends CyclicBehaviour{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		ACLMessage messageServeur = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE), MessageTemplate.MatchSender(getServerAID())));
		if (messageServeur != null) {
			System.out.println(myAgent.getLocalName() + " reçu -> " + messageServeur.getContent());
			// On déséréalise le message
			ObjectMapper omap = new ObjectMapper();
			List<AID> destinataires = null;
			String content = null;
			try {
				ServerLiaisonMessage msg = omap.readValue(messageServeur.getContent(),ServerLiaisonMessage.class);
				destinataires = msg.getListeDestinataires();
				content = msg.getContent();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			// Ecriture de message
			myAgent.send(createMessageToDevices(destinataires, content, messageServeur.getConversationId()));
		}
	}
	
	private ACLMessage createMessageToDevices(List<AID> destinataires, String content, String conversationId) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		if(destinataires!=null){
			for(AID destinataire : destinataires) {
				message.addReceiver(destinataire);
			}
		}
		// Ajout du contenu du message.
		message.setConversationId(conversationId);
		message.setContent(content);
		return message;
	}
	
	private AID getServerAID() {
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
