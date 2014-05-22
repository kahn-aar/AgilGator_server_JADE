package Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Messages.SynchroMessage;
import behaviours.SynchroReceptionistBehaviour;
import behaviours.SynchroSendRequestBehaviour;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Gère la synchronisation des utilisateurs lorsqu'ils se connectent
 * 
 * @author Nicolas
 *
 */
public class SynchroAgent extends Agent {

	@Override
	public void setup() {
		super.setup();
		
		this.addBehaviour(new SynchroReceptionistBehaviour());
		this.addBehaviour(new SynchroSendRequestBehaviour());
		

		//Enregistrement de l'agent auprès du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Serveur");
		sd.setName("Serveur " + getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	
}
