package behaviours.bdd;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.BDDRequestMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Behaviour de base, attends les requêtes
 * 
 * @author Nicolas
 *
 */
public class BDDWaitingRequestBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 1L;


		@Override
		public void action() {
			ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			if (message != null) {
				// Déséréalisation JSON
				ObjectMapper omap = new ObjectMapper();
				BDDRequestMessage msg = null;
				try {
					msg = omap.readValue(message.getContent(), BDDRequestMessage.class);
				}
				catch (Exception e) {
					
				}
				if(msg.getType()!=null){
				switch(msg.getType()) {
					case INSERT:
						myAgent.addBehaviour(new BDDLunchInsertRequestBehaviour(message.getConversationId(), msg.getRequest(), msg.getRequest2(), msg.getUser(), message.getSender(), msg.getDemande()));
						break;
					case SELECT:
						myAgent.addBehaviour(new BDDLunchSelectRequestBehaviour(message.getConversationId(), msg.getRequest(), msg.getUser(), message.getSender(), msg.getDemande()));
						break;
					case UPDATE:
						myAgent.addBehaviour(new BDDLunchUpdateRequestBehaviour(message.getConversationId(), msg.getRequest(), message.getSender()));
						break;
					default:
						break;
				
				}
				}
			}
		}
		
	}

