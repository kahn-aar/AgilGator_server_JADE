package behaviours;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

/**
 * Ce behaviour attend transmet la requête à la BDD
 * 
 * @author Nicolas
 *
 */
public class SynchroSendRequestBehaviour extends OneShotBehaviour {

	private String conversationId;
	private String message;
	

	public SynchroSendRequestBehaviour(String conversationId2, String message) {
		this.conversationId = conversationId2;
		this.message = message;
	}

	@Override
	public void action() {
		ACLMessage requestSprintToBdd = new ACLMessage(ACLMessage.REQUEST);
		requestSprintToBdd.addReceiver(getBDDAgent());
		requestSprintToBdd.setContent(message);
		requestSprintToBdd.setConversationId(conversationId);
		myAgent.send(requestSprintToBdd);
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