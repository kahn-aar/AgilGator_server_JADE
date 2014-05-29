package Agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import behaviours.liaison.LiaisonWaitingDeviceMessageBehaviour;
import behaviours.liaison.LiaisonWaitingServeurMessageBehaviour;

/**
 * Agent de liaison entre l'application serveur et 
 * les devices.
 * 
 * @author Nicolas
 *
 */
public class LiaisonAgent extends Agent {

	private static final long serialVersionUID = 1L;

	@Override
	public void setup() {
		super.setup();
		
		//Ajout des behaviours de base
		this.addBehaviour(new LiaisonWaitingDeviceMessageBehaviour());
		this.addBehaviour(new LiaisonWaitingServeurMessageBehaviour());
		
		//Enregistrement de l'agent auprès du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Liaison");
		sd.setName("Liaison " + getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

}
