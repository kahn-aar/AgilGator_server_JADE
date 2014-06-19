package Messages;

import Datas.Project;
import Datas.Sprint;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;

public class SprintRequestMessage {

	private Sprint sprint;
	private DeviceInfoTypes demande;
	private Utilisateur user;
	private Project project;
	
	public Sprint getSprint() {
		return sprint;
	}
	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
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
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
}
