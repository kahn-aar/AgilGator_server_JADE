package Messages;

import jade.core.AID;

import java.util.List;

import Datas.enums.DeviceInfoTypes;

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
	private DeviceInfoTypes demande;
	
	
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

	public DeviceInfoTypes getDemande() {
		return demande;
	}

	public void setDemande(DeviceInfoTypes demande) {
		this.demande = demande;
	}
	
	
	
}
