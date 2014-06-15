package behaviours.compte;

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
import Messages.CompteMessage;
import Messages.ProjetRequestMessage;
import Messages.TacheRequestMessage;
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
public class CompteSendingRequestBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private Utilisateur user;
	private DeviceInfoTypes demande;
	private Boolean existed;
	private String conversationId;
	
	public CompteSendingRequestBehaviour(String conversationId, DeviceInfoTypes demande, Utilisateur user, Boolean existed) {
		this.conversationId = conversationId;
		this.user = user;
		this.demande = demande;
		this.existed = existed;
	}

	@Override
	public void action() {
		String request = null;
		String request2 = null;
		BDDRequestTypes type = null;
		switch(demande){
			case CREE_COMPTE:
				// On vérifie que le compte n'existe pas déjà
				if (existed ==null){
					CompteMessage checkMsg = new CompteMessage();
					checkMsg.setDemande(DeviceInfoTypes.IS_USER);
					checkMsg.setUser(user);
					ObjectMapper omap = new ObjectMapper();
					try {
						String content = omap.writeValueAsString(checkMsg);
						ACLMessage msg= new ACLMessage(ACLMessage.REQUEST);
						msg.addReceiver(getCompteAgent());
						msg.setContent(content);
						msg.setConversationId(conversationId);
						msg.setLanguage("JSON");
						myAgent.send(msg);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(existed == false){
					request = requestCreeCompte(user);
					request2 = requestCreeCompte2();
					type = BDDRequestTypes.INSERT;
				}
				else {
					System.out.println("Votre compte existe déjà !");
				}
				break;
			case IS_USER:
				request = requestAllUsers();
				type = BDDRequestTypes.SELECT;
				break;
			case ALL_USERS:
				request = requestAllUsers();
				type = BDDRequestTypes.SELECT;
				break;
			case CONNEXION:
				request = requestConnexion();
				type = BDDRequestTypes.SELECT;
				 break;
			default:
				break;
		}// fin switch
		if(request!= null && type != null){
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(getBddAgent());
			message.setContent(createContent(request,request2,user , type, demande));
			message.setConversationId(conversationId);
			message.setLanguage("JSON");
			myAgent.send(message);
			myAgent.addBehaviour(new CompteWaitingReplyBehaviour(conversationId));
			myAgent.addBehaviour(new CompteWaitingSuccessBehaviour(conversationId));
		}
	}


	private String requestAllUsers() {
		StringBuilder request = new StringBuilder();
		request.append("SELECT * FROM Users");
		return request.toString();
	}

	private String requestConnexion() {
		StringBuilder request = new StringBuilder();
		// requête à implémenter
		return request.toString();
	}

	private String requestCreeCompte(Utilisateur user) {
		StringBuilder request = new StringBuilder();
		request.append("INSERT INTO Users (email, password, firstname, name, salt1)")
			.append("VALUES (")
			.append("'"+user.getEmail()+"'")
			.append(",")
			.append("'"+user.getPassword()+"'")
			.append(",")
			.append("'"+user.getFirstname()+"'")
			.append(",")
			.append("'"+user.getName()+"'")
			.append(",")
			.append("'"+user.getSalt1()+"'")
			.append(");");
		return request.toString();
	}
	
	private String requestCreeCompte2() {
		StringBuilder request = new StringBuilder();
		request.append("SELECT MAX(id) FROM Users;");
		return request.toString();
	}

	private String createContent(String request, String request2, Utilisateur user, BDDRequestTypes type, DeviceInfoTypes demande) {
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
	
	private AID getCompteAgent() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Compte");
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
