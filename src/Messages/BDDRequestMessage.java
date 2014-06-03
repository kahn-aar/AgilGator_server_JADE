package Messages;

import Datas.Utilisateur;
import Datas.enums.BDDRequestTypes;
import Datas.enums.DeviceInfoTypes;


public class BDDRequestMessage {

	private BDDRequestTypes type;
	private DeviceInfoTypes demande;
	private String request;
	private String request2;
	private Utilisateur user;
	
	public BDDRequestTypes getType() {
		return type;
	}
	
	public void setType(BDDRequestTypes type) {
		this.type = type;
	}
	
	public String getRequest() {
		return request;
	}
	
	public void setRequest(String request) {
		this.request = request;
	}
	
	public String getRequest2() {
		return request2;
	}
	
	public void setRequest2(String request) {
		this.request2 = request;
	}

	public DeviceInfoTypes getDemande() {
		return demande;
	}

	public void setDemande(DeviceInfoTypes demande) {
		this.demande = demande;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

}
