package Messages;

import Datas.enums.BDDRequestTypes;


public class BDDRequestMessage {

	private BDDRequestTypes type;
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

}
