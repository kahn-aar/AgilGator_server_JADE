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
	 * L'agent attend un message de type inform qui indique qu'un utilisateur est connect� ou d�connecter.
	 * L'objectif est de mettre � jour la liste des utilisateurs connect�s.
	 */
	private static final long serialVersionUID = 4509112375330341972L;


	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchSender(getServeurAgent())));
		if (message != null) {
			System.out.println(myAgent.getLocalName() + " re�u -> " + message.getContent());
			ObjectMapper omap = new ObjectMapper();
			try {
				UserMessage userMsg = omap.readValue(message.getContent(),UserMessage.class);
				//Ajout d'utilisateur
				if (userMsg.getAction().equals("connexion")){
					((UtilisateursAgent) myAgent).addUtilisateursConnect�s(userMsg.getMonUser());
				}
				// suppression d'utilisateur
				else if (userMsg.getAction().equals("deconnexion")){
					((UtilisateursAgent) myAgent).removeUtilisateursConnect�s(userMsg.getMonUser());
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
