package Messages;

import Datas.SubTask;
import Datas.enums.DeviceInfoTypes;

public class SousTacheRequestMessage {
	private DeviceInfoTypes demande;
	private SubTask sousTache;
	
	public DeviceInfoTypes getDeviceType() {
		return demande;
	}
	
	public void setDeviceType(DeviceInfoTypes demande) {
		this.demande = demande;
	}

	public SubTask getSousTache() {
		return sousTache;
	}

	public void setSousTache(SubTask sousTache) {
		this.sousTache = sousTache;
	}
}

