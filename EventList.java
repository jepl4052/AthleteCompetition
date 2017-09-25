/*
 * Jens Plate. jepl4052
 * Erik Hörnström, erho7892
 */
import java.util.ArrayList;

public class EventList {

	private ArrayList<Event> listOfEvents = new ArrayList<>();

	public void addEvent(String eventName, int attemptsAllowed) {
		Event event = new Event(eventName, attemptsAllowed);
		listOfEvents.add(event);
	}

	public boolean isEvent(String eventName) {
		for (Event event : listOfEvents) {
			if (event.getEventName().equalsIgnoreCase(eventName)) {
				return true;
			}
		}
		return false;
	}

	public Event getEventByName(String eventName) {
		for (Event event : listOfEvents) {
			if(event.getEventName().equals(eventName)) {
				return event;
			}
		}
		return null;
	}

	public ArrayList<Event> getEventList() {
		return listOfEvents;
	}

	public String listResults(Participant participant) {
		String list = "";
		boolean match = false;

		for(Event event : listOfEvents) {
			double[] result = participant.getResultsByEvent(event);

			if(!(result.length == 0)) {
				match = true;
				list += "Results for " +participant.toString() + " in " + event.getEventName() + ": ";

				for(int i = 0; i < result.length; i++) {
					list += (result[i]);
					if(!(i == result.length-1)) {
						list += (", ");
					}
				}
				list += "\n";
			}
		}
		if(!match) {
			list += "No results to show.";
		}
		return list;
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < listOfEvents.size(); i++) {
			s += listOfEvents.get(i).toString() + "\n";
		}
		return s;
	}
}
