package Messages;

import java.util.List;

import Datas.Project;
import Datas.Sprint;
import Datas.SubTask;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;

public class BDDAnswerMessage {

	private List<Utilisateur> mesUsers;
	public List<Utilisateur> getMesUsers() {
		return mesUsers;
	}

	public void setMesUsers(List<Utilisateur> mesUsers) {
		this.mesUsers = mesUsers;
	}

	private Utilisateur user;
	private Project project;
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	private String table;
	private DeviceInfoTypes demande;
	private int id;
	

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public DeviceInfoTypes getDemande() {
		return demande;
	}

	public void setDemande(DeviceInfoTypes demande) {
		this.demande = demande;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}