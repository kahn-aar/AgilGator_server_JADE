package Datas;

import java.sql.Timestamp;

import Datas.enums.SousTacheEtat;

public class SubTask {
	private int id ;
	private Task task;
	private String titre ;
	private String description ;
	private SousTacheEtat etat ;
	private Utilisateur effecteur;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitre() {
		return titre;
	}
	public void setTitre(String name) {
		this.titre = name;
	}
	public SousTacheEtat getEtat() {
		return etat;
	}
	public void setEtat(SousTacheEtat current_state) {
		this.etat = current_state;
	}
	public Utilisateur getEffecteur() {
		return effecteur;
	}
	public void setEffecteur(Utilisateur current_developer) {
		this.effecteur = current_developer;
	}
}
