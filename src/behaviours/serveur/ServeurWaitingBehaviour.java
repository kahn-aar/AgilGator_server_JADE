package behaviours.serveur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Agents.ServeurAgent;
import Agents.UtilisateursAgent;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.DataMessage;
import Messages.ServerLiaisonMessage;
import behaviours.compte.CompteSendingRequestBehaviour;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Attens les retours de projet, tache, sous-tâche, compte, sprint
 * @author Léa
 *
 */
public class ServeurWaitingBehaviour extends CyclicBehaviour{

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
		if(message!=null){
			System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
			String conversationId = message.getConversationId();
			ObjectMapper omap = new ObjectMapper();
			try {
				ServerLiaisonMessage msgToLiaison = omap.readValue(message.getContent(),ServerLiaisonMessage.class);
				String content = msgToLiaison.getContent();
				DeviceInfoTypes demande = msgToLiaison.getDemande();
				List<AID> destinataires = new ArrayList<AID>();
				// WTF ??? Faut faire une demande
				List<Utilisateur> utilisateurs = null;//((UtilisateursAgent) myAgent).getUtilisateursConnectés();
				// Définit la liste des destinataires en fonction de la demande
				switch(demande){
					case CREE_COMPTE:
						destinataires.add(((ServeurAgent) myAgent).getUser().getAid());
						break;
					case AJOUT_MEMBRE:
						// destinataires = tous les membres du projets
						break;
					case AJOUT_MANAGER:
						destinataires.add(((ServeurAgent) myAgent).getUser().getAid());
						break;
					case CREE_PROJET:
						destinataires.add(((ServeurAgent) myAgent).getUser().getAid());
						break;
					case CREE_TACHE:
						for(Utilisateur user : utilisateurs){
							destinataires.add(user.getAid());
						}
						break;
					case CREE_SOUS_TACHE:
						destinataires.add(((ServeurAgent) myAgent).getUser().getAid());
						break;
					case CREE_SPRINT:
						for(Utilisateur user : utilisateurs){
							destinataires.add(user.getAid());
						}
						break;
					case EFFACE_PROJET:
						for(Utilisateur user : utilisateurs){
							destinataires.add(user.getAid());
						}
						break;
					case SUPPRIMER_TACHE:
						for(Utilisateur user : utilisateurs){
							destinataires.add(user.getAid());
						}
						break;
					case SUPPRIMER_SOUS_TACHE:
						for(Utilisateur user : utilisateurs){
							destinataires.add(user.getAid());
						}
						break;
					case EFFACE_SPRINT:
						
						for(Utilisateur user : utilisateurs){
							destinataires.add(user.getAid());
						}
						break;
					default:
						break;
				
				}
				myAgent.addBehaviour(new ServeurSendToLiaisonBehaviour(conversationId, destinataires, content));
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
