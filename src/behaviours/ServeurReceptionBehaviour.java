package behaviours;

import java.io.IOException;

import Datas.DeviceInfoTypes;
import Datas.Utilisateur;
import Messages.DataMessage;
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

public class ServeurReceptionBehaviour extends CyclicBehaviour{
	/**
	 * Ce behaviour attend et lit les messages reçus, et définit la chose à faire
	 * 
	 * @author Nicolas
	 */
	private static final long serialVersionUID = 1L;
	
	private String conversationId;

		@Override
		public void action() {
			ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE));
			if (message != null) {
				System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
				ObjectMapper omap = new ObjectMapper();
				DeviceInfoTypes type = null;
				Utilisateur user = null;
				try {
					DataMessage msg = omap.readValue(message.getContent(),DataMessage.class);
					type = msg.getDeviceType();
					user = msg.getUser();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(type!=null){
					switch(type){
					case CONNEXION:
						// envoie à l'agent User l'utilisateur à ajouter à la liste
						if(user!=null){
							ObjectMapper omapConnexion = new ObjectMapper();
							try {
								UserMessage um = new UserMessage();
								um.setAction("connexion");
								um.setMonUser(user);
								String msgConnexionContent = omapConnexion.writeValueAsString(um);
								ACLMessage msgConnexion = new ACLMessage(ACLMessage.REQUEST);
								msgConnexion.addReceiver(getUtilisateursAgent());
								msgConnexion.setContent(msgConnexionContent);
								msgConnexion.setConversationId(conversationId);
								msgConnexion.setLanguage("JSON");
								myAgent.send(message);
								
								
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						break;
					case DECONNEXION:
						// envoie à l'agent User l'utilisateur à enlever de la liste
						if(user!=null){
							ObjectMapper omapConnexion = new ObjectMapper();
							try {
								UserMessage um = new UserMessage();
								um.setAction("deconnexion");
								um.setMonUser(user);
								String msgDeconnexionContent = omapConnexion.writeValueAsString(um);
								ACLMessage msgDeconnexion = new ACLMessage(ACLMessage.REQUEST);
								msgDeconnexion.addReceiver(getUtilisateursAgent());
								msgDeconnexion.setContent(msgDeconnexionContent);
								msgDeconnexion.setConversationId(conversationId);
								msgDeconnexion.setLanguage("JSON");
								myAgent.send(message);
								
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						break;
					default:
						break;
					
					}
				}
			}
		}
		private AID getUtilisateursAgent() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Utilisateurs");
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
