package Agents;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

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
