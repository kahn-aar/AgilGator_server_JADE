package Datas;

import jade.core.AID;

public class Utilisateur {

	private int id;
	private AID aid;
	private String pseudo;
	private String email;
	private String password;
	private String salt1;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt1() {
		return salt1;
	}

	public void setSalt1(String salt1) {
		this.salt1 = salt1;
	}
}
