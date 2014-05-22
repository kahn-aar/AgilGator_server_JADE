package Messages;

import Datas.Utilisateur;

public class UserMessage {
	
	private String action;
	private Utilisateur monUser;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Utilisateur getMonUser() {
		return monUser;
	}
	public void setMonUser(Utilisateur monUser) {
		this.monUser = monUser;
	}
	
	

}
