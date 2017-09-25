/*
 * Jens Plate. jepl4052
 * Erik Hörnström, erho7892
 */
public class Participant {

	private static int numberCount = 100;

	private String firstName;
	private String lastName;
	private Team team;
	private int number = numberCount;

	private ResultList resultList = new ResultList();

	public Participant(String firstName, String lastName, Team team) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.team = team;
		numberCount++;
	}

	public void addResult(Event event, double result) {
		resultList.addResult(new Result(this, event, result));
	}

	public String getFullName () {
		return firstName + " " + lastName;
	}

	public int getParticipantNumber() {
		return this.number;
	}

	public Team getTeam() {
		return team;
	}

	public ResultList getResultList() {
		return resultList;
	}

	public double[] getResultsByEvent(Event event) {
		return resultList.getResultsByEvent(event);
	}

	public Result getBestResultByEventName(String eventName) {
		return resultList.getBestResultByEventName(eventName);
	}

	public String toString() {
		return firstName + " " + lastName + " with number " + number + " in team " + team;
	}
}
