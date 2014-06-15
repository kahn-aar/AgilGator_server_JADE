package Datas;

import jade.core.AID;

public class Utilisateur {

	private int id;
	private AID aid;
	private String firstname;
	private String name;
	private String email;
	private String password;
	private String tag;
	private String salt1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String login) {
		this.firstname = login;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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

	public String getPassword() {
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
