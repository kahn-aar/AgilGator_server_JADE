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
	private List<Project> myProjects;
	private List<Sprint> mySprints;
	private List<Task> myTasks;
	private List<SubTask> mySubTasks;
	private Utilisateur user;
	private String table;
	private DeviceInfoTypes demande;
	private int id;
	
	public List<Sprint> getMySprints() {
		return mySprints;
	}

	public void setMySprints(List<Sprint> mySprints) {
		this.mySprints = mySprints;
	}

	public List<Task> getMyTasks() {
		return myTasks;
	}

	public void setMyTasks(List<Task> myTasks) {
		this.myTasks = myTasks;
	}

	public List<SubTask> getMySubTasks() {
		return mySubTasks;
	}

	public void setMySubTasks(List<SubTask> mySubTasks) {
		this.mySubTasks = mySubTasks;
	}
	
	public List<Project> getMyProjects() {
		return myProjects;
	}

	public void setMyProjects(List<Project> myProjects) {
		this.myProjects = myProjects;
	}
	
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