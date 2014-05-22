package Messages;

import Datas.RequestTypes;


public class BDDRequestMessage {

	private RequestTypes type;
	private String request;
	
	public RequestTypes getType() {
		return type;
	}
	
	public void setType(RequestTypes type) {
		this.type = type;
	}
	
	public String getRequest() {
		return request;
	}
	
	public void setRequest(String request) {
		this.request = request;
	}
	
	
}
