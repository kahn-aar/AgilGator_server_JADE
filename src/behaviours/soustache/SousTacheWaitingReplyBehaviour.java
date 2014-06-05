package behaviours.soustache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Agents.UtilisateursAgent;
import Datas.Utilisateur;
import Datas.Constantes.ConstantesTables;
import Messages.BDDAnswerMessage;
import Messages.UserListMessage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Behaviour attendant une réponse à sa request
 * 
 * @author Nicolas
 *
 */
public class SousTacheWaitingReplyBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;
	private int step = 0;
	private String conversationId;
	
	public SousTacheWaitingReplyBehaviour(String conversationId) {
		this.conversationId = conversationId;
	}
	
	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchConversationId(conversationId), MessageTemplate.and(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchConversationId(conversationId)), MessageTemplate.MatchSender(getBDDAgent()))));
		if (message != null) {
			// Il récupère le résultat de la requête.
			ObjectMapper omap = new ObjectMapper();
			try {
				BDDAnswerMessage answer = omap.readValue(message.getContent(),BDDAnswerMessage.class);
				if(answer !=null){
					switch(answer.getTable()){
						case ConstantesTables.SUBTASK:
							break;
						default:
							break;
					}
				}
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

	@Override
	public boolean done() {
		if (step == 1) {
			return true;
		}
		return false;
	}
	
	private AID getBDDAgent() {
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
