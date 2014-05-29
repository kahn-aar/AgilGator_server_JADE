package Datas;

import jade.core.AID;

public class Utilisateur {

	private int id;
	private AID aid;
	private String pseudo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String login) {
		this.pseudo = login;
	}

	public AID getAid() {
		return aid;
	}

	public void setAid(AID aid) {
		this.aid = aid;
	}
}
