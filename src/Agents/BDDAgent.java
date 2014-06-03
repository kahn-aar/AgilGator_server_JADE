package Agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import behaviours.bdd.BDDWaitingRequestBehaviour;

/**
 * Agent gérant la base de données. Lui seul en a l'accès.
 * 
 * @author Nicolas
 *
 */
public class BDDAgent extends Agent {
	
	private static final long serialVersionUID = 1L;
	private static final String DATABASE = "//localhost:3306/agilgator"; //Adresse de la BDD
	private static final String USERNAME = "root"; //Username pour la BDD
	private static final String PASSWORD = ""; //Mot de passe de la BDD agilgatormonkey28

	@Override
	public void setup() {
		super.setup();
		this.addBehaviour(new BDDWaitingRequestBehaviour());

		//Enregistrement de l'agent auprès du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("BDD");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	
	//Connection à la base de données
	public Connection connectDatabase()
	{
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql:"+this.DATABASE, this.USERNAME, this.PASSWORD);
			if(connection==null){
				connection = DriverManager.getConnection("jdbc:mysql://172.25.27.53:3306", this.USERNAME, this.PASSWORD);
			}
		return connection;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	//Ferme la connection
	public void disconnectDatabase(Connection connection)
	{
		try{
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	//Test
	public void testBDD()
	{
		Connection connect = this.connectDatabase();
		try {
			Statement statement = connect.createStatement();
			ResultSet resultat = statement.executeQuery("SELECT * FROM Users;");
			while (resultat.next()) 
			{
			    int id = resultat.getInt("id");
			    String mail = resultat.getString("email");
			    String pass = resultat.getString("password");
			    String username = resultat.getString("pseudo");
			    
			    System.out.println(id + " " + mail + " " + pass + " " + username);
			}
			this.disconnectDatabase(connect);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
