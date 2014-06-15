package behaviours.serveur;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.List;
import Datas.enums.DeviceInfoTypes;
import ServerClient.ServerSynchronistMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Behaviour qui attend les réponses de la synchronisation pour 
 * l'envoyer à la liaison
 * 
 * @author Nicolas
 *
 */
public class ServerWaitingSynchronistBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;
	private int step = 0;
	private String conversationId;
	private AID user;
	
	public ServerWaitingSynchronistBehaviour(String conversationId, AID user) {
		this.conversationId = conversationId;
		this.user = user;
	}
	
	@Override
	public void action() {
		if (step == 0) {
			ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchConversationId(conversationId)), MessageTemplate.MatchSender(getSynchroAgent())));
			if (message != null) {
				ServerSynchronistMessage propagate = new ServerSynchronistMessage();
				ObjectMapper omap = new ObjectMapper();
				String msg = null;
				propagate.setContent(message.getContent());
				propagate.setType(DeviceInfoTypes.SYNCHRONIZE_UP);
				try {
					msg = omap.writeValueAsString(propagate);
				}
				catch (Exception e) {
					
				}
				List<AID> destinataire = new ArrayList<AID>(1);
				destinataire.add(user);
				myAgent.addBehaviour(new ServeurSendToLiaisonBehaviour(conversationId, destinataire, msg, DeviceInfoTypes.SYNCHRONIZE_UP));
				step++;
			}
		}

	}

	@Override
	public boolean done() {
		if (step == 1) {
			return true;
		}
		return false;
	}
	
	private AID getSynchroAgent() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Synchro");
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
