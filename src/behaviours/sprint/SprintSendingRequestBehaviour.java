package behaviours.sprint;

import java.io.IOException;
import java.sql.Time;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Datas.Project;
import Datas.Sprint;
import Datas.SubTask;
import Datas.Task;
import Datas.enums.BDDRequestTypes;
import Datas.enums.DeviceInfoTypes;
import Messages.BDDRequestMessage;
import Messages.ProjetRequestMessage;
import Messages.UserListMessage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * L'agent projet attend un message de l'agent serveur de type BDDMessage
 * Ce message lui donnera le type d'action à exécuter (select, insert, delete) et la requête
 * @author Léa
 *
 */
public class SprintSendingRequestBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private Sprint sprint;
	private DeviceInfoTypes demande;
	
	private String conversationId;
	
	public SprintSendingRequestBehaviour(String conversationId, Sprint sprint, DeviceInfoTypes demande) {
		this.conversationId = conversationId;
		this.sprint = sprint;
		this.demande = demande;	
	}
	
	@Override
	public void action() {
		String request = null;
		BDDRequestTypes type = null;
		switch(demande){
				case CREE_SPRINT:
					request = requestCreeSprint();
					type = BDDRequestTypes.INSERT;
					break;
				case EFFACE_SPRINT:
					request = requestEffaceSprint();
					type = BDDRequestTypes.UPDATE;
					break;
				case ARCHIVER_SPRINT:
					request = requestArchiverSprint();
					type = BDDRequestTypes.UPDATE;
					break;
				default:
					break;
			}// fin switch
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(getBddAgent());
			message.setContent(createContent(request, type, demande));
			message.setConversationId(conversationId);
			message.setLanguage("JSON");
			myAgent.send(message);
			myAgent.addBehaviour(new SprintWaitingReplyBehaviour(conversationId));
	}

	
	private String requestArchiverSprint() {
		StringBuilder request = new StringBuilder();
		// Requête à implémenter
		return request.toString();
	}

	private String requestEffaceSprint() {
		StringBuilder request = new StringBuilder();
		// Requête à implémenter
		return request.toString();
	}

	private String requestCreeSprint() {
		StringBuilder request = new StringBuilder();
		// Requête à implémenter
		return request.toString();
	}


	private String createContent(String request, BDDRequestTypes type, DeviceInfoTypes demande) {
		BDDRequestMessage message = new BDDRequestMessage();
		message.setRequest(request);
		message.setType(type);
		message.setDemande(demande);
		// Séréalisation JSON
		ObjectMapper omap = new ObjectMapper();
		String messageCorps = null;
		try {
			messageCorps = omap.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();}
		return messageCorps;
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
