package behaviours.liaison;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class LiaisonPropagateInformationBehaviour extends OneShotBehaviour {

	/**
	 *  Behaviour permettant de propager une information vers le serveur
	 * ou vers les différents devices
	 * 
	 * @author Nicolas
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		ACLMessage message = createMessage();
		myAgent.send(message);
	}
	
	public AID getServer() {
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
	
	public ACLMessage createMessage() {
		ACLMessage newMessage = new ACLMessage(ACLMessage.PROPAGATE);
		
		newMessage.addReceiver(getServer());
		newMessage.setContent("salut");
		
		return newMessage;
	}
	
}
