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

	private List<AID> listeDestinataires;
	private DeviceInfoTypes demande;
	private Task tache;
	private Project projet;
	private SubTask soustache;
	private Sprint sprint;
	
	
	public List<AID> getListeDestinataires() {
		return listeDestinataires;
	}
	
	public void setListeDestinataires(List<AID> listeDestinataires) {
		this.listeDestinataires = listeDestinataires;
	}
	

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

	public SubTask getSoustache() {
		return soustache;
	}

	public void setSoustache(SubTask soustache) {
		this.soustache = soustache;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
	
	
	
}
