package behaviours.device;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

import Datas.Project;
import Datas.Sprint;
import Datas.SubTask;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.BDDAnswerMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WaitingAnswerBehaviour extends CyclicBehaviour {
	
	public Object objectWaitingId;
	
	public WaitingAnswerBehaviour(Object o)
	{
		this.objectWaitingId = o;
	}

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (message != null) {
			String conversationId = message.getConversationId();
			System.out.println(myAgent.getLocalName() + " reçu -> " + message.getContent());
			ObjectMapper omap = new ObjectMapper();
			DeviceInfoTypes demande = null;
			int id = -1;
			
			try {
				BDDAnswerMessage msg = omap.readValue(message.getContent(),BDDAnswerMessage.class);
				demande = msg.getDemande();
				id = msg.getId();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(demande!=null){
				switch(demande){
				//Attend un boolean ou rien
				case CONNEXION:
					//Send OK to connexion function
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
				case DECONNEXION:
					break;
					
				//Attend un ID 
				case CREE_PROJET:
					((Project)this.objectWaitingId).setId(id);
					break;
				case CREE_COMPTE:
					((Utilisateur)this.objectWaitingId).setId(id);
					break;
				case CREE_SOUS_TACHE:
					((SubTask)this.objectWaitingId).setId(id);
					break;
				case CREE_SPRINT:
					((Sprint)this.objectWaitingId).setId(id);
					break;
				case CREE_TACHE:
					((Task)this.objectWaitingId).setId(id);
					break;
					
				//Attend un contenu
				case MEMBRES_DU_PROJET:
					break;
				case ALL_USERS:
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
