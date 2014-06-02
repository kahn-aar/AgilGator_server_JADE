package behaviours.tache;

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
 * L'agent projet attend un message de l'agent serveur de type BDDMessage
 * Ce message lui donnera le type d'action à exécuter (select, insert, delete) et la requête
 * @author Léa
 *
 */
public class TacheSendingRequestBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private Task tache;
	private DeviceInfoTypes demande;
	
	private String conversationId;
	
	public TacheSendingRequestBehaviour(String conversationId, Task tache, DeviceInfoTypes demande) {
		this.conversationId = conversationId;
		this.tache = tache;
		this.demande = demande;
	}
	
	@Override
	public void action() {
		String request = null;
		String request2 = null;
		BDDRequestTypes type = null;
		switch(demande){
				case CREE_TACHE:
					request = requestCreeTache(tache);
					request2 = requestCreeTache2();
					type = BDDRequestTypes.INSERT;
					break;
				case MODIFIE_TACHE:
					request = requestModifieTache(tache);
					type = BDDRequestTypes.UPDATE;
					break;
				case SUPPRIMER_TACHE:
					request = requestSupprimerTache(tache.getId());
					type = BDDRequestTypes.UPDATE;
					break;
				default:
					break;
			}// fin switch
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(getBddAgent());
			message.setContent(createContent(request,request2,type, demande));
			message.setConversationId(conversationId);
			message.setLanguage("JSON");
			myAgent.send(message);
			myAgent.addBehaviour(new TacheWaitingReplyBehaviour(conversationId));
	}


	private String requestSupprimerTache(int tacheId) {
		StringBuilder request = new StringBuilder();
		request.append("DELETE FROM Task WHERE id = ")
				.append(tacheId)
				.append(";");
		return request.toString();
	}

	private String requestModifieTache(Task t) {
		StringBuilder request = new StringBuilder();
		request.append("UPDATE Task")
			.append("SET name = ")
			.append("'"+t.getName()+"'")
			.append(",")
			.append("description = ")
			.append("'"+t.getDescription()+"'")
			.append(",")
			.append("sprint = ")
			.append("'"+t.getSprint()+"'")
			.append(",")
			.append("creation_date = ")
			.append(t.getCreation_date())
			.append(",")
			.append("last_update = ")
			.append(t.getLast_update())
			.append(",")
			.append("priority = ")
			.append(t.getPriority())
			.append(",")
			.append("difficulty = ")
			.append(t.getDifficulty())
			.append(",")
			.append("current_state = ")
			.append(t.getCurrent_state())
			.append("WHERE id = ")
			.append(t.getId())
			.append(";");
		return request.toString();
	}

	private String requestCreeTache(Task tache) {
		StringBuilder request = new StringBuilder();
		request.append("INSERT INTO Task (sprint, name, description, priority, current_state, creation_date, last_update, difficulty)")
		.append("VALUES (")
		.append("'"+tache.getSprint()+"'")
		.append(",")
		.append("'"+tache.getName()+"'")
		.append(",")
		.append("'"+tache.getDescription()+"'")
		.append(",")
		.append(tache.getPriority())
		.append(",")
		.append(tache.getCurrent_state())
		.append(",")
		.append(tache.getCreation_date())
		.append(",")
		.append(tache.getLast_update())
		.append(",")
		.append(tache.getDifficulty())
		.append(");");
	return request.toString();
	}

	private String requestCreeTache2() {
		StringBuilder request = new StringBuilder();
		request.append("SELECT MAX(id) FROM Task;");
		return request.toString();
	}


	private String createContent(String request, String request2, BDDRequestTypes type, DeviceInfoTypes demande) {
		BDDRequestMessage message = new BDDRequestMessage();
		message.setRequest(request);
		message.setRequest2(request2);
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
