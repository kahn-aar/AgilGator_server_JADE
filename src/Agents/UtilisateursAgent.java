package Agents;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

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
