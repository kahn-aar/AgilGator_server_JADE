package Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

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
		this.addBehaviour(new ReceptionistBehaviour());
		
		//Enregistrement de l'agent aupr�s du DF
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
	
	/**
	 * Ce behaviour attend et lit les messages re�us, et d�finit la chose � faire
	 * 
	 * @author Nicolas
	 *
	 */
	public class ReceptionistBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE));
			if (message != null) {
				System.out.println(myAgent.getLocalName() + " re�u -> " + message.getContent());
			}
			
		}
		
	}
	
}
