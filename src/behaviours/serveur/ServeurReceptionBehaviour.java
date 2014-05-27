package behaviours.serveur;

import java.io.IOException;

import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.DataMessage;
import Messages.UserMessage;
import Messages.clientcontent.ClientSynchronizeMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Ce behaviour attend et lit les messages reçus, et définit la chose à faire
 * 
 * @author Nicolas
 */
public class ServeurReceptionBehaviour extends CyclicBehaviour{

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
				String contenu = null;
				try {
					DataMessage msg = omap.readValue(message.getContent(),DataMessage.class);
					type = msg.getDeviceType();
					user = msg.getUser();
					contenu = msg.getContent();
					
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
					case CREE_PROJET:
						break;
					case EFFACE_PROJET:
						break;
					case AJOUT_MEMBRE:
						break;
					case CREE_COMPTE:
						break;
					case ARCHIVER_SPRINT:
					case CREE_SOUS_TACHE:
					case CREE_SPRINT:
					case CREE_TACHE:
					case EFFACE_SPINT:
					case MODIFIER_SOUS_TACHE:
					case MODIFIE_PROJET:
					case MODIFIE_TACHE:
					case RETRAIT_MEMBRE:
					case SUPPRIMER_SOUS_TACHE:
					case SUPPRIMER_TACHE:
					case SYNCHRONIZE_DOWN:
						myAgent.addBehaviour(ajoutBehaviourSynchronist(contenu, user.getAid()));
						break;
					case SYNCHRONIZE_UP:
					default:
						break;
					
					}
				}
			}
		}
		
		private ServeurSynchronistRequestBehaviour ajoutBehaviourSynchronist(String contenu, AID aid) {
			ObjectMapper omap = new ObjectMapper();
			int userId = 0;
			int timeStamp = 0;
			try {
				ClientSynchronizeMessage msg = omap.readValue(contenu, ClientSynchronizeMessage.class);
				userId = msg.getUserId();
				timeStamp = msg.getTimestamp();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			return new ServeurSynchronistRequestBehaviour(userId, timeStamp, aid);
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
