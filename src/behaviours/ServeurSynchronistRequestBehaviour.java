package behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import Messages.SynchroMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *  * Ce behaviour envoie une requete de synchronisation
 * 
 * @author Nicolas
 *
 */
public class ServeurSynchronistRequestBehaviour extends OneShotBehaviour {
	
	private static final long serialVersionUID = 1L;

		private String conversationId;
		private int userId;
		private int timeStamp;
		
		public ServeurSynchronistRequestBehaviour(int userId, int timeStamp) {
			this.userId = userId;
			this.timeStamp = timeStamp;
			conversationId = userId + "sync"+ timeStamp;
		}
		
		@Override
		public void action() {
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.setContent(writeMessage());
			message.setConversationId(conversationId);
			//Envoi de message vers Sync
		}
		
		private String writeMessage() {
			// Séréalisation JSON
			SynchroMessage corps = new SynchroMessage();
			corps.setUserId(userId);
			corps.setTimeStampLast(timeStamp);
			
			ObjectMapper omap = new ObjectMapper();
			String messageCorps = null;
			try {
				messageCorps = omap.writeValueAsString(corps);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			return messageCorps;
		}
		
	}
	