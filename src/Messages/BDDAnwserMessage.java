package Messages;

import java.util.ArrayList;
import java.util.List;

public class BDDAnwserMessage {

	private String table;
	private List<String> results = new ArrayList<>();

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}
	
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void addNewRequest(String newRequest) {
		results.add(newRequest);
	}
	
	public void addNewRequests(List<String> newRequests) {
		results.addAll(newRequests);
	}
}
