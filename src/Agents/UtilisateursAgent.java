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

	private List<Utilisateur> utilisateurConnectés;
	
	@Override
	public void setup() {
		super.setup();
		utilisateurConnectés = new ArrayList<>();
	}
	
}
