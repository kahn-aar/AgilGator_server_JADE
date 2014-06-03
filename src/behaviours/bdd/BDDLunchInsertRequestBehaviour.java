package behaviours.bdd;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Agents.BDDAgent;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.BDDAnswerMessage;

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
	private DeviceInfoTypes demande;
	private Utilisateur user;
	private int id;
	
	public BDDLunchInsertRequestBehaviour(String conversationId, String query1, String query2, Utilisateur user, AID replyTo, DeviceInfoTypes demande) {
		this.conversationId = conversationId;
		this.query1 = query1;
		this.query2 = query2;
		this.receiver = replyTo;
		this.demande = demande;
		this.user = user;
	}
	
	
	@Override
	public void action() {
		System.out.println("BDD reçu = " + query1);
		System.out.println("BDD reçu = " + query2);
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
							ResultSet rs = statement.executeQuery(query2);
							if(rs.first()){
								 do{
								  this.id = rs.getInt(1);
								 }while(rs.next());
								}
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
			message = new ACLMessage(ACLMessage.INFORM);
			BDDAnswerMessage answer = new BDDAnswerMessage();
			answer.setDemande(demande);
			answer.setId(id);
			ObjectMapper omap = new ObjectMapper();
			String messageCorps;
			try {
				messageCorps = omap.writeValueAsString(answer);
				message.setContent(messageCorps);
				message.addReceiver(receiver);
				System.out.println(receiver.getName());
				message.setConversationId(conversationId);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return message;
	}
}
