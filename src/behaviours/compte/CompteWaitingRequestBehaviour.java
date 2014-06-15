package behaviours.compte;

import java.io.IOException;

import Datas.Project;
import Datas.SubTask;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;
import Messages.CompteMessage;
import Messages.DataMessage;
import Messages.ProjetRequestMessage;
import Messages.TacheRequestMessage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
 * Attend les messages de requête de l'agent serveur
 * @author Léa
 *
 */
public class CompteWaitingRequestBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		// Attends un message de serveur qui lui demandera les actions à exécuter
		// il peut également attendre un message de lui même (requête de vérification)
		ACLMessage msgServeur = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (msgServeur != null){
			String conversationId = msgServeur.getConversationId();
			System.out.println(myAgent.getLocalName() + " reçu -> " + msgServeur.getContent());
			ObjectMapper omap = new ObjectMapper();
			CompteMessage requestMsg;
				try {
					requestMsg = omap.readValue(msgServeur.getContent(),CompteMessage.class);
					DeviceInfoTypes demande = requestMsg.getDemande();
					Utilisateur user = requestMsg.getUser();
					Boolean existed = requestMsg.getExisted();
					if (demande != null){
						myAgent.addBehaviour(new CompteSendingRequestBehaviour(conversationId,demande, user, existed));
					}
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
