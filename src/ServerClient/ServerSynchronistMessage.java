package ServerClient;

import Datas.enums.DeviceInfoTypes;


public class ServerSynchronistMessage {

	private DeviceInfoTypes type;
	private String content;
	
	
	public DeviceInfoTypes getType() {
		return type;
	}
	
	public void setType(DeviceInfoTypes type) {
		this.type = type;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
