package Messages;

import java.util.List;

import Datas.Utilisateur;

public class UserListMessage {
	
	private List<Utilisateur> userList;

	public List<Utilisateur> getUserList() {
		return userList;
	}

	public void setUserList(List<Utilisateur> userList) {
		this.userList = userList;
	}

}
