package Messages;

import Datas.Project;
import Datas.SubTask;
import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;

public class DataMessage {

	private DeviceInfoTypes demande;
	private Utilisateur user;
	private Project projet;
	private Task tache;
	private SubTask sousTache;
	
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
	


}
