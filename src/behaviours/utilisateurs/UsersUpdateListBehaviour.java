package behaviours.utilisateurs;

import java.io.IOException;

import Messages.UserMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Agents.UtilisateursAgent;

public class UsersUpdateListBehaviour extends CyclicBehaviour {

	/**
	 * L'agent attend un message de type inform qui indique qu'un utilisateur est connecté ou déconnecter.
	 * L'objectif est de mettre à jour la liste des utilisateurs connectés.
	 */
	private static final long serialVersionUID = 4509112375330341972L;


	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchSender(getServeurAgent())));
		if (message != null) {
			System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
			ObjectMapper omap = new ObjectMapper();
			try {
				UserMessage userMsg = omap.readValue(message.getContent(),UserMessage.class);
				//Ajout d'utilisateur
				if (userMsg.getAction().equals("connexion")){
					((UtilisateursAgent) myAgent).addUtilisateursConnectés(userMsg.getMonUser());
				}
				// suppression d'utilisateur
				else if (userMsg.getAction().equals("deconnexion")){
					((UtilisateursAgent) myAgent).removeUtilisateursConnectés(userMsg.getMonUser());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	private AID getServeurAgent() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Serveur");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			return result[0].getName();
		} catch(FIPAException fe) {
			fe.printStackTrace();
		}
		return null;
	}
}
