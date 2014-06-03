package behaviours.bdd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Agents.BDDAgent;
import Messages.BDDAnswerMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
* Behaviour lançant une requête sur la base de données, qui n'attend pas de 
* résultats en retour, uniquement un message de succès ou de fail
*/
public class BDDLunchUpdateRequestBehaviour extends OneShotBehaviour  {
	
	private static final long serialVersionUID = 1L;

	private String conversationId;
	private String query;
	private AID receiver;
	
	public BDDLunchUpdateRequestBehaviour(String conversationId, String query,  AID replyTo) {
		this.conversationId = conversationId;
		this.query = query;
		this.receiver = replyTo;
	}
	
	@Override
	public void action() {
		System.out.println("BDD Update reçu = " + query);
		Connection connection = ((BDDAgent)myAgent).connectDatabase();
		try {
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			System.out.println(result);
			myAgent.send(this.createMessage(result));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		((BDDAgent)myAgent).disconnectDatabase(connection);
	}
	
	private ACLMessage createMessage(int answer) {
		ACLMessage message;
		if(answer == 0)
			message = new ACLMessage(ACLMessage.FAILURE);
		else
			message = new ACLMessage(ACLMessage.CONFIRM);
		message.addReceiver(receiver);
		message.setConversationId(conversationId);
		return message;
	}
	
}

