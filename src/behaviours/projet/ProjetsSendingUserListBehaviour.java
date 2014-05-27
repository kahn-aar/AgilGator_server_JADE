package behaviours.projet;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Datas.Utilisateur;
import Messages.UserListMessage;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


/**
 * Envoie à l'agent Serveur la liste d'utilisateur du projet
 * @author Léa
 *
 */
public class ProjetsSendingUserListBehaviour extends OneShotBehaviour{
	
	private static final long serialVersionUID = 1L;
	private List<Utilisateur> userList;
	private String conversationId;

	public ProjetsSendingUserListBehaviour(List<Utilisateur> userList, String conversationId){
		this.userList = userList;
		this.conversationId = conversationId;
	}
	
	@Override
	public void action() {
		// Envoie un message à l'agent Serveur avec la liste des utilisateurs du projet
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		ObjectMapper omap = new ObjectMapper();
		UserListMessage ulm = new UserListMessage();
		ulm.setUserList(userList);
		try {
			String content = omap.writeValueAsString(ulm);
			msg.addReceiver(getServeurAgent());
			msg.setContent(content);
			msg.setConversationId(conversationId);
			msg.setLanguage("JSON");
			myAgent.send(msg); 
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Utilisateur> getUserList() {
		return userList;
	}

	public void setUserList(List<Utilisateur> userList) {
		this.userList = userList;
	}
	
	private AID getServeurAgent() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Serveur");
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