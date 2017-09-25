/*
 * Jens Plate. jepl4052
 * Erik Hörnström, erho7892
 */
public class Result {

	private Participant participant;
	private Event event;
	private double result;

	public Result(Participant participant, Event event, double result) {
		this.participant = participant;
		this.event = event;
		this.result = result;
	}

	public Participant getParticipant() {
		return participant;
	}

	public Event getEvent() {
		return event;
	}

	public double getResult() {
		return result;
	}

	public String toString() {
		return "Result for " + participant.toString() + " in " + event.toString() + ": " + getResult();
	}
}
