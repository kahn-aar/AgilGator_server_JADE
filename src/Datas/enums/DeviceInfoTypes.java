package Datas.enums;

public enum DeviceInfoTypes {

	// Action pour l'agent COMPTE
	CREE_COMPTE,
	CONNEXION,
	DECONNEXION,
	ALL_USERS, // Retourne la liste de tous les users d'agilGator
	IS_USER, // Regarde si un utilisateur existe déjà
	// Action pour l'agent PROJET
	CREE_PROJET,
	EFFACE_PROJET,
	MODIFIE_PROJET,
	AJOUT_MEMBRE,
	RETRAIT_MEMBRE,
	MEMBRES_DU_PROJET,
	AJOUT_MANAGER, // ajoute un chef de projet lors de la création d'un projet
	// Action pour l'agent SPRINT
	CREE_SPRINT,
	EFFACE_SPRINT,
	ARCHIVER_SPRINT,
	// Action pour l'agent TACHE
	CREE_TACHE,
	MODIFIE_TACHE,
	SUPPRIMER_TACHE,
	// Action pour l'agent SOUS-TACHE
	CREE_SOUS_TACHE,
	MODIFIER_SOUS_TACHE,
	SUPPRIMER_SOUS_TACHE,
	// Actions pour 'agent synchro
	SYNCHRONIZE_UP,
	SYNCHRONIZE_DOWN,
}