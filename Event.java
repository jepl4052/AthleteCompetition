/*
 * Jens Plate. jepl4052
 * Erik Hörnström, erho7892
 */

public class Event {

	private String eventName;
	private int attemptsAllowed;

	public Event(String eventName, int attemptsAllowed) {
		this.eventName = eventName;
		this.attemptsAllowed = attemptsAllowed;
	}

	public String getEventName() {
		return eventName;
	}

	public int getAttemptsAllowed() {
		return attemptsAllowed;
	}

	public String toString() {
		return eventName + " with " + attemptsAllowed + " attempts allowed";
	}
}
