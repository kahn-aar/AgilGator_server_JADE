package Agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import behaviours.ServeurReceptionBehaviour;

/**
 * Agent de coordination sur le serveur,
 * toutes les informations transitent par lui.
 * 
 * @author Nicolas
 *
 */
public class ServeurAgent extends Agent {

	@Override
	public void setup() {
		super.setup();
		
		//Ajout du behaviour de base
		this.addBehaviour(new ServeurReceptionBehaviour());
		
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

