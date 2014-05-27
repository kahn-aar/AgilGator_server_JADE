package behaviours.serveur;

import java.io.IOException;

import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.DataMessage;
import Messages.UserListMessage;

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
 * Ce behaviour attend un message de Liaison qui lui donne la liste des utilisateurs d'un projet.
 * Il envoie ensuite un message à l'agent Utlisateur pour qui lui renvoie la liste des utilisateurs du projet connectés.
 * @author Léa
 *
 */
public class ServeurProjectUserListConnectedRequestBehaviour extends OneShotBehaviour{

	private static final long serialVersionUID = 1L;
	private String conversationId;

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		if (message != null) {
			System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
			ObjectMapper omap = new ObjectMapper();
			try {
				UserListMessage ulm = omap.readValue(message.getContent(),UserListMessage.class);
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				String content = omap.writeValueAsString(ulm);
				msg.addReceiver(getUtilisateursAgent());
				msg.setContent(content);
				msg.setConversationId(conversationId);
				msg.setLanguage("JSON");
				myAgent.send(msg);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private AID getUtilisateursAgent() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Utilisateurs");
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
