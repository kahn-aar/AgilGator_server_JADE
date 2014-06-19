package Messages;

import jade.core.AID;

import java.util.List;

import Datas.Project;
import Datas.Sprint;
import Datas.SubTask;
import Datas.Task;
import Datas.enums.DeviceInfoTypes;

/**
 * Message
 * Server => Liaison
 * 
 * @author Nicolas
 *
 */
public class ServerLiaisonMessage {

	private DeviceInfoTypes demande;
	private Task tache;
	private Project projet;
	private SubTask sousTache;
	private Sprint sprint;
	

	public DeviceInfoTypes getDemande() {
		return demande;
	}

	public void setDemande(DeviceInfoTypes demande) {
		this.demande = demande;
	}

	public Task getTache() {
		return tache;
	}

	public void setTache(Task tache) {
		this.tache = tache;
	}

	public Project getProjet() {
		return projet;
	}

	public void setProjet(Project projet) {
		this.projet = projet;
	}

	public SubTask getSousTache() {
		return sousTache;
	}

	public void setSousTache(SubTask soustache) {
		this.sousTache = soustache;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
	
	
	
}
