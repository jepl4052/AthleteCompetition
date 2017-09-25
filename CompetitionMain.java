/*
 * Jens Plate. jepl4052
 * Erik Hörnström, erho7892
 */

import java.util.ArrayList;
import java.util.Scanner;

public class CompetitionMain {

	private static Scanner keyboard = new Scanner(System.in);

	private ParticipantList participantList = new ParticipantList();
	private TeamList teamList = new TeamList();
	private EventList eventList = new EventList();
	private Message messagePrinter = new Message();

	// __________Ask-metoder_____________________

	private String askString(String s) {
		System.out.print(s);
		return readAndTrimString(s);
	}

	private int askInt(String s) {
		System.out.print(s);
		return readInt();
	}

	// ______Read-metoder________________________

	private int readInt() {
		int i = keyboard.nextInt();
		keyboard.nextLine();
		return i;
	}

	private double readDouble() {
		double i = keyboard.nextDouble();
		keyboard.nextLine();
		return i;
	}

	private String readString() {
		return keyboard.nextLine();
	}

	// _____Trim, validate and normalize-metoder_____________________

	private String readAndTrimString(String s) {
		String userInput = keyboard.nextLine().trim();
		return validationControl(userInput, s);
	}

	private String validationControl(String userInput, String s) {
		userInput = isEmptyString(userInput, s);
		return normalizeString(userInput);
	}

	private String isEmptyString(String userInput, String s) {
		while (userInput.equals("") || userInput.equals(null)) {
			System.out.println("Can't be empty");
			System.out.print(s);
			userInput = keyboard.nextLine().trim();
		}
		return userInput;
	}

	private String normalizeString(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	// commands

	private void addEvent() {
		String eventName = askString("Event name? ");
		if (eventList.isEvent(eventName)) {
			System.out.println("Event already exists");
		} else {
			int attemptsAllowed = askInt("Attempts allowed? ");
			attemptsAllowed = isAttemptsGreaterThanZero(attemptsAllowed);
			eventList.addEvent(eventName, attemptsAllowed);
			System.out.println(normalizeString(eventName) + " added");
		}
	}

	private int isAttemptsGreaterThanZero(int attemptsAllowed) {
		while (attemptsAllowed <= 0) {
			System.out.println("Too low, attempts must be greater than 0");
			attemptsAllowed = askInt("Attempts allowed? ");
		}
		return attemptsAllowed;
	}

	private void addParticipant() {
		String firstName = askString("First name? ");
		String lastName = askString("Last name? ");
		String teamName = askString("Team name? ");

		if(isTeam(teamName)) {
			participantList.addParticipant(firstName, lastName, teamList.getTeam(teamName));
		} else {
			teamList.addTeam(teamName);
			participantList.addParticipant(firstName, lastName, teamList.getTeam(teamName));
		}
	}

	private boolean isTeam(String teamName) {
		if(teamList.isTeam(teamName)) {
			return true;
		} else {
			return false;
		}
	}

	private void removeParticipant() {

		int participantNumber = askInt("Number? ");

		if (isParticipant(participantNumber)) {

			String teamName = participantList.getParticipantByNumber(participantNumber).getTeam().getTeamName();
			participantList.removeParticipant(participantNumber);

			if(participantList.isTeamEmpty(teamName)) {
				teamList.removeTeam(teamName);
			}
		} else {
			System.out.println("Participant with number " +participantNumber+ " does not exist");
		}
	}

	private void addResult() {

		int participantNumber = askInt("Number? ");
		if(!(isParticipant(participantNumber))) {
			return;
		}
		Participant participant = participantList.getParticipantByNumber(participantNumber);

		String eventName = askString("Event? ");
		if(!(isEvent(eventName))) {
			return;
		}
		Event event = eventList.getEventByName(eventName);

		if(!(isAttemptAllowed(participant, event))) {
			return;
		}

		double result = getResultValue(participant, event);
		participantList.addResult(participantNumber, event, result);
	}

	private boolean isParticipant(int participantNumber) {
		if(participantList.checkParticipantNumber(participantNumber) && participantList.getParticipantByNumber(participantNumber) != null) {
			return true;
		} else {
			System.out.println("No participant with number " + participantNumber + " found!");
			return false;
		}
	}

	private boolean isEvent(String eventName) {
		if(!eventList.isEvent(eventName)) {
			System.out.println("No event called \"" + eventName + "\" found!");
			return false;
		} else {
			return true;
		}
	}

	private boolean isAttemptAllowed(Participant participant, Event event) {
		if(participant.getResultList().getQtyOfResultsByEvent(event) >= event.getAttemptsAllowed()) {
			System.out.println(participant.toString() + " has already made his " + event.getAttemptsAllowed() + " attempts in " + event.getEventName());
			return false;
		} else {
			return true;
		}
	}

	private double getResultValue(Participant participant, Event event) {
		System.out.print("Result for " +participant.toString()+ " in " +event.getEventName()+ ": ");
		double result = readDouble();

		while(result < 0) {
			System.out.println("Must be greater than or equal to zero!");
			System.out.print("Add result for " +participant.toString()+ " in " +event.getEventName()+ ": ");
			result = readDouble();
		}
		return result;
	}

	private void listParticipantResults() {
		int participantNumber = askInt("Number? ");
		String list = eventList.listResults(participantList.getParticipantByNumber(participantNumber));
		System.out.println(list);
	}

	private void listEventResults(String eventName) {
		ArrayList<Result> list = participantList.listResultsByEvent(eventName);
		if(list != null && !(list.isEmpty())) {
			String resultList = participantList.getResultlistByEvent(list, eventName);
			System.out.println(resultList);
		} else {
			System.out.println("No results to show.");
		}
	}

	private void listTeamsResults() {
		ArrayList<Event> listOfEvents = eventList.getEventList();
		ArrayList<Team> listOfTeams = teamList.getTeamList();
		String resultList = participantList.listTeamsResults(listOfEvents, listOfTeams);
		System.out.println(resultList);
	}

	private String checkIfMessage(String cmd) {
		String message = "";
		if(cmd.length() > 7 && cmd.substring(0, 7).equals("message")) {
			message = cmd.substring(0, 7);
		} else {
			message = "";
		}
		return message;
	}

	// ____Setup, runC, closeDown, Run__________

	private void setup() {

	}

	private void runCommand() {

		boolean running = true;
		while (running){
			System.out.print("Command>");

			String cmd = readString().toLowerCase().trim();
			String message = checkIfMessage(cmd);

			if(message.equalsIgnoreCase("message")) {
				messagePrinter.printMessage(cmd);
			}
			else if (eventList.isEvent(cmd)) {
				listEventResults(normalizeString(cmd));
			}
			else {

				switch (cmd) {
				case "add event":
					addEvent();
					break;
				case "add participant":
					addParticipant();
					break;
				case "remove participant":
					removeParticipant();
					break;
				case "add result":
					addResult();
					break;
				case "participant":
					listParticipantResults();
					break;
				case "teams":
					listTeamsResults();
					break;
				case "exit":
					running = false;
					break;
				default:
					System.out.println("Wrong command, try again!\n");
				}
				System.out.println();
			}
		}
	}

	private void closeDown() {
		keyboard.close();
	}

	private void run() {
		setup();
		runCommand();
		closeDown();
	}

	// _________MAIN_____________________________

	public static void main(String[] args) {

		CompetitionMain program = new CompetitionMain();
		program.run();
	}
}
