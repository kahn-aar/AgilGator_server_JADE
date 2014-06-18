package behaviours.projet;

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
import Datas.Utilisateur;
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
public class ProjetsSendingRequestBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private Project projet;
	private DeviceInfoTypes demande;
	private Utilisateur member;
	private Utilisateur user;
	private String conversationId;
	
	public ProjetsSendingRequestBehaviour(String conversationId, Project projet, Utilisateur member, Utilisateur user, DeviceInfoTypes demande) {
		this.conversationId = conversationId;
		this.projet = projet;
		this.demande = demande;	
		this.member = member;
		this.user = user;
	}
	
	@Override
	public void action() {
		String request = null;
		String request2 = null;
		BDDRequestTypes type = null;
		switch(demande){
				case CREE_PROJET:
					request = requestCreeProjet(projet);
					request2 = requestCreeProjet2();
					type = BDDRequestTypes.INSERT;
					break;
				case EFFACE_PROJET:
					request = requestEffaceProjet(projet.getId());
					type = BDDRequestTypes.UPDATE;
					break;
				case MODIFIE_PROJET:
					request = requestModifieProjet(projet);
					type = BDDRequestTypes.UPDATE;
					break;
				case AJOUT_MEMBRE:
					request = requestAjoutMembre(projet.getId(), member.getId());
					request2 = requestGetProject(projet.getId());
					type = BDDRequestTypes.SELECT;
					break;
				case RETRAIT_MEMBRE:
					request = requestRetraitMembre(member.getId(), projet.getId());
					type = BDDRequestTypes.UPDATE;
					break;
				case MEMBRES_DU_PROJET:
					request = requestListeMembres(projet.getId());
					type = BDDRequestTypes.SELECT;
					break;
				case AJOUT_MANAGER:
					request = requestAjoutManager(projet.getId(), user.getId());
					type = BDDRequestTypes.INSERT;
				default:
					break;
			}// fin switch
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(getBddAgent());
			message.setContent(createContent(request, request2, type, demande));
			message.setConversationId(conversationId);
			message.setLanguage("JSON");
			myAgent.send(message);
			myAgent.addBehaviour(new ProjetsWaitingReplyBehaviour(conversationId, projet));
			myAgent.addBehaviour(new ProjetsWaitingSuccessBehaviour(conversationId));
	}


	private String requestAjoutManager(int projetId, int managerId) {
		StringBuilder request = new StringBuilder();
		request.append("INSERT INTO  Member (project, member, manager) VALUES (")
				.append(projetId)
				.append(",")
				.append(managerId)
				.append(", 1)")
				.append(";");
		return request.toString();
	}
	
	private String requestGetProject(int projectId)
	{
		StringBuilder request = new StringBuilder();
		request.append("SELECT * FROM Project WHERE Project.id = ")
		.append(projectId)
		.append(";");
		return request.toString();
	}

	private String requestListeMembres(int projetId) {
		StringBuilder request = new StringBuilder();
		request.append("SELECT member FROM Member WHERE projet = ")
				.append(projetId)
				.append(";");
		return request.toString();
	}

	private String requestRetraitMembre(int memberId, int projectId) {
		StringBuilder request = new StringBuilder();
		request.append("DELETE FROM Member WHERE member = ")
				.append(memberId)
				.append(" AND project = ")
				.append(projectId)
				.append(";");
		return request.toString();
	}

	private String requestAjoutMembre(int projectId, int memberId) {
		StringBuilder request = new StringBuilder();
		request.append("INSERT INTO  Member (project, member, manager) VALUES (")
				.append(projectId)
				.append(",")
				.append(memberId)
				.append(", 0)")
				.append(";");
		return request.toString();
	}

	private String requestModifieProjet(Project projet) {
		StringBuilder request = new StringBuilder();
		request.append("UPDATE Project ")
			.append("SET title = ")
			.append("'"+projet.getTitle()+"'")
			.append(",")
			.append("subtitle = ")
			.append("'"+projet.getSubTitle()+"'")
			.append(",")
			.append("description = ")
			.append("'"+projet.getDescription()+"'")
			.append(",")
			.append("last_update = ")
			.append("CURRENT_TIMESTAMP")
			.append(" WHERE id = ")
			.append(projet.getId())
			.append(";");
		return request.toString();
	}

	private String requestEffaceProjet(int projetId) {
		StringBuilder request = new StringBuilder();
		request.append("DELETE FROM Project WHERE id = ")
				.append(projetId)
				.append(";");
		return request.toString();
	}

	private String requestCreeProjet(Project projet) {
		StringBuilder request = new StringBuilder();
		request.append("INSERT INTO Project (title, subtitle, description, creation_date, last_update)")
			.append("VALUES (")
			.append("'"+projet.getTitle()+"'")
			.append(",")
			.append("'"+projet.getSubTitle()+"'")
			.append(",")
			.append("'"+projet.getDescription()+"'")
			.append(",")
			.append("CURRENT_TIMESTAMP")
			.append(",")
			.append("CURRENT_TIMESTAMP")
		.append(");");
		return request.toString();
	}
	
	private String requestCreeProjet2() {
		StringBuilder request = new StringBuilder();
		request.append("SELECT MAX(id) FROM PROJECT;");
		return request.toString();
	}

	

	private String createContent(String request, String request2, BDDRequestTypes type, DeviceInfoTypes demande) {
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
