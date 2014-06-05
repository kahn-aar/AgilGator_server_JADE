package behaviours.liaison;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *  Behaviour permettant de propager une information vers les différents devices
 * 
 * @author Nicolas
 */
public class LiaisonPropagateInformationToDeviceBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		// Attend un message de serveur
		ACLMessage msgServeur = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),MessageTemplate.MatchSender(getServeur())));
		if(msgServeur!=null){
			System.out.println(myAgent.getLocalName() + " reçu -> " + msgServeur.getContent());
			ACLMessage message = createMessage(msgServeur.getConversationId(), msgServeur.getContent());
			myAgent.send(message);
		}
	}
	
	private AID getDevice() {
		/* A faire quand device sera prêt
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Device");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			return result[0].getName();
		} catch(FIPAException fe) {
			fe.printStackTrace();
		}*/
		return null;
	}
	
	private AID getServeur() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Device");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			return result[0].getName();
		} catch(FIPAException fe) {
			fe.printStackTrace();
		}
		return null;
	}
	
	private ACLMessage createMessage(String conversationId, String content) {
		ACLMessage newMessage = new ACLMessage(ACLMessage.PROPAGATE);
		newMessage.addReceiver(getDevice());
		newMessage.setContent(content);
		newMessage.setConversationId(conversationId);
		return newMessage;
	}
	
}
