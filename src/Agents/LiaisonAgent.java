package Agents;

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
		this.addBehaviour(new PropagateInformationBehaviour());
		
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
	
	/**
	 * Behaviour permettant de propager une information vers le serveur
	 * ou vers les différents devices
	 * 
	 * @author Nicolas
	 *
	 */
	public class PropagateInformationBehaviour extends OneShotBehaviour {

		@Override
		public void action() {
			ACLMessage message = createMessage();
			myAgent.send(message);
		}
		
		public AID getServer() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Serveur");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template);
				return result[0].getName();
			} catch(FIPAException fe) {
				fe.printStackTrace();
			}
			return null;
		}
		
		public ACLMessage createMessage() {
			ACLMessage newMessage = new ACLMessage(ACLMessage.PROPAGATE);
			
			newMessage.addReceiver(getServer());
			newMessage.setContent("salut");
			
			return newMessage;
		}
		
	}

}
