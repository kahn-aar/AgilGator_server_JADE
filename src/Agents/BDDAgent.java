package Agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import behaviours.BDDWaitingRequestBehaviour;

/**
 * Agent g�rant la base de donn�es. Lui seul en a l'acc�s.
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

		//Enregistrement de l'agent aupr�s du DF
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
