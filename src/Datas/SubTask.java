package Datas;

import java.sql.Timestamp;

public class SubTask {
	private int id ;
	private int taskId;
	private String name ;
	private String description ;
	private int current_state ;
	private int current_developer;
	private Timestamp creation_date ;
	private Timestamp last_update;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCurrent_state() {
		return current_state;
	}
	public void setCurrent_state(int current_state) {
		this.current_state = current_state;
	}
	public int getCurrent_developer() {
		return current_developer;
	}
	public void setCurrent_developer(int current_developer) {
		this.current_developer = current_developer;
	}
}
