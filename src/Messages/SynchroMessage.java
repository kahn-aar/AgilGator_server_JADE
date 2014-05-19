package Messages;

/**
 * Message de synchronisation
 * ServeurAgent => SynchroAgent
 * 
 * @author Nicolas
 *
 */
public class SynchroMessage {
	
	private int userId;
	private int timeStampLast;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTimeStampLast() {
		return timeStampLast;
	}
	public void setTimeStampLast(int timeStampLast) {
		this.timeStampLast = timeStampLast;
	}
	
	

}
