package behaviours.serveur;

import java.io.IOException;

import Datas.Project;
import Datas.Sprint;
import Datas.SubTask;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.DataMessage;
import Messages.ProjetRequestMessage;
import Messages.SousTacheRequestMessage;
import Messages.SprintRequestMessage;
import Messages.TacheRequestMessage;
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
				Sprint sprint = null;
				Utilisateur member = null;
				try {
					DataMessage msg = omap.readValue(message.getContent(),DataMessage.class);
					demande = msg.getDemande();
					user = msg.getUser();
					projet = msg.getProjet();
					tache = msg.getTache();
					soustache = msg.getSousTache();
					sprint = msg.getSprint();
					member = msg.getMember();
					
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
						projetMsg.setUser(user);
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
						ObjectMapper omapEffaceProjet = new ObjectMapper();
						ProjetRequestMessage effaceProjetMsg= new ProjetRequestMessage();
						effaceProjetMsg.setDemande(demande);
						effaceProjetMsg.setProjet(projet);
						try {
							String content4 = omapEffaceProjet.writeValueAsString(effaceProjetMsg);
							ACLMessage msgCreeProjet= new ACLMessage(ACLMessage.REQUEST);
							msgCreeProjet.addReceiver(getProjetAgent());
							msgCreeProjet.setContent(content4);
							msgCreeProjet.setConversationId(conversationId);
							msgCreeProjet.setLanguage("JSON");
							myAgent.send(msgCreeProjet);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case AJOUT_MEMBRE:
						ObjectMapper omapAjoutMembre = new ObjectMapper();
						ProjetRequestMessage ajoutMembreProjetMsg= new ProjetRequestMessage();
						ajoutMembreProjetMsg.setDemande(demande);
						ajoutMembreProjetMsg.setProjet(projet);
						ajoutMembreProjetMsg.setMember(member);
						try {
							String content4 = omapAjoutMembre.writeValueAsString(ajoutMembreProjetMsg);
							ACLMessage msgAM= new ACLMessage(ACLMessage.REQUEST);
							msgAM.addReceiver(getProjetAgent());
							msgAM.setContent(content4);
							msgAM.setConversationId(conversationId);
							msgAM.setLanguage("JSON");
							myAgent.send(msgAM);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case CREE_COMPTE:
						ObjectMapper omapCC= new ObjectMapper();
						ProjetRequestMessage CCMsg= new ProjetRequestMessage();
						CCMsg.setDemande(demande);
						CCMsg.setUser(user);
						try {
							String content4 = omapCC.writeValueAsString(CCMsg);
							ACLMessage msgCC= new ACLMessage(ACLMessage.REQUEST);
							msgCC.addReceiver(getProjetAgent());
							msgCC.setContent(content4);
							msgCC.setConversationId(conversationId);
							msgCC.setLanguage("JSON");
							myAgent.send(msgCC);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case ARCHIVER_SPRINT:
						ObjectMapper omapArchiverSprint= new ObjectMapper();
						SprintRequestMessage ArchiverSprintMsg= new SprintRequestMessage();
						ArchiverSprintMsg.setDemande(demande);
						ArchiverSprintMsg.setSprint(sprint);
						try {
							String content4 = omapArchiverSprint.writeValueAsString(ArchiverSprintMsg);
							ACLMessage msgArchiverSprint= new ACLMessage(ACLMessage.REQUEST);
							msgArchiverSprint.addReceiver(getSprintAgent());
							msgArchiverSprint.setContent(content4);
							msgArchiverSprint.setConversationId(conversationId);
							msgArchiverSprint.setLanguage("JSON");
							myAgent.send(msgArchiverSprint);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case CREE_SOUS_TACHE:
						ObjectMapper omapCreeSubTask = new ObjectMapper();
						SousTacheRequestMessage creeSubTaskMsg= new SousTacheRequestMessage();
						creeSubTaskMsg.setDemande(demande);
						creeSubTaskMsg.setSousTache(soustache);
						try {
							String content5 = omapCreeSubTask.writeValueAsString(creeSubTaskMsg);
							ACLMessage msgCreeSubTask= new ACLMessage(ACLMessage.REQUEST);
							msgCreeSubTask.addReceiver(getSubTaskAgent());
							msgCreeSubTask.setContent(content5);
							msgCreeSubTask.setConversationId(conversationId);
							msgCreeSubTask.setLanguage("JSON");
							myAgent.send(msgCreeSubTask);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case CREE_SPRINT:
						ObjectMapper omapSprint = new ObjectMapper();
						SprintRequestMessage sprintMsg= new SprintRequestMessage();
						sprintMsg.setDemande(demande);
						sprintMsg.setSprint(sprint);
						try {
							String content5 = omapSprint.writeValueAsString(sprintMsg);
							ACLMessage msgSprint= new ACLMessage(ACLMessage.REQUEST);
							msgSprint.addReceiver(getSprintAgent());
							msgSprint.setContent(content5);
							msgSprint.setConversationId(conversationId);
							msgSprint.setLanguage("JSON");
							myAgent.send(msgSprint);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case CREE_TACHE:
						ObjectMapper omapCreeTask = new ObjectMapper();
						TacheRequestMessage creeTaskMsg= new TacheRequestMessage();
						creeTaskMsg.setDemande(demande);
						creeTaskMsg.setTache(tache);
						try {
							String content5 = omapCreeTask.writeValueAsString(creeTaskMsg);
							ACLMessage msgCreeTask= new ACLMessage(ACLMessage.REQUEST);
							msgCreeTask.addReceiver(getTaskAgent());
							msgCreeTask.setContent(content5);
							msgCreeTask.setConversationId(conversationId);
							msgCreeTask.setLanguage("JSON");
							myAgent.send(msgCreeTask);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case EFFACE_SPRINT:
						ObjectMapper omapEffaceSprint = new ObjectMapper();
						SprintRequestMessage effaceSprintMsg= new SprintRequestMessage();
						effaceSprintMsg.setDemande(demande);
						effaceSprintMsg.setSprint(sprint);
						try {
							String content5 = omapEffaceSprint.writeValueAsString(effaceSprintMsg);
							ACLMessage msgEffaceSprint= new ACLMessage(ACLMessage.REQUEST);
							msgEffaceSprint.addReceiver(getSprintAgent());
							msgEffaceSprint.setContent(content5);
							msgEffaceSprint.setConversationId(conversationId);
							msgEffaceSprint.setLanguage("JSON");
							myAgent.send(msgEffaceSprint);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case MODIFIER_SOUS_TACHE:
						ObjectMapper omapModifySubTask = new ObjectMapper();
						SousTacheRequestMessage modifySubTaskMsg= new SousTacheRequestMessage();
						modifySubTaskMsg.setDemande(demande);
						modifySubTaskMsg.setSousTache(soustache);
						try {
							String content5 = omapModifySubTask.writeValueAsString(modifySubTaskMsg);
							ACLMessage msgModifySubTask= new ACLMessage(ACLMessage.REQUEST);
							msgModifySubTask.addReceiver(getSubTaskAgent());
							msgModifySubTask.setContent(content5);
							msgModifySubTask.setConversationId(conversationId);
							msgModifySubTask.setLanguage("JSON");
							myAgent.send(msgModifySubTask);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case MODIFIE_PROJET:
						ObjectMapper omapModifyProjet = new ObjectMapper();
						ProjetRequestMessage modifyProjetMsg= new ProjetRequestMessage();
						modifyProjetMsg.setDemande(demande);
						modifyProjetMsg.setProjet(projet);
						try {
							String content5 = omapModifyProjet.writeValueAsString(modifyProjetMsg);
							ACLMessage msgModifyProjet= new ACLMessage(ACLMessage.REQUEST);
							msgModifyProjet.addReceiver(getProjetAgent());
							msgModifyProjet.setContent(content5);
							msgModifyProjet.setConversationId(conversationId);
							msgModifyProjet.setLanguage("JSON");
							myAgent.send(msgModifyProjet);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case MODIFIE_TACHE:
						ObjectMapper omapModifyTask = new ObjectMapper();
						TacheRequestMessage modifyTaskMsg= new TacheRequestMessage();
						modifyTaskMsg.setDemande(demande);
						modifyTaskMsg.setTache(tache);
						try {
							String content5 = omapModifyTask.writeValueAsString(modifyTaskMsg);
							ACLMessage msgModifyTask= new ACLMessage(ACLMessage.REQUEST);
							msgModifyTask.addReceiver(getTaskAgent());
							msgModifyTask.setContent(content5);
							msgModifyTask.setConversationId(conversationId);
							msgModifyTask.setLanguage("JSON");
							myAgent.send(msgModifyTask);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case RETRAIT_MEMBRE:
						ObjectMapper omapRetraitMembre = new ObjectMapper();
						ProjetRequestMessage retraitMembreProjetMsg= new ProjetRequestMessage();
						retraitMembreProjetMsg.setDemande(demande);
						retraitMembreProjetMsg.setProjet(projet);
						retraitMembreProjetMsg.setMember(member);
						try {
							String content4 = omapRetraitMembre.writeValueAsString(retraitMembreProjetMsg);
							ACLMessage msgRM= new ACLMessage(ACLMessage.REQUEST);
							msgRM.addReceiver(getProjetAgent());
							msgRM.setContent(content4);
							msgRM.setConversationId(conversationId);
							msgRM.setLanguage("JSON");
							myAgent.send(msgRM);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case AJOUT_MANAGER:
						ObjectMapper omapManager = new ObjectMapper();
						ProjetRequestMessage managerMsg= new ProjetRequestMessage();
						managerMsg.setDemande(demande);
						managerMsg.setProjet(projet);
						managerMsg.setUser(user);
						try {
							String content4 = omapManager.writeValueAsString(managerMsg);
							ACLMessage msgManager= new ACLMessage(ACLMessage.REQUEST);
							msgManager.addReceiver(getProjetAgent());
							msgManager.setContent(content4);
							msgManager.setConversationId(conversationId);
							msgManager.setLanguage("JSON");
							myAgent.send(msgManager);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case SUPPRIMER_SOUS_TACHE:
						ObjectMapper omapSuppSubTask = new ObjectMapper();
						SousTacheRequestMessage suppSubTaskMsg= new SousTacheRequestMessage();
						suppSubTaskMsg.setDemande(demande);
						suppSubTaskMsg.setSousTache(soustache);
						try {
							String content5 = omapSuppSubTask.writeValueAsString(suppSubTaskMsg);
							ACLMessage msgSuppSubTask= new ACLMessage(ACLMessage.REQUEST);
							msgSuppSubTask.addReceiver(getSubTaskAgent());
							msgSuppSubTask.setContent(content5);
							msgSuppSubTask.setConversationId(conversationId);
							msgSuppSubTask.setLanguage("JSON");
							myAgent.send(msgSuppSubTask);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case SUPPRIMER_TACHE:
						ObjectMapper omapSuppTask = new ObjectMapper();
						TacheRequestMessage suppTaskMsg= new TacheRequestMessage();
						suppTaskMsg.setDemande(demande);
						suppTaskMsg.setTache(tache);
						try {
							String content5 = omapSuppTask.writeValueAsString(suppTaskMsg);
							ACLMessage msgSuppTask= new ACLMessage(ACLMessage.REQUEST);
							msgSuppTask.addReceiver(getTaskAgent());
							msgSuppTask.setContent(content5);
							msgSuppTask.setConversationId(conversationId);
							msgSuppTask.setLanguage("JSON");
							myAgent.send(msgSuppTask);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case SYNCHRONIZE_DOWN:
						ObjectMapper omapDown = new ObjectMapper();
						ProjetRequestMessage downMsg= new ProjetRequestMessage();
						downMsg.setDemande(demande);
						downMsg.setProjet(projet);
						try {
							String content4 = omapDown.writeValueAsString(downMsg);
							ACLMessage msgDown= new ACLMessage(ACLMessage.REQUEST);
							msgDown.addReceiver(getProjetAgent());
							msgDown.setContent(content4);
							msgDown.setConversationId(conversationId);
							msgDown.setLanguage("JSON");
							myAgent.send(msgDown);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case SYNCHRONIZE_UP:
						ObjectMapper omapUP = new ObjectMapper();
						ProjetRequestMessage upMsg= new ProjetRequestMessage();
						upMsg.setDemande(demande);
						upMsg.setProjet(projet);
						try {
							String content4 = omapUP.writeValueAsString(upMsg);
							ACLMessage msgUP= new ACLMessage(ACLMessage.REQUEST);
							msgUP.addReceiver(getProjetAgent());
							msgUP.setContent(content4);
							msgUP.setConversationId(conversationId);
							msgUP.setLanguage("JSON");
							myAgent.send(msgUP);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					default:
						break;
					
					}
				}
			}
		}
		
		private AID getTaskAgent() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Tache");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template);
				return result[0].getName();
			} catch(FIPAException fe) {
				fe.printStackTrace();
			}
			return null;
		}

		private AID getSprintAgent() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Sprint");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template);
				return result[0].getName();
			} catch(FIPAException fe) {
				fe.printStackTrace();
			}
			return null;
		}

		private AID getSubTaskAgent() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("SousTache");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template);
				return result[0].getName();
			} catch(FIPAException fe) {
				fe.printStackTrace();
			}
			return null;
		}

		private AID getProjetAgent() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Projet");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template);
				return result[0].getName();
			} catch(FIPAException fe) {
				fe.printStackTrace();
			}
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
