package behaviours.device;
import java.util.Date;

import com.sun.jmx.snmp.Timestamp;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.DataMessage;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

// Behaviour qui attend une demande liée à une tâche
public class WaitingTaskBehaviour extends Behaviour{

	private static final long serialVersionUID = 1L;
	DeviceInfoTypes demande;
	Utilisateur user;
	Task tache;
	String conversationId;
	
	public WaitingTaskBehaviour(DeviceInfoTypes demande, Utilisateur user, Task tache){
		this.demande = demande;
		this.user = user;
		this.tache = tache;
		this.conversationId  = generateConversationId();
	}

	private String generateConversationId() {
		Date date = new Date();
		return new Timestamp(date.getTime()).toString();
	}

	@Override
	public void action() {
		// construction du data message
		DataMessage dm = new DataMessage();
		dm.setDemande(demande);
		dm.setUser(user);
		dm.setTache(tache);
		// construction de l'acl message
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(getLiaisonAID());
		message.setConversationId(conversationId);
		myAgent.send(message);
	}
	
	private AID getLiaisonAID() {
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

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
		
	}
}
