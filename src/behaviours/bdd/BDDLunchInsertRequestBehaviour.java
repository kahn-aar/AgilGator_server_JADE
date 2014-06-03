package behaviours.bdd;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Agents.BDDAgent;

/**
 * Behaviour gérant les requêtes en "insert" : retourne l'ID de l'élément ajouté
 * 
 * @author Yoann
 *
 */

public class BDDLunchInsertRequestBehaviour extends OneShotBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String conversationId;
	private String query1;
	private String query2;
	private AID receiver;
	private int id = -1;

	public BDDLunchInsertRequestBehaviour(String conversationId, String query1, String query2,  AID replyTo) {
		this.conversationId = conversationId;
		this.query1 = query1;
		this.query2 = query2;
		this.receiver = replyTo;
	}
	
	@Override
	public void action() {
		System.out.println("BDD reçu = " + query1);
		Connection connection = ((BDDAgent)myAgent).connectDatabase();
		if (connection != null){
			System.out.println("Connexion de la database");
			try {
				Statement statement = connection.createStatement();
				if(query1!=null){
					int result = statement.executeUpdate(query1);
					if(result == 0)
						this.id = -1;
					else
					{
						if(query2!=null){
							ResultSet resultSet = statement.executeQuery(query2);
							this.id = resultSet.getInt("id");
						}
					}
					myAgent.send(this.createMessage());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			((BDDAgent)myAgent).disconnectDatabase(connection);
		}
		else{
			System.out.println("FAIL");
		}
	}
	
	private ACLMessage createMessage() {
		ACLMessage message;
		if(id == -1)
			message = new ACLMessage(ACLMessage.FAILURE);
		else
		{
			message = new ACLMessage(ACLMessage.CONFIRM);
			message.setContent(String.valueOf(id));
		}
		message.addReceiver(receiver);
		message.setConversationId(conversationId);
		return message;
	}

}
