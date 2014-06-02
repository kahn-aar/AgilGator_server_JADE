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
 * Agent connaissant les utilisateurs connect�s
 * 
 * @author Nicolas
 *
 */
public class UtilisateursAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private List<Utilisateur> utilisateurConnect�s;
	
	@Override
	public void setup() {
		super.setup();
		utilisateurConnect�s = new ArrayList<>();
		// Ajout des behaviours de type waiting
		this.addBehaviour(new UsersUpdateListBehaviour() );
		//Enregistrement de l'agent aupr�s du DF
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
	
	public List<Utilisateur> getUtilisateursConnect�s(){
		return utilisateurConnect�s;
		
	}
	
	public void addUtilisateursConnect�s(Utilisateur user){
		utilisateurConnect�s.add(user);
		
	}
	public void removeUtilisateursConnect�s(Utilisateur user){
		utilisateurConnect�s.remove(user);
		
	}
}
