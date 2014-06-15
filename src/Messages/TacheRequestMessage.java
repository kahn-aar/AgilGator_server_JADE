package Messages;


import Datas.Task;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;

public class TacheRequestMessage {
	
	private Task tache;
	private DeviceInfoTypes demande;
	private Utilisateur user;
	
	public Task getTache() {
		return tache;
	}
	public void setTache(Task tache) {
		this.tache = tache;
	}
	public DeviceInfoTypes getDemande() {
		return demande;
	}
	public void setDemande(DeviceInfoTypes demande) {
		this.demande = demande;
	}
	public Utilisateur getUser() {
		return this.user;
	}
	public void setUser(Utilisateur user) {
		this.user = user;
	}
	
}
