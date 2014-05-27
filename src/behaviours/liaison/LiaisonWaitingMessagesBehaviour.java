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

/**
 * Behaviour attendant les messages, que ce soit des devices
 * ou du serveur.
 * 
 * @author Nicolas
 *
 */
public class LiaisonWaitingMessagesBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		ACLMessage messageServeur = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE), MessageTemplate.MatchSender(getServerAID())));
		ACLMessage messageDevice = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		if (messageServeur != null) {
			// On déséréalise le message
			ObjectMapper omap = new ObjectMapper();
			List<AID> destinataires = null;
			try {
				ServerLiaisonMessage msg = omap.readValue(messageServeur.getContent(),ServerLiaisonMessage.class);
				destinataires = msg.getListeDestinataires();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			// Eciture de message
			myAgent.send(createMessageToDevices(destinataires));
		}
		if (messageDevice != null) {
			// Message venant du device
			myAgent.send(createMessageToServeur(messageServeur.getContent()));
			
		}

	}
	
	private ACLMessage createMessageToDevices(List<AID> destinataires) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		for(AID destinataire : destinataires) {
			message.addReceiver(destinataire);
		}
		// Ajout du contenu du message.
		message.setContent("lol");
		return message;
	}
	
	private ACLMessage createMessageToServeur(String contenu) {
		ACLMessage message = new ACLMessage(ACLMessage.PROPAGATE);
		message.addReceiver(getServerAID());
		message.setContent(contenu);
		return message;
	}
	
	private AID getServerAID() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Server");
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
