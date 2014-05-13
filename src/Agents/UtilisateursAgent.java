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

	private List<Utilisateur> utilisateurConnect�s;
	
	@Override
	public void setup() {
		super.setup();
		utilisateurConnect�s = new ArrayList<>();
	}
	
}
