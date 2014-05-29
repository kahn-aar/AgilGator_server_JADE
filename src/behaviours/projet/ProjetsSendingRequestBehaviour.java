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
	private Task tache;
	private SubTask sousTache;
	private DeviceInfoTypes demande;
	
	private String conversationId;
	
	public ProjetsSendingRequestBehaviour(String conversationId, Project projet, Task tache, SubTask sousTache, DeviceInfoTypes demande) {
		this.conversationId = conversationId;
		this.projet = projet;
		this.sousTache = sousTache;
		this.tache = tache;
		this.demande = demande;	
	}
	
	@Override
	public void action() {
		String request = null;
		BDDRequestTypes type = null;
		switch(demande){
				case CREE_COMPTE: 
					request = requestCreeCompte();
					type = BDDRequestTypes.INSERT;
					break;
				case CONNEXION:
					request = requestConnexion();
					// type = BDDRequestTypes.INSERT; NOT SURE
					 break;
				case DECONNEXION: 
					request = requestDeconnexion();
					// type = BDDRequestTypes.INSERT; NOT SURE
					break;
				case CREE_PROJET:
					request = requestCreeProjet(projet);
					type = BDDRequestTypes.INSERT;
					break;
				case EFFACE_PROJET:
					request = requestEffaceProjet(projet.getId());
					type = BDDRequestTypes.DELETE;
					break;
				case MODIFIE_PROJET:
					request = requestModifieProjet();
					type = BDDRequestTypes.UPDATE;
					break;
				case AJOUT_MEMBRE:
					request = requestAjoutMembre();
					type = BDDRequestTypes.INSERT;
					break;
				case RETRAIT_MEMBRE:
					request = requestRetraitMembre();
					type = BDDRequestTypes.DELETE;
					break;
				case CREE_SPRINT:
					request = requestCreeSprint();
					break;
				case EFFACE_SPRINT:
					request = requestEffaceSprint();
					type = BDDRequestTypes.INSERT;
					break;
				case ARCHIVER_SPRINT:
					request = requestArchiverSprint();
					//type = BDDRequestTypes.INSERT; NOT SURE
					break;
				case CREE_TACHE:
					request = requestCreeTache(tache);
					type = BDDRequestTypes.INSERT;
					break;
				case MODIFIE_TACHE:
					request = requestModifieTache();
					type = BDDRequestTypes.UPDATE;
					break;
				case SUPPRIMER_TACHE:
					request = requestSupprimerTache(tache.getId());
					type = BDDRequestTypes.DELETE;
					break;
				case CREE_SOUS_TACHE:
					request = requestCreeSousTache(sousTache);
					type = BDDRequestTypes.INSERT;
					break;
				case MODIFIER_SOUS_TACHE:
					request = requestModifierSousTache();
					type = BDDRequestTypes.UPDATE;
				case SUPPRIMER_SOUS_TACHE:
					request = requestSupprimerSousTache(sousTache.getId());
					type = BDDRequestTypes.DELETE;
					break;
				case SYNCHRONIZE_UP:
					request = requestSynchronizeUp();
					// type = BDDRequestTypes.INSERT; NOT SURE
					break;
				case SYNCHRONIZE_DOWN:
					request = requestSynchronizeDown();
					// type = BDDRequestTypes.INSERT; NOT SURE
					break;
				case LISTE_MEMBRES:
					request = requestListeMembres(projet.getId());
					type = BDDRequestTypes.SELECT;
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
			myAgent.addBehaviour(new ProjetsWaitingReplyBehaviour(conversationId));
	}

	private String requestListeMembres(int projetId) {
		StringBuilder request = new StringBuilder();
		request.append("SELECT member FROM Member WHERE projet = ")
				.append(projetId)
				.append(";");
		return request.toString();
	}

	private String requestSynchronizeDown() {
		StringBuilder request = new StringBuilder();
		// requête à implémenter
		return request.toString();
	}

	private String requestSynchronizeUp() {
		StringBuilder request = new StringBuilder();
		// requête à implémenter
		return request.toString();
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

	private String requestSupprimerTache(int tacheId) {
		StringBuilder request = new StringBuilder();
		request.append("DELETE Task id = ")
				.append(tacheId)
				.append(";");
		return request.toString();
	}

	private String requestModifieTache() {
		StringBuilder request = new StringBuilder();
		// Requête à implémenter
		return request.toString();
	}

	private String requestCreeTache(Task tache) {
		StringBuilder request = new StringBuilder();
		request.append("INSERT INTO Task (id, sprint, name, description, priority, current_state, creation_date, last_update, difficulty)")
		.append("VALUES (")
		.append(tache.getId())
		.append(",")
		.append(tache.getSprint())
		.append(",")
		.append(tache.getName())
		.append(",")
		.append(tache.getDescription())
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

	private String requestRetraitMembre() {
		StringBuilder request = new StringBuilder();
		// Requête à implémenter
		return request.toString();
	}

	private String requestAjoutMembre() {
		StringBuilder request = new StringBuilder();
		// Requête à implémenter
		return request.toString();
	}

	private String requestModifieProjet() {
		StringBuilder request = new StringBuilder();
		// Requête à implémenter
		return request.toString();
	}

	private String requestEffaceProjet(int projetId) {
		StringBuilder request = new StringBuilder();
		request.append("DELETE Project WHERE id = ")
				.append(projetId)
				.append(";");
		return request.toString();
	}

	private String requestCreeProjet(Project projet) {
		StringBuilder request = new StringBuilder();
		request.append("INSERT INTO Project (id, title, subtitle, description, creation_date, last_update)")
			.append("VALUES (")
			.append(projet.getId())
			.append(",")
			.append(projet.getTitle())
			.append(",")
			.append(projet.getSubtitle())
			.append(",")
			.append(projet.getDescription())
			.append(",")
			.append(projet.getCreation_date())
			.append(",")
			.append(projet.getLast_update())
		.append(");");
		return request.toString();
	}

	private String requestDeconnexion() {
		StringBuilder request = new StringBuilder();
		// requête à implémenter
		return request.toString();
	}

	private String requestConnexion() {
		StringBuilder request = new StringBuilder();
		// requête à implémenter
		return request.toString();
	}

	private String requestCreeCompte() {
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
