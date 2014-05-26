package behaviours.projet;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import Datas.enums.BDDRequestTypes;
import Messages.BDDRequestMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Behaviour posant des questions à l'agent BDD
 * 
 * @author Nicolas
 *
 */
public class ProjetsSendingRequestBehaviour extends OneShotBehaviour {

	private int projectId;
	private String conversationId;
	
	public ProjetsSendingRequestBehaviour(int id) {
		this.projectId = id;
		this.conversationId = String.valueOf(id);
		System.out.println("yo projets agent");
	}
	
	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(getBddAgent());
		message.setContent(createContent());
		message.setConversationId(conversationId);
		message.setLanguage("JSON");
		myAgent.send(message);
		
		myAgent.addBehaviour(new ProjetsWaitingReplyBehaviour(conversationId));
	}
	
	private String createContent() {
		BDDRequestMessage message = new BDDRequestMessage();
		message.setRequest(createRequest());
		message.setType(BDDRequestTypes.SELECT);
		
		// Séréalisation JSON
		ObjectMapper omap = new ObjectMapper();
		String messageCorps = null;
		try {
			messageCorps = omap.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return messageCorps;
	}
	
	private String createRequest() {
		StringBuilder request = new StringBuilder();
		request.append("SELECT user FROM t_user_projet WHERE projet_id = ")
				.append(projectId)
				.append(";");
		
		return request.toString();
	}
	
	private AID getBddAgent() {
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
