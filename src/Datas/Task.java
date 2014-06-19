package Datas;

import java.sql.Timestamp;
import java.util.List;

public class Task {
	private int id;
	private int sprint;
	private String name;
	private String description;
	private int priorite;
	private int difficulte;
	private int notifications;
	private List<SubTask> sousTaches;
	
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
	public List<SubTask> getSousTaches() {
		return sousTaches;
	}
	public void setSousTaches(List<SubTask> sousTaches) {
		this.sousTaches = sousTaches;
	}
	
}
