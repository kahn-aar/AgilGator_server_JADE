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
 * Ce behaviour attend les messages du device avant de les propager vers Serveur
 * Aucun traitement des messages
 * @author L�a
 *
 */
public class LiaisonWaitingDeviceMessageBehaviour extends CyclicBehaviour {
		
	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		// A AJOUTER : match sender device
		ACLMessage messageDevice = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (messageDevice != null) {
			System.out.println(myAgent.getLocalName() + " re�u -> " + messageDevice.getContent());
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(getServerAID());
			message.setContent(messageDevice.getContent());
			message.setConversationId(messageDevice.getConversationId());
			myAgent.send(message);
		}
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