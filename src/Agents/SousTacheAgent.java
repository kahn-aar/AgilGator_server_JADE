package Agents;

import behaviours.liaison.LiaisonWaitingDeviceMessageBehaviour;
import behaviours.soustache.SousTacheWaitingRequestBehaviour;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class SousTacheAgent extends Agent {

	private static final long serialVersionUID = 1L;

	@Override
	public void setup() {
		super.setup();
		// Ajout des behaviours de type waiting
		this.addBehaviour(new SousTacheWaitingRequestBehaviour());
		//Enregistrement de l'agent aupr�s du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("SousTache");
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
