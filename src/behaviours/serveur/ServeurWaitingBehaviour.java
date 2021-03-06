package behaviours.serveur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Agents.ServeurAgent;
import Agents.UtilisateursAgent;
import Datas.Project;
import Datas.Sprint;
import Datas.SubTask;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.DataMessage;
import Messages.ServerLiaisonMessage;
import behaviours.compte.CompteSendingRequestBehaviour;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Attens les retours de projet, tache, sous-t�che, compte, sprint
 * @author L�a
 *
 */
public class ServeurWaitingBehaviour extends CyclicBehaviour{

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
		if(message!=null){
			System.out.println(myAgent.getLocalName() + " re�u -> " + message.getContent());
			String conversationId = message.getConversationId();
			ObjectMapper omap = new ObjectMapper();
			try {
				ServerLiaisonMessage msgToLiaison = omap.readValue(message.getContent(),ServerLiaisonMessage.class);
				DeviceInfoTypes demande = msgToLiaison.getDemande();
				Project projet = msgToLiaison.getProjet();
				Task tache = msgToLiaison.getTache();
				SubTask soustache = msgToLiaison.getSousTache();
				Sprint sprint = msgToLiaison.getSprint();
				myAgent.addBehaviour(new ServeurSendToLiaisonBehaviour(conversationId,  demande, projet, tache, soustache, sprint));
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	
	}
}
