package Messages;

import Datas.RequestTypes;
import Datas.Utilisateur;

public class DataMessage {

	private RequestTypes type;
	private String content;
	private Utilisateur user;
	
	public RequestTypes getType() {
		return type;
	}
	
	public void setType(RequestTypes type) {
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
