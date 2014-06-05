package behaviours.utilisateurs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Agents.UtilisateursAgent;
import Datas.Utilisateur;
import Messages.DataMessage;
import Messages.UserListMessage;
import Messages.UserMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;



/**
 * Ce behaviour a pour rôle de renvoyer la liste des utilisateurs connectés
 * @author Léa
 *
 */
public class UserProjectListBehaviour extends OneShotBehaviour{

	private static final long serialVersionUID = 1L;
	private String conversationId;

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchConversationId(conversationId), MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchSender(getServeurAgent()))));
		if (message != null) {
			System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
			ObjectMapper omap = new ObjectMapper();
			try {
				UserListMessage userListMsg = omap.readValue(message.getContent(),UserListMessage.class);
				// List des utilisateurs participant au projet
				List<Utilisateur> userProject = userListMsg.getUserList();
				// List des utilisateurs connectés
				List<Utilisateur> userConnected = ((UtilisateursAgent) myAgent).getUtilisateursConnectés();
				List<Utilisateur> userProjectConnected = intersection(userProject, userConnected);
				if(userProjectConnected!=null){
					ObjectMapper omapConnexion = new ObjectMapper();
					try {
						UserListMessage ulm = new UserListMessage();
						ulm.setUserList(userProjectConnected);
						String replyContent = omapConnexion.writeValueAsString(ulm);
						ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
						reply.addReceiver(getServeurAgent());
						reply.setContent(replyContent);
						reply.setConversationId(conversationId);
						reply.setLanguage("JSON");
						myAgent.send(reply);
						
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	

    public List<Utilisateur> intersection(List<Utilisateur> list1, List<Utilisateur> list2) {
        List<Utilisateur> resultList = new ArrayList<Utilisateur>();

        for (Utilisateur t : list1) {
            if(list2.contains(t)) {
                resultList.add(t);
            }
        }
        return resultList;
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