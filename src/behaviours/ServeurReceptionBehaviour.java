package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ServeurReceptionBehaviour extends CyclicBehaviour{
	/**
	 * Ce behaviour attend et lit les messages re�us, et d�finit la chose � faire
	 * 
	 * @author Nicolas
	 */
	private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE));
			if (message != null) {
				System.out.println(myAgent.getLocalName() + " re�u -> " + message.getContent());
			}
			
		}
		
}
