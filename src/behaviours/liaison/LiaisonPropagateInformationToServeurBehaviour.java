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
 * Propage les informations de device vers serveur
 * @author Léa
 *
 */
public class LiaisonPropagateInformationToServeurBehaviour extends OneShotBehaviour{

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		// Attend un message de serveur
		ACLMessage msgServeur = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),MessageTemplate.MatchSender(getDevice())));
		ACLMessage message = createMessage(msgServeur.getContent());
		myAgent.send(message);
	}
	
	private AID getDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	private AID getServer() {
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
	
	private ACLMessage createMessage(String content) {
		ACLMessage newMessage = new ACLMessage(ACLMessage.PROPAGATE);
		newMessage.addReceiver(getServer());
		newMessage.setContent(content);
		return newMessage;
	}
}
