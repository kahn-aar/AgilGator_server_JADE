package Agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import behaviours.synchro.SynchroReceptionistBehaviour;

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
