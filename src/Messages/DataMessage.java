package Messages;

import Datas.Project;
import Datas.Sprint;
import Datas.SubTask;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;

public class DataMessage {

	private DeviceInfoTypes demande;
	// Utilisateur, sera le manager dans le cas d'une création de projet
	private Utilisateur user;
	private Project projet;
	private Task tache;
	private SubTask sousTache;
	private Sprint sprint;
	// Membre que l'on veut ajouter au projet ou supprimer du projet
	private Utilisateur member;
	
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

	public Project getProjet() {
		return projet;
	}

	public void setProjet(Project projet) {
		this.projet = projet;
	}

	public Task getTache() {
		return tache;
	}

	public void setTache(Task tache) {
		this.tache = tache;
	}

	public SubTask getSousTache() {
		return sousTache;
	}

	public void setSousTache(SubTask sousTache) {
		this.sousTache = sousTache;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public Utilisateur getMember() {
		return member;
	}

	public void setMember(Utilisateur member) {
		this.member = member;
	}
	


}
