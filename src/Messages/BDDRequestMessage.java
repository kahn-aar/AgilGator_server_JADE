package Messages;

import Datas.RequestTypes;
import Datas.Utilisateur;


public class BDDRequestMessage {

	private RequestTypes type;
	private String request;
	private Utilisateur user;
	
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

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}
	
	
}
