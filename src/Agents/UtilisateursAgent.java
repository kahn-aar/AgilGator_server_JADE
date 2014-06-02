package Agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.ArrayList;
import java.util.List;

import behaviours.tache.TacheWaitingRequestBehaviour;
import behaviours.utilisateurs.UsersUpdateListBehaviour;
import Datas.Utilisateur;

/**
 * Agent connaissant les utilisateurs connectés
 * 
 * @author Nicolas
 *
 */
public class UtilisateursAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private List<Utilisateur> utilisateurConnectés;
	
	@Override
	public void setup() {
		super.setup();
		utilisateurConnectés = new ArrayList<>();
		// Ajout des behaviours de type waiting
		this.addBehaviour(new UsersUpdateListBehaviour() );
		//Enregistrement de l'agent auprès du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Utilisateurs");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	
	public List<Utilisateur> getUtilisateursConnectés(){
		return utilisateurConnectés;
		
	}
	
	public void addUtilisateursConnectés(Utilisateur user){
		utilisateurConnectés.add(user);
		
	}
	public void removeUtilisateursConnectés(Utilisateur user){
		utilisateurConnectés.remove(user);
		
	}
}
