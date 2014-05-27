package behaviours.utilisateurs;

import java.io.IOException;
import Messages.UserMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import Agents.UtilisateursAgent;

public class UsersUpdateListBehaviour extends CyclicBehaviour {

	/**
	 * L'agent attend un message de type subsribe ou inform qui indique qu'un utilisateur est connect� ou d�connecter.
	 * L'objectif est de mettre � jour la liste des utilisateurs connect�s.
	 */
	private static final long serialVersionUID = 4509112375330341972L;


	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		if (message != null) {
			System.out.println(myAgent.getLocalName() + " re�u -> " + message.getContent());
			ObjectMapper omap = new ObjectMapper();
			try {
				UserMessage userMsg = omap.readValue(message.getContent(),UserMessage.class);
				//Ajout d'utilisateur
				if (userMsg.getAction().equals("connexion")){
					((UtilisateursAgent) myAgent).addUtilisateursConnect�s(userMsg.getMonUser());
				}
				else if (userMsg.getAction().equals("connexion")){
					((UtilisateursAgent) myAgent).addUtilisateursConnect�s(userMsg.getMonUser());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}