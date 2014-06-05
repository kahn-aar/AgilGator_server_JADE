package Agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import Datas.Utilisateur;
import behaviours.serveur.ServeurReceptionBehaviour;
import behaviours.serveur.ServeurWaitingBehaviour;

/**
 * Agent de coordination sur le serveur,
 * toutes les informations transitent par lui.
 * 
 * @author Nicolas
 *
 */
public class ServeurAgent extends Agent {

	private Utilisateur user; // garde en m�moire l'utilisateur qui fait la demande
	private static final long serialVersionUID = 1L;

	@Override
	public void setup() {
		super.setup();
		
		//Enregistrement de l'agent aupr�s du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Serveur");
		sd.setName("Serveur " + getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		//Ajout du behaviour de base
		this.addBehaviour(new ServeurReceptionBehaviour());
		this.addBehaviour(new ServeurWaitingBehaviour());
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}
}

