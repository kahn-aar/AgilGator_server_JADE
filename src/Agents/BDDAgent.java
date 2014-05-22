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
import Messages.RequestMessage;
import behaviours.BDDLunchRequestBehaviour;
import behaviours.BDDLunchUpdateRequestBehaviour;
import behaviours.BDDWaitingRequestBehaviour;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Agent gérant la base de données. Lui seul en a l'accès.
 * 
 * @author Nicolas
 *
 */
public class BDDAgent extends Agent {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void setup() {
		super.setup();
		
		this.addBehaviour(new BDDWaitingRequestBehaviour());

		//Enregistrement de l'agent auprès du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("BDD");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
}
