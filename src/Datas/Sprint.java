package Datas;

import java.sql.Date;
import java.sql.Timestamp;

public class Sprint {

	private int id;
	private int project;
	private Timestamp start_date;
	private Timestamp end_date;
	private String description;
	
	public int getId() {return id;	}
	public int getProject() {return project;}
	public Timestamp getStart_date() {return start_date;}
	public Timestamp getEnd_date() {return end_date;}
	public String getDescription() {return description;}
	
	public void setId(int id) {this.id = id;}
	public void setProject(int project) {this.project = project;}
	public void setStart_date(Timestamp start_date) {this.start_date = start_date;}
	public void setEnd_date(Timestamp end_date) {this.end_date = end_date;}
	public void setDescription(String description) {this.description = description;}
}
