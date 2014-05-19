package Agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Messages.SynchroMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * G�re la synchronisation des utilisateurs lorsqu'ils se connectent
 * 
 * @author Nicolas
 *
 */
public class SynchroAgent extends Agent {

	@Override
	public void setup() {
		super.setup();
	}
	
	/**
	 * Ce behaviour attend et lit les messages re�us, et transmet la requ�te � la BDD
	 * 
	 * @author Nicolas
	 *
	 */
	public class ReceptionistBehaviour extends CyclicBehaviour {

		private String conversationId;
		private int userId;
		private int timeStamp;
		
		@Override
		public void action() {
			ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			if (message != null) {
				// D�s�r�alisation JSON
				ObjectMapper omap = new ObjectMapper();
				SynchroMessage msg = null;
				try {
					msg = omap.readValue(message.getContent(), SynchroMessage.class);
				}
				catch (Exception e) {
					
				}
				
				conversationId = message.getConversationId();
				userId = msg.getUserId();
				timeStamp = msg.getTimeStampLast();
				
				
			}
		}
		
		private String writeRequest() {
			//On va �crire toutes les requ�tes possibles sur la BDD.
			return null;
		}
		
	}
	
	/**
	 * Ce behaviour attend transmet la requ�te � la BDD
	 * 
	 * @author Nicolas
	 *
	 */
	public class SendRequestBehaviour extends OneShotBehaviour {

		private String conversationId;
		private int userId;
		private int timeStamp;
		
		@Override
		public void action() {
			
		}
		
	}
	
}
