package Messages;

import jade.core.AID;

import java.util.List;

/**
 * Message
 * Server => Liaison
 * 
 * @author Nicolas
 *
 */
public class ServerLiaisonMessage {

	private List<AID> listeDestinataires;
	private String content;
	private String typeOfMessage;
	
	
	public List<AID> getListeDestinataires() {
		return listeDestinataires;
	}
	
	public void setListeDestinataires(List<AID> listeDestinataires) {
		this.listeDestinataires = listeDestinataires;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getTypeOfMessage() {
		return typeOfMessage;
	}
	
	public void setTypeOfMessage(String typeOfMessage) {
		this.typeOfMessage = typeOfMessage;
	}
	
	
	
}
