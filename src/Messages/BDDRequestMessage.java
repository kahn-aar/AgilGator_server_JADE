package Messages;

import Datas.enums.BDDRequestTypes;
import Datas.enums.DeviceInfoTypes;


public class BDDRequestMessage {

	private BDDRequestTypes type;
	private DeviceInfoTypes demande;
	private String request;
	
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

	public DeviceInfoTypes getDemande() {
		return demande;
	}

	public void setDemande(DeviceInfoTypes demande) {
		this.demande = demande;
	}

}
