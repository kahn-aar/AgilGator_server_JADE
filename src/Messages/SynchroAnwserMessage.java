package Messages;

import java.util.ArrayList;
import java.util.List;

public class SynchroAnwserMessage {

	private List<String> updates = new ArrayList<>();

	public List<String> getUpdates() {
		return updates;
	}

	public void setUpdates(List<String> updates) {
		this.updates = updates;
	}
	
	public void addNewRequest(String newRequest) {
		updates.add(newRequest);
	}
	
	public void addNewRequests(List<String> newRequests) {
		updates.addAll(newRequests);
	}
	
}
