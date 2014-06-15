package Datas;

import java.sql.Timestamp;

public class Task {
	private int id;
	private int sprint;
	private String name;
	private String description;
	private int priorite;
	private int current_state;
	private Timestamp creation_date;
	private Timestamp last_update;
	private int difficulte;
	private int notifications;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSprint() {
		return sprint;
	}
	public void setSprint(int sprint) {
		this.sprint = sprint;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPriorite() {
		return priorite;
	}
	public void setPriorite(int priority) {
		this.priorite = priority;
	}
	public Timestamp getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Timestamp creation_date) {
		this.creation_date = creation_date;
	}
	public int getCurrent_state() {
		return current_state;
	}
	public void setCurrent_state(int current_state) {
		this.current_state = current_state;
	}
	public Timestamp getLast_update() {
		return last_update;
	}
	public void setLast_update(Timestamp last_update) {
		this.last_update = last_update;
	}
	public int getDifficulte() {
		return difficulte;
	}
	public void setDifficulte(int difficulty) {
		this.difficulte = difficulty;
	}
	public int getNotifications() {
		return notifications;
	}
	public void setNotifications(int notifications) {
		this.notifications = notifications;
	}
	
}
