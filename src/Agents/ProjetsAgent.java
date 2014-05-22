package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Datas.RequestTypes;
import Messages.RequestMessage;
import behaviours.ProjetsSendingRequestBehaviour;
import behaviours.ProjetsWaitingReplyBehaviour;
import behaviours.ProjetsWaitingRequestBehaviour;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjetsAgent extends Agent {

	@Override
	public void setup() {
		super.setup();
		
		this.addBehaviour(new ProjetsSendingRequestBehaviour(50));
		//this.addBehaviour(new ProjetsWaitingRequestBehaviour());
		//this.addBehaviour(new ProjetsWaitingReplyBehaviour(conversationId));
		
		//Enregistrement de l'agent auprès du DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("BDD");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
	}
	
}
