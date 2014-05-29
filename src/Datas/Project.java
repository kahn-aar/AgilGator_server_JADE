package Datas;

import java.sql.Timestamp;

public class Project {
	
	private int id ;
	private String title ;
	private String subtitle ;
	private String description ;
	private Timestamp creation_date ;
	private Timestamp last_update ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
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
	

}
