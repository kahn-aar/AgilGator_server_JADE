package behaviours;

import jade.core.behaviours.OneShotBehaviour;

/**
 * Ce behaviour attend transmet la requ�te � la BDD
 * 
 * @author Nicolas
 *
 */
public class SynchroSendRequestBehaviour extends OneShotBehaviour {

	private String conversationId;
	private int userId;
	private int timeStamp;
	
	@Override
	public void action() {
		
	}
	
}