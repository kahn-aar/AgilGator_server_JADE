package Agents;

import behaviours.liaison.LiaisonPropagateInformationBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

/**
 * Agent de liaison entre l'application serveur et 
 * les devices.
 * 
 * @author Nicolas
 *
 */
public class LiaisonAgent extends Agent {

	@Override
	public void setup() {
		super.setup();
		
		//Ajout des behaviours de base
		this.addBehaviour(new LiaisonPropagateInformationBehaviour());
		
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
