package Messages;



import Datas.Project;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;

public class ProjetRequestMessage {
	
	private Project projet;
	private DeviceInfoTypes demande;
	// Utilisateur à ajouter ou à retirer d'un projet
	private Utilisateur member;
	
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
	public Utilisateur getMember() {
		return member;
	}
	public void setMember(Utilisateur member) {
		this.member = member;
	}
	
	
}
	
	