package behaviours.serveur;

import java.io.IOException;

import Agents.ServeurAgent;
import Agents.UtilisateursAgent;
import Datas.Project;
import Datas.Sprint;
import Datas.SubTask;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.DataMessage;
import Messages.CompteMessage;
import Messages.ProjetRequestMessage;
import Messages.SousTacheRequestMessage;
import Messages.SprintRequestMessage;
import Messages.TacheRequestMessage;
import Messages.UserMessage;
import Messages.clientcontent.ClientSynchronizeMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.Module.SetupContext;

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

		@Override
		public void action() {
			ACLMessage message = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchSender(getLiaisonAgent())));
			if (message != null) {
				String conversationId = message.getConversationId();
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
					// Enregistrement du user qui fait la demande
					((ServeurAgent) myAgent).setUser(user);
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
					case MEMBRES_DU_PROJET:
						if(projet!=null){
							ObjectMapper omapLM = new ObjectMapper();
							ProjetRequestMessage lmMsg= new ProjetRequestMessage();
							lmMsg.setDemande(demande);
							lmMsg.setProjet(projet);
							try {
								String content = omapLM.writeValueAsString(lmMsg);
								ACLMessage msgLM= new ACLMessage(ACLMessage.PROPAGATE);
								msgLM.addReceiver(getProjetAgent());
								msgLM.setContent(content);
								msgLM.setConversationId(conversationId);
								msgLM.setLanguage("JSON");
								myAgent.send(msgLM);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
						break;
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
						if (projet != null){
							ObjectMapper omapCreeProjet = new ObjectMapper();
							ProjetRequestMessage projetMsg= new ProjetRequestMessage();
							projetMsg.setDemande(demande);
							projetMsg.setProjet(projet);
							projetMsg.setUser(user);
							String content;
							try {
								content = omapCreeProjet.writeValueAsString(projetMsg);
								ACLMessage msgCreeProjet= new ACLMessage(ACLMessage.PROPAGATE);
								msgCreeProjet.addReceiver(getProjetAgent());
								msgCreeProjet.setContent(content);
								System.out.println(content);
								msgCreeProjet.setConversationId(conversationId);
								msgCreeProjet.setLanguage("JSON");
								myAgent.send(msgCreeProjet);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case EFFACE_PROJET:
						if(projet!=null){
							ObjectMapper omapEffaceProjet = new ObjectMapper();
							ProjetRequestMessage effaceProjetMsg= new ProjetRequestMessage();
							effaceProjetMsg.setDemande(demande);
							effaceProjetMsg.setProjet(projet);
							effaceProjetMsg.setUser(user);
							try {
								String content = omapEffaceProjet.writeValueAsString(effaceProjetMsg);
								ACLMessage msgCreeProjet= new ACLMessage(ACLMessage.PROPAGATE);
								msgCreeProjet.addReceiver(getProjetAgent());
								msgCreeProjet.setContent(content);
								msgCreeProjet.setConversationId(conversationId);
								msgCreeProjet.setLanguage("JSON");
								myAgent.send(msgCreeProjet);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case AJOUT_MEMBRE:
						if(member!=null){
							ObjectMapper omapAjoutMembre = new ObjectMapper();
							ProjetRequestMessage ajoutMembreProjetMsg= new ProjetRequestMessage();
							ajoutMembreProjetMsg.setDemande(demande);
							ajoutMembreProjetMsg.setProjet(projet);
							ajoutMembreProjetMsg.setMember(member);
							ajoutMembreProjetMsg.setUser(user);
							try {
								String content4 = omapAjoutMembre.writeValueAsString(ajoutMembreProjetMsg);
								ACLMessage msgAM= new ACLMessage(ACLMessage.PROPAGATE);
								msgAM.addReceiver(getProjetAgent());
								msgAM.setContent(content4);
								msgAM.setConversationId(conversationId);
								msgAM.setLanguage("JSON");
								myAgent.send(msgAM);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case CREE_COMPTE:
						if (user!=null){
							ObjectMapper omapCC= new ObjectMapper();
							CompteMessage CCMsg= new CompteMessage();
							CCMsg.setDemande(demande);
							CCMsg.setUser(user);
							try {
								String content = omapCC.writeValueAsString(CCMsg);
								ACLMessage msgCC= new ACLMessage(ACLMessage.REQUEST);
								msgCC.addReceiver(getCompteAgent());
								msgCC.setContent(content);
								msgCC.setConversationId(conversationId);
								msgCC.setLanguage("JSON");
								myAgent.send(msgCC);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case ALL_USERS:
						ObjectMapper omapAll= new ObjectMapper();
						CompteMessage AllMsg= new CompteMessage();
						AllMsg.setDemande(demande);
						AllMsg.setUser(user);
						try {
							String content = omapAll.writeValueAsString(AllMsg);
							ACLMessage msgAll= new ACLMessage(ACLMessage.PROPAGATE);
							msgAll.addReceiver(getCompteAgent());
							msgAll.setContent(content);
							msgAll.setConversationId(conversationId);
							msgAll.setLanguage("JSON");
							myAgent.send(msgAll);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case ARCHIVER_SPRINT:
						if(sprint!=null){
							ObjectMapper omapArchiverSprint= new ObjectMapper();
							SprintRequestMessage ArchiverSprintMsg= new SprintRequestMessage();
							ArchiverSprintMsg.setDemande(demande);
							ArchiverSprintMsg.setSprint(sprint);
							ArchiverSprintMsg.setUser(user);
							try {
								String content4 = omapArchiverSprint.writeValueAsString(ArchiverSprintMsg);
								ACLMessage msgArchiverSprint= new ACLMessage(ACLMessage.PROPAGATE);
								msgArchiverSprint.addReceiver(getSprintAgent());
								msgArchiverSprint.setContent(content4);
								msgArchiverSprint.setConversationId(conversationId);
								msgArchiverSprint.setLanguage("JSON");
								myAgent.send(msgArchiverSprint);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case CREE_SOUS_TACHE:
						if(soustache !=null){
							ObjectMapper omapCreeSubTask = new ObjectMapper();
							SousTacheRequestMessage creeSubTaskMsg= new SousTacheRequestMessage();
							creeSubTaskMsg.setDemande(demande);
							creeSubTaskMsg.setSousTache(soustache);
							creeSubTaskMsg.setUser(user);
							try {
								String content5 = omapCreeSubTask.writeValueAsString(creeSubTaskMsg);
								ACLMessage msgCreeSubTask= new ACLMessage(ACLMessage.PROPAGATE);
								msgCreeSubTask.addReceiver(getSubTaskAgent());
								msgCreeSubTask.setContent(content5);
								msgCreeSubTask.setConversationId(conversationId);
								msgCreeSubTask.setLanguage("JSON");
								myAgent.send(msgCreeSubTask);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case CREE_SPRINT:
						if(sprint!=null){
							ObjectMapper omapSprint = new ObjectMapper();
							SprintRequestMessage sprintMsg= new SprintRequestMessage();
							sprintMsg.setDemande(demande);
							sprintMsg.setSprint(sprint);
							sprintMsg.setUser(user);
							try {
								String content5 = omapSprint.writeValueAsString(sprintMsg);
								ACLMessage msgSprint= new ACLMessage(ACLMessage.PROPAGATE);
								msgSprint.addReceiver(getSprintAgent());
								msgSprint.setContent(content5);
								msgSprint.setConversationId(conversationId);
								msgSprint.setLanguage("JSON");
								myAgent.send(msgSprint);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case CREE_TACHE:
						if(tache!=null){
							ObjectMapper omapCreeTask = new ObjectMapper();
							TacheRequestMessage creeTaskMsg= new TacheRequestMessage();
							creeTaskMsg.setDemande(demande);
							creeTaskMsg.setTache(tache);
							creeTaskMsg.setUser(user);
							try {
								String content5 = omapCreeTask.writeValueAsString(creeTaskMsg);
								ACLMessage msgCreeTask= new ACLMessage(ACLMessage.PROPAGATE);
								msgCreeTask.addReceiver(getTaskAgent());
								msgCreeTask.setContent(content5);
								msgCreeTask.setConversationId(conversationId);
								msgCreeTask.setLanguage("JSON");
								myAgent.send(msgCreeTask);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case EFFACE_SPRINT:
						if(sprint!=null){
							ObjectMapper omapEffaceSprint = new ObjectMapper();
							SprintRequestMessage effaceSprintMsg= new SprintRequestMessage();
							effaceSprintMsg.setDemande(demande);
							effaceSprintMsg.setSprint(sprint);
							effaceSprintMsg.setUser(user);
							try {
								String content5 = omapEffaceSprint.writeValueAsString(effaceSprintMsg);
								ACLMessage msgEffaceSprint= new ACLMessage(ACLMessage.PROPAGATE);
								msgEffaceSprint.addReceiver(getSprintAgent());
								msgEffaceSprint.setContent(content5);
								msgEffaceSprint.setConversationId(conversationId);
								msgEffaceSprint.setLanguage("JSON");
								myAgent.send(msgEffaceSprint);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case MODIFIER_SOUS_TACHE:
						if(soustache!= null){
							ObjectMapper omapModifySubTask = new ObjectMapper();
							SousTacheRequestMessage modifySubTaskMsg= new SousTacheRequestMessage();
							modifySubTaskMsg.setDemande(demande);
							modifySubTaskMsg.setSousTache(soustache);
							modifySubTaskMsg.setUser(user);
							try {
								String content5 = omapModifySubTask.writeValueAsString(modifySubTaskMsg);
								ACLMessage msgModifySubTask= new ACLMessage(ACLMessage.PROPAGATE);
								msgModifySubTask.addReceiver(getSubTaskAgent());
								msgModifySubTask.setContent(content5);
								msgModifySubTask.setConversationId(conversationId);
								msgModifySubTask.setLanguage("JSON");
								myAgent.send(msgModifySubTask);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case MODIFIE_PROJET:
						if(projet!= null){
							ObjectMapper omapModifyProjet = new ObjectMapper();
							ProjetRequestMessage modifyProjetMsg= new ProjetRequestMessage();
							modifyProjetMsg.setDemande(demande);
							modifyProjetMsg.setProjet(projet);
							modifyProjetMsg.setUser(user);
							try {
								String content = omapModifyProjet.writeValueAsString(modifyProjetMsg);
								ACLMessage msgModifyProjet= new ACLMessage(ACLMessage.PROPAGATE);
								msgModifyProjet.addReceiver(getProjetAgent());
								msgModifyProjet.setContent(content);
								msgModifyProjet.setConversationId(conversationId);
								msgModifyProjet.setLanguage("JSON");
								myAgent.send(msgModifyProjet);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case MODIFIE_TACHE:
						if(tache!=null){
							ObjectMapper omapModifyTask = new ObjectMapper();
							TacheRequestMessage modifyTaskMsg= new TacheRequestMessage();
							modifyTaskMsg.setDemande(demande);
							modifyTaskMsg.setTache(tache);
							modifyTaskMsg.setUser(user);
							try {
								String content = omapModifyTask.writeValueAsString(modifyTaskMsg);
								ACLMessage msgModifyTask= new ACLMessage(ACLMessage.PROPAGATE);
								msgModifyTask.addReceiver(getTaskAgent());
								msgModifyTask.setContent(content);
								msgModifyTask.setConversationId(conversationId);
								msgModifyTask.setLanguage("JSON");
								myAgent.send(msgModifyTask);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case RETRAIT_MEMBRE:
						if(member!=null && projet!=null){
							ObjectMapper omapRetraitMembre = new ObjectMapper();
							ProjetRequestMessage retraitMembreProjetMsg= new ProjetRequestMessage();
							retraitMembreProjetMsg.setDemande(demande);
							retraitMembreProjetMsg.setProjet(projet);
							retraitMembreProjetMsg.setMember(member);
							retraitMembreProjetMsg.setUser(user);
							try {
								String content = omapRetraitMembre.writeValueAsString(retraitMembreProjetMsg);
								ACLMessage msgRM= new ACLMessage(ACLMessage.PROPAGATE);
								msgRM.addReceiver(getProjetAgent());
								msgRM.setContent(content);
								msgRM.setConversationId(conversationId);
								msgRM.setLanguage("JSON");
								myAgent.send(msgRM);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case AJOUT_MANAGER:
						if(user!=null && projet!=null){
							ObjectMapper omapManager = new ObjectMapper();
							ProjetRequestMessage managerMsg= new ProjetRequestMessage();
							managerMsg.setDemande(demande);
							managerMsg.setProjet(projet);
							managerMsg.setUser(user);
							try {
								String content4 = omapManager.writeValueAsString(managerMsg);
								ACLMessage msgManager= new ACLMessage(ACLMessage.PROPAGATE);
								msgManager.addReceiver(getProjetAgent());
								msgManager.setContent(content4);
								msgManager.setConversationId(conversationId);
								msgManager.setLanguage("JSON");
								myAgent.send(msgManager);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case SUPPRIMER_SOUS_TACHE:
						if(soustache!=null){
							ObjectMapper omapSuppSubTask = new ObjectMapper();
							SousTacheRequestMessage suppSubTaskMsg= new SousTacheRequestMessage();
							suppSubTaskMsg.setDemande(demande);
							suppSubTaskMsg.setSousTache(soustache);
							suppSubTaskMsg.setUser(user);
							try {
								String content5 = omapSuppSubTask.writeValueAsString(suppSubTaskMsg);
								ACLMessage msgSuppSubTask= new ACLMessage(ACLMessage.PROPAGATE);
								msgSuppSubTask.addReceiver(getSubTaskAgent());
								msgSuppSubTask.setContent(content5);
								msgSuppSubTask.setConversationId(conversationId);
								msgSuppSubTask.setLanguage("JSON");
								myAgent.send(msgSuppSubTask);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case SUPPRIMER_TACHE:
						if(tache!=null){
							ObjectMapper omapSuppTask = new ObjectMapper();
							TacheRequestMessage suppTaskMsg= new TacheRequestMessage();
							suppTaskMsg.setDemande(demande);
							suppTaskMsg.setTache(tache);
							suppTaskMsg.setUser(user);
							try {
								String content = omapSuppTask.writeValueAsString(suppTaskMsg);
								ACLMessage msgSuppTask= new ACLMessage(ACLMessage.PROPAGATE);
								msgSuppTask.addReceiver(getTaskAgent());
								msgSuppTask.setContent(content);
								msgSuppTask.setConversationId(conversationId);
								msgSuppTask.setLanguage("JSON");
								myAgent.send(msgSuppTask);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case SYNCHRONIZE_DOWN:
						ObjectMapper omapDown = new ObjectMapper();
						ProjetRequestMessage downMsg= new ProjetRequestMessage();
						downMsg.setDemande(demande);
						downMsg.setProjet(projet);
						downMsg.setUser(user);
						try {
							String content4 = omapDown.writeValueAsString(downMsg);
							ACLMessage msgDown= new ACLMessage(ACLMessage.PROPAGATE);
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
						upMsg.setUser(user);
						try {
							String content4 = omapUP.writeValueAsString(upMsg);
							ACLMessage msgUP= new ACLMessage(ACLMessage.PROPAGATE);
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
		
		private AID getCompteAgent() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Compte");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template);
				return result[0].getName();
			} catch(FIPAException fe) {
				fe.printStackTrace();
			}
			return null;
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
