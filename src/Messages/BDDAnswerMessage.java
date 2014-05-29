package Messages;

import java.util.ArrayList;
import java.util.List;

import Datas.Utilisateur;

public class BDDAnswerMessage {

	private List<Utilisateur> mesUsers;
	private String table;

	public List<Utilisateur> getMesUsers() {
		return mesUsers;
	}

	public void setMesUsers(List<Utilisateur> mesUsers) {
		this.mesUsers = mesUsers;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
}