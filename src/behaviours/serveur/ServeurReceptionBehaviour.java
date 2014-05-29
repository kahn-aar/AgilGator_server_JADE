package behaviours.serveur;

import java.io.IOException;

import Datas.Project;
import Datas.SubTask;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.DataMessage;
import Messages.ProjetRequestMessage;
import Messages.UserMessage;
import Messages.clientcontent.ClientSynchronizeMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
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
			ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE), MessageTemplate.MatchSender(getLiaisonAgent())));
			if (message != null) {
				System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
				ObjectMapper omap = new ObjectMapper();
				DeviceInfoTypes demande = null;
				Utilisateur user = null;
				Project projet = null;
				Task tache = null;
				SubTask soustache = null;
				try {
					DataMessage msg = omap.readValue(message.getContent(),DataMessage.class);
					demande = msg.getDemande();
					user = msg.getUser();
					projet = msg.getProjet();
					tache = msg.getTache();
					soustache = msg.getSousTache();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(demande!=null){
					switch(demande){
					case CONNEXION:
						// envoie à l'agent User l'utilisateur à ajouter à la liste
						if(user!=null){
							ObjectMapper omapConnexion = new ObjectMapper();
							try {
								UserMessage um = new UserMessage();
								um.setAction("connexion");
								um.setMonUser(user);
								String msgConnexionContent = omapConnexion.writeValueAsString(um);
								ACLMessage msgConnexion = new ACLMessage(ACLMessage.INFORM);
								msgConnexion.addReceiver(getUtilisateursAgent());
								msgConnexion.setContent(msgConnexionContent);
								msgConnexion.setConversationId(conversationId);
								msgConnexion.setLanguage("JSON");
								myAgent.send(msgConnexion);
								
								
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
								ACLMessage msgDeconnexion = new ACLMessage(ACLMessage.INFORM);
								msgDeconnexion.addReceiver(getUtilisateursAgent());
								msgDeconnexion.setContent(msgDeconnexionContent);
								msgDeconnexion.setConversationId(conversationId);
								msgDeconnexion.setLanguage("JSON");
								myAgent.send(msgDeconnexion);
								
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						break;
					case CREE_PROJET:
						ObjectMapper omapCreeProjet = new ObjectMapper();
						ProjetRequestMessage projetMsg= new ProjetRequestMessage();
						projetMsg.setDemande(demande);
						projetMsg.setProjet(projet);
						String content;
						try {
							content = omapCreeProjet.writeValueAsString(projetMsg);
							ACLMessage msgCreeProjet= new ACLMessage(ACLMessage.REQUEST);
							msgCreeProjet.addReceiver(getProjetAgent());
							msgCreeProjet.setContent(content);
							msgCreeProjet.setConversationId(conversationId);
							msgCreeProjet.setLanguage("JSON");
							myAgent.send(msgCreeProjet);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						break;
					case EFFACE_PROJET:
						break;
					case AJOUT_MEMBRE:
						break;
					case CREE_COMPTE:
						break;
					case ARCHIVER_SPRINT:
						break;
					case CREE_SOUS_TACHE:
						break;
					case CREE_SPRINT:
						break;
					case CREE_TACHE:
						break;
					case EFFACE_SPRINT:
						break;
					case MODIFIER_SOUS_TACHE:
						break;
					case MODIFIE_PROJET:
						break;
					case MODIFIE_TACHE:
						break;
					case RETRAIT_MEMBRE:
						break;
					case SUPPRIMER_SOUS_TACHE:
						break;
					case SUPPRIMER_TACHE:
						break;
					case SYNCHRONIZE_DOWN:
						//myAgent.addBehaviour(ajoutBehaviourSynchronist(contenu, user.getAid()));
						break;
					case SYNCHRONIZE_UP:
						break;
					default:
						break;
					
					}
				}
			}
		}
		
		private AID getProjetAgent() {
			// TODO Auto-generated method stub
			return null;
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
		
		private AID getLiaisonAgent() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Liaison");
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
