package behaviours.bdd;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Agents.BDDAgent;
import Datas.Project;
import Datas.Sprint;
import Datas.Utilisateur;
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
	private Utilisateur user;
	
	public BDDLunchSelectRequestBehaviour(String conversationId, String query, Utilisateur user, AID replyTo, DeviceInfoTypes demande) {
		this.conversationId = conversationId;
		this.query = query;
		this.receiver = replyTo;
		this.demande = demande;
		this.user = user;
	}
	
	@Override
	public void action() {
		System.out.println("BDD Select reçu -> " + query);
		System.out.println("BDD Select conversationId -> " + conversationId);
		Connection connection = ((BDDAgent)myAgent).connectDatabase();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			ACLMessage reply = this.createMessage(result);
			myAgent.send(reply);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		((BDDAgent)myAgent).disconnectDatabase(connection);
	}
	
	private ACLMessage createMessage(ResultSet result) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(receiver);
		message.setContent(writeContent(result));
		message.setConversationId(conversationId);
		return message;
	}
	
	private String writeContent(ResultSet result) {
		BDDAnswerMessage corps = new BDDAnswerMessage();
		ObjectMapper omap = new ObjectMapper();
		switch(demande){
			case ALL_USERS:
				corps.setMesUsers(handleResultSetUser(result));
				corps.setDemande(demande);
				corps.setUser(user);
				corps.setTable("Users");
				break;
			case IS_USER:
				corps.setMesUsers(handleResultSetUser(result));
				corps.setDemande(demande);
				corps.setTable("Users");
				corps.setUser(user);
				break;
			case GET_PROJECT:
				corps.setProject(this.handleResultSetProject(result));
				corps.setDemande(demande);
				corps.setTable("Users");
				corps.setUser(user);
				break;
			case SELECT_LAST_SPRINT:
				corps.setSprint(this.handleResultSetSprint(result));
				corps.setDemande(demande);
				corps.setTable("Users");
				corps.setUser(user);
			default:
				break;
		}
		String messageCorps = "";
		try {
			messageCorps = omap.writeValueAsString(corps);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return messageCorps;	
	}
	
	private Sprint handleResultSetSprint(ResultSet result) {
		Sprint sprint = new Sprint();
		try {
			if(result.first())
			{
				sprint.setId(result.getInt(1));
				sprint.setProject(result.getInt(2));
				sprint.setNumber(result.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sprint;
	}

	private Project handleResultSetProject(ResultSet result)
	{
		Project proj = new Project();
		try {
			if(result.first())
			{
				proj.setId(result.getInt(1));
				proj.setTitle(result.getString(2));
				proj.setSubTitle(result.getString(3));
				proj.setDescription(result.getString(4));
				proj.setCreation_date(result.getTimestamp(5));
				proj.setLast_update(result.getTimestamp(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return proj;
	}
	
	private List<Utilisateur> handleResultSetUser(ResultSet result){
		List<Utilisateur> userList = new ArrayList<Utilisateur>();
		try {
			if(result.first()){
				 do{
				  Utilisateur u = new Utilisateur();
				  u.setId(result.getInt(1));
				  u.setEmail(result.getString(2));
				  u.setPassword(result.getString(3));
				  u.setFirstname(result.getString(4));
				  u.setSalt1(result.getString(5));
				  userList.add(u);
				 }while(result.next());
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}
	
	private Object select() {
		return null;
	}

}
