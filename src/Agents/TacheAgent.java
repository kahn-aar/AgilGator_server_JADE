package Agents;

import behaviours.liaison.LiaisonWaitingDeviceMessageBehaviour;
import behaviours.tache.TacheWaitingRequestBehaviour;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 * Agent gérant les tâches
 * 
 * @author Nicolas
 *
 */
public class TacheAgent extends Agent {

	private static final long serialVersionUID = 1L;

	@Override
	public void setup() {
		super.setup();
		// Ajout des behaviours de type waiting
		this.addBehaviour(new TacheWaitingRequestBehaviour());
		//Enregistrement de l'agent auprès du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Tache");
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
