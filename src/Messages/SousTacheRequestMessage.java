package Messages;

import Datas.SubTask;
import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;

public class SousTacheRequestMessage {
	private DeviceInfoTypes demande;
	private Utilisateur user;
	private SubTask sousTache;
	
	public DeviceInfoTypes getDemande() {
		return demande;
	}
	
	public void setDemande(DeviceInfoTypes demande) {
		this.demande = demande;
	}

	public SubTask getSousTache() {
		return sousTache;
	}

	public void setSousTache(SubTask sousTache) {
		this.sousTache = sousTache;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}
}

