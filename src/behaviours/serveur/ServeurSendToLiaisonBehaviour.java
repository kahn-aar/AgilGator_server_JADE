package behaviours.serveur;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Datas.Project;
import Datas.Sprint;
import Datas.SubTask;
import Datas.Task;
import Datas.enums.DeviceInfoTypes;
import Messages.ServerLiaisonMessage;

/**
 * Behaviour permettant de transmettre les informations à l'agent de liaison
 * 
 * @author Nicolas
 *
 */
public class ServeurSendToLiaisonBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private String conversationId;
	private DeviceInfoTypes demande;
	private Project projet;
	private Sprint sprint;
	private Task tache;
	private SubTask soustache;
	
	public ServeurSendToLiaisonBehaviour(String conversationId, DeviceInfoTypes demande, Project projet, Task tache, SubTask soustache, Sprint sprint){
		this.conversationId = conversationId;
		this.demande = demande;
		this.projet = projet;
		this.tache = tache;
		this.soustache = soustache;
		this.sprint = sprint;
	}
	
	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.PROPAGATE);
		message.addReceiver(getLiasonAgent());
		message.setContent(writeMessage());
		message.setConversationId(conversationId);
		myAgent.send(message);
	}
	
	private String writeMessage() {
		ServerLiaisonMessage message = new ServerLiaisonMessage();
		message.setDemande(demande);
		message.setProjet(projet);
		message.setSprint(sprint);
		message.setTache(tache);
		message.setSousTache(soustache);
		
		ObjectMapper omap = new ObjectMapper();
		String messageCorps = null;
		try {
			messageCorps = omap.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return messageCorps;
	}
	
	
	
	
	private AID getLiasonAgent() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Liaison");
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
