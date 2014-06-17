package behaviours.device;

import java.io.IOException;

import Agents.ServeurAgent;
import Datas.Project;
import Datas.Sprint;
import Datas.SubTask;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.DataMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitingAnswerBehaviour extends CyclicBehaviour {

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
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
				//Attend un boolean
				case CONNEXION:
					break;
				case EFFACE_PROJET:
					break;
				case AJOUT_MEMBRE:
					break;
				case ARCHIVER_SPRINT:
					break;
				case EFFACE_SPRINT:
					break;
				case MODIFIER_SOUS_TACHE:
					break;
				case MODIFIE_PROJET:
					break;
				case MODIFIE_TACHE:
					break;
				case SUPPRIMER_SOUS_TACHE:
					break;
				case SUPPRIMER_TACHE:
					break;
				case RETRAIT_MEMBRE:
					break;
				case AJOUT_MANAGER:
					break;
					
				//Attend un ID 
				case CREE_PROJET:
					break;
				case CREE_COMPTE:
					break;
				case CREE_SOUS_TACHE:
					break;
				case CREE_SPRINT:
					break;
				case CREE_TACHE:
					break;
					
				//Attend un contenu
				case MEMBRES_DU_PROJET:
					break;
				case ALL_USERS:
					break;
				case DECONNEXION:
					break;
				case SYNCHRONIZE_DOWN:
					break;
				case SYNCHRONIZE_UP:
					break;
				default:
					break;
				
				}
			}
		}
	
	}	
}
