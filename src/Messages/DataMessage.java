package Messages;

import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;

public class DataMessage {

	private DeviceInfoTypes type;
	private String content;
	private Utilisateur user;
	
	public DeviceInfoTypes getDeviceType() {
		return type;
	}
	
	public void setDeviceType(DeviceInfoTypes type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}
	


}
