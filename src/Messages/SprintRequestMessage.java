package Messages;

import Datas.Sprint;
import Datas.enums.DeviceInfoTypes;

public class SprintRequestMessage {

	private Sprint sprint;
	private DeviceInfoTypes demande;
	
	public Sprint getSprint() {
		return sprint;
	}
	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
	public DeviceInfoTypes getDemande() {
		return demande;
	}
	public void setDemande(DeviceInfoTypes demande) {
		this.demande = demande;
	}
	
}
