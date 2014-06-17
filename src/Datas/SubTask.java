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
	private Timestamp creation_date ;
	private Timestamp last_update;
	
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
	public Timestamp getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Timestamp creation_date) {
		this.creation_date = creation_date;
	}
	public Timestamp getLast_update() {
		return last_update;
	}
	public void setLast_update(Timestamp last_update) {
		this.last_update = last_update;
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
