package Messages;

import Datas.Utilisateur;
import Datas.enums.DeviceInfoTypes;

/**
 * Message permettant d'envoyer une demande qui ne requiert pas de contenu
 * Exemple : la liste de tous les utilisateurs
 * @author Léa
 *
 */
public class CompteMessage {
	
	private DeviceInfoTypes demande;
	private Utilisateur user;
	private Boolean existed;

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

	public Boolean getExisted() {
		return existed;
	}

	public void setExisted(Boolean existed) {
		this.existed = existed;
	}

}
