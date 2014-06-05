package behaviours.soustache;


import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import Datas.SubTask;
import Datas.enums.BDDRequestTypes;
import Datas.enums.DeviceInfoTypes;
import Messages.BDDRequestMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
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
		String request2 = null;
		BDDRequestTypes type = null;
		switch(demande){
				case CREE_SOUS_TACHE:
					request = requestCreeSousTache(sousTache);
					request2 = requestCreeSousTache2();
					type = BDDRequestTypes.INSERT;
					break;
				case MODIFIER_SOUS_TACHE:
					request = requestModifierSousTache(sousTache);
					type = BDDRequestTypes.UPDATE;
					break;
				case SUPPRIMER_SOUS_TACHE:
					request = requestSupprimerSousTache(sousTache.getId());
					type = BDDRequestTypes.UPDATE;
					break;
				default:
					break;
			}// fin switch
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(getBddAgent());
			message.setContent(createContent(request, request2, type, demande));
			message.setConversationId(conversationId);
			message.setLanguage("JSON");
			myAgent.send(message);
			myAgent.addBehaviour(new SousTacheWaitingReplyBehaviour(conversationId));
			myAgent.addBehaviour(new SousTacheWaitingSuccessBehaviour(conversationId));
	}


	private String requestModifierSousTache(SubTask t) {
		StringBuilder request = new StringBuilder();
		request.append("UPDATE SubTask")
			.append("SET name = ")
			.append("'"+t.getName()+"'")
			.append(",")
			.append("description = ")
			.append("'"+t.getDescription()+"'")
			.append(",")
			.append("task = ")
			.append(t.getTaskId())
			.append(",")
			.append("last_update = ")
			.append("CURRENT_TIMESTAMP")
			.append(",")
			.append("current_developer = ")
			.append(t.getCurrent_developer())
			.append(",")
			.append("current_state = ")
			.append(t.getCurrent_state())
			.append("WHERE id = ")
			.append(t.getId())
			.append(";");
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
		request.append("INSERT INTO SubTask (task, name, description, current_state, current_developper, creation_date, last_update)")
			.append("VALUES (")
			.append(sousTache.getTaskId()+"'")
			.append(",")
			.append("'"+sousTache.getName()+"'")
			.append(",")
			.append("'"+sousTache.getDescription())
			.append(",")
			.append(sousTache.getCurrent_state())
			.append(",")
			.append(sousTache.getCurrent_developer())
			.append(",")
			.append("CURRENT_TIMESTAMP")
			.append(",")
			.append("CURRENT_TIMESTAMP")
			.append(");");
		return request.toString();
	}
	
	private String requestCreeSousTache2() {
		StringBuilder request = new StringBuilder();
		request.append("SELECT MAX(id) FROM SubTask;");
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
