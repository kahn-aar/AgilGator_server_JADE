package Agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import behaviours.bdd.BDDWaitingRequestBehaviour;

/**
 * Agent gérant la base de données. Lui seul en a l'accès.
 * 
 * @author Nicolas
 *
 */
public class BDDAgent extends Agent {
	
	private static final long serialVersionUID = 1L;
	private String database = "//localhost:3306/agilgator"; //Adresse de la BDD
	private String username = "root"; //Username pour la BDD
	private String password = "agilgatormonkey28"; //Mot de passe de la BDD

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
			Connection connection = DriverManager.getConnection("jdbc:mysql:"+this.database, this.username, this.password);
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

}
