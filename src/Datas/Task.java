package Datas;

import java.sql.Time;

public class Task {
	private int id;
	private int sprint;
	private String name;
	private String description;
	private int priority;
	private int current_state;
	private Time creation_date;
	private Time last_update;
	private int difficulty;
	
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
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Time getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Time creation_date) {
		this.creation_date = creation_date;
	}
	public int getCurrent_state() {
		return current_state;
	}
	public void setCurrent_state(int current_state) {
		this.current_state = current_state;
	}
	public Time getLast_update() {
		return last_update;
	}
	public void setLast_update(Time last_update) {
		this.last_update = last_update;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}
