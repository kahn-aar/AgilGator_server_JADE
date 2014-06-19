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
import java.util.ArrayList;
import java.util.List;

import Datas.enums.DeviceInfoTypes;
import Messages.ServerLiaisonMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LiaisonWaitingServeurMessageBehaviour extends CyclicBehaviour{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		ACLMessage messageServeur = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE), MessageTemplate.MatchSender(getServerAID())));
		if (messageServeur != null) {
			System.out.println(myAgent.getLocalName() + " re�u -> " + messageServeur.getContent());
			// On d�s�r�alise le message
			ObjectMapper omap = new ObjectMapper();
			try {
				ServerLiaisonMessage msg = omap.readValue(messageServeur.getContent(),ServerLiaisonMessage.class);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// Ecriture de message
			ACLMessage message = createMessageToDevices(messageServeur.getContent(), messageServeur.getConversationId());
			myAgent.send(message);
			System.out.println(myAgent.getLocalName() + " envoy� -> " + message.getContent());
		}
	}
	
	private ACLMessage createMessageToDevices(String content, String conversationId) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		for(AID destinataire : addDevicesRecivers()) {
			message.addReceiver(destinataire);
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
	
	private List<AID> addDevicesRecivers() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Device");
		template.addServices(sd);
		List<AID> aids = new ArrayList<>();
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			for (DFAgentDescription agentd : result) {
				aids.add(agentd.getName());
			}
		} catch(FIPAException fe) {
			fe.printStackTrace();
		}
		return aids;
	}

}
