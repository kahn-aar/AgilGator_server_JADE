package behaviours.liaison;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
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
		// Ajouter un "MatchSender" pour le messageServeur
		ACLMessage messageServeur = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE));
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

	}
	
	private ACLMessage createMessageToDevices(List<AID> destinataires) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		for(AID destinataire : destinataires) {
			message.addReceiver(destinataire);
		}
		// Ajout du contenu du message.
		return message;
	}

}
