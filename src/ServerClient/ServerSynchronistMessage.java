package ServerClient;

import Datas.enums.ServerToDeviceTypes;

public class ServerSynchronistMessage {

	private ServerToDeviceTypes type;
	private String content;
	
	
	public ServerToDeviceTypes getType() {
		return type;
	}
	
	public void setType(ServerToDeviceTypes type) {
		this.type = type;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
