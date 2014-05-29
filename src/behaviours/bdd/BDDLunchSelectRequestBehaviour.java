package behaviours.bdd;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Agents.BDDAgent;
import Datas.enums.DeviceInfoTypes;
import Messages.BDDAnswerMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Behaviour gérant les requêtes en "select"
 * 
 * @author Nicolas
 *
 */
public class BDDLunchSelectRequestBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private String conversationId;
	private String query;
	private AID receiver;
	private DeviceInfoTypes demande;
	
	public BDDLunchSelectRequestBehaviour(String conversationId, String query, AID replyTo, DeviceInfoTypes demande) {
		this.conversationId = conversationId;
		this.query = query;
		this.receiver = replyTo;
		this.demande = demande;
	}
	
	@Override
	public void action() {
		System.out.println("BDD reçu = " + query);
		Connection connection = ((BDDAgent)myAgent).connectDatabase();
		try {
			Statement statement = connection.createStatement();
			int ok = statement.executeUpdate(query);
			this.createMessage(ok);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		((BDDAgent)myAgent).disconnectDatabase(connection);
		
	}
	
	private ACLMessage createMessage(int answer) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(receiver);
		message.setContent(writeContent(answer));
		message.setConversationId(conversationId);
		
		return message;
	}
	
	private String writeContent(int answer) {
		BDDAnswerMessage corps = new BDDAnswerMessage();
		
		ObjectMapper omap = new ObjectMapper();
		String messageCorps = String.valueOf(answer);
		try {
			messageCorps = omap.writeValueAsString(corps);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return messageCorps;
	}
	
	private Object handleResultSet(ResultSet result)
	{
		return null;
	}
	
	private Object select() {
		return null;
	}

}
