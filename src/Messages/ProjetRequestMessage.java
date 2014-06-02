package Messages;

import java.util.List;

import Datas.Project;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;

public class ProjetRequestMessage {
	
	private Project projet;
	private DeviceInfoTypes demande;
	private List<Utilisateur> members;
	
	public Project getProjet() {
		return projet;
	}
	public void setProjet(Project projet) {
		this.projet = projet;
	}
	public DeviceInfoTypes getDemande() {
		return demande;
	}
	public void setDemande(DeviceInfoTypes demande) {
		this.demande = demande;
	}
	public List<Utilisateur> getMembers() {
		return members;
	}
	public void setMembers(List<Utilisateur> members) {
		this.members = members;
	}
	
}
	
	