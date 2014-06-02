package Agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import behaviours.liaison.LiaisonWaitingDeviceMessageBehaviour;
import behaviours.projet.ProjetsSendingRequestBehaviour;
import behaviours.projet.ProjetsWaitingReplyBehaviour;
import behaviours.projet.ProjetsWaitingRequestBehaviour;

public class ProjetsAgent extends Agent {

	private static final long serialVersionUID = 1L;

	@Override
	public void setup() {
		super.setup();
		
		// Ajout des behaviours de type waiting
		this.addBehaviour(new ProjetsWaitingRequestBehaviour());
		
		//Enregistrement de l'agent auprès du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Projet");
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
