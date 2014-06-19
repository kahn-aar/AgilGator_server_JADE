package behaviours.tache;


import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.BDDRequestTypes;
import Datas.enums.DeviceInfoTypes;
import Messages.BDDRequestMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	private Utilisateur user;	
	public TacheSendingRequestBehaviour(String conversationId, Task tache, DeviceInfoTypes demande, Utilisateur user) {
		this.conversationId = conversationId;
		this.tache = tache;
		this.demande = demande;
		this.user = user;
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
			message.setContent(createContent(request,request2,type));
			message.setConversationId(conversationId);
			message.setLanguage("JSON");
			myAgent.send(message);
			myAgent.addBehaviour(new TacheWaitingReplyBehaviour(conversationId, tache));
			myAgent.addBehaviour(new TacheWaitingSuccessBehaviour(conversationId));
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
			.append(",")
			.append("priorite = ")
			.append(t.getPriorite())
			.append(",")
			.append("difficulte = ")
			.append(t.getDifficulte())
			.append(",")
			.append("WHERE id = ")
			.append(t.getId())
			.append(";");
		return request.toString();
	}

	private String requestCreeTache(Task tache) {
		StringBuilder request = new StringBuilder();
		request.append("INSERT INTO Task (sprint, name, description, priorite, difficulte)")
		.append("VALUES (")
		.append("'"+tache.getSprint()+"'")
		.append(",")
		.append("'"+tache.getName()+"'")
		.append(",")
		.append("'"+tache.getDescription()+"'")
		.append(",")
		.append(tache.getPriorite())
		.append(",")
		.append(tache.getDifficulte())
		.append(");");
	return request.toString();
	}

	private String requestCreeTache2() {
		StringBuilder request = new StringBuilder();
		request.append("SELECT MAX(id) FROM Task;");
		return request.toString();
	}


	private String createContent(String request, String request2, BDDRequestTypes type) {
		BDDRequestMessage message = new BDDRequestMessage();
		message.setRequest(request);
		message.setRequest2(request2);
		message.setType(type);
		message.setDemande(demande);
		message.setUser(user);
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
