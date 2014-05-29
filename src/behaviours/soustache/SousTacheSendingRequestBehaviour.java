package behaviours.soustache;

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
 * L'agent sous tache attend un message de l'agent serveur de type BDDMessage
 * Ce message lui donnera le type d'action à exécuter (select, insert, delete) et la requête
 * @author Léa
 *
 */
public class SousTacheSendingRequestBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private SubTask sousTache;
	private DeviceInfoTypes demande;
	
	private String conversationId;
	
	public SousTacheSendingRequestBehaviour(String conversationId, SubTask sousTache, DeviceInfoTypes demande) {
		this.conversationId = conversationId;
		this.sousTache = sousTache;
		this.demande = demande;	
	}
	
	@Override
	public void action() {
		String request = null;
		BDDRequestTypes type = null;
		switch(demande){
				case CREE_SOUS_TACHE:
					request = requestCreeSousTache(sousTache);
					type = BDDRequestTypes.INSERT;
					break;
				case MODIFIER_SOUS_TACHE:
					request = requestModifierSousTache();
					type = BDDRequestTypes.UPDATE;
				case SUPPRIMER_SOUS_TACHE:
					request = requestSupprimerSousTache(sousTache.getId());
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
			myAgent.addBehaviour(new SousTacheWaitingReplyBehaviour(conversationId));
	}


	private String requestModifierSousTache() {
		StringBuilder request = new StringBuilder();
		// requête à implémenter
		return request.toString();
	}

	private String requestSupprimerSousTache(int sousTacheId) {
		StringBuilder request = new StringBuilder();
		request.append("DELETE FROM SubTask WHERE id = ")
				.append(sousTacheId)
				.append(";");
		
		return request.toString();
	}

	private String requestCreeSousTache(SubTask sousTache) {
		StringBuilder request = new StringBuilder();
		request.append("INSERT INTO SubTask (id, task, name, description, current_state, current_developper, creation_date, last_update)")
			.append("VALUES (")
			.append(sousTache.getId())
			.append(",")
			.append(sousTache.getTaskId())
			.append(",")
			.append(sousTache.getName())
			.append(",")
			.append(sousTache.getDescription())
			.append(",")
			.append(sousTache.getCurrent_state())
			.append(",")
			.append(sousTache.getCurrent_developer())
			.append(",")
			.append(sousTache.getCreation_date())
			.append(",")
			.append(sousTache.getLast_update())
			.append(");");
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
