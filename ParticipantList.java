/*
 * Jens Plate. jepl4052
 * Erik Hörnström, erho7892
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ParticipantList {

	private ArrayList<Participant> listOfParticipants = new ArrayList<>();

	public ParticipantList() {

	}

	public void addParticipant(String firstName, String lastName, Team team) {
		Participant participant = new Participant(firstName, lastName, team);
		listOfParticipants.add(participant);
		System.out.println(participant.toString() + " added");

	}

	public void addResult(int participantNumber, Event event, double result) {
		getParticipantByNumber(participantNumber).addResult(event, result);
	}

	public boolean isTeamEmpty(String teamName) {
		for(Participant participant : listOfParticipants) {
			if(participant.getTeam().getTeamName().equals(teamName)) {
				return false;
			}
		}
		return true;
	}

	public boolean checkParticipantNumber(int participantNumber) {
		for (Participant participant : listOfParticipants) {
			if (participant.getParticipantNumber() == participantNumber) {
				return true;
			}
		}
		return false;
	}

	public Participant getParticipantByNumber(int participantNumber) {
		for(Participant participant : listOfParticipants) {
			if(participant.getParticipantNumber() == participantNumber) {
				return participant;
			}
		}
		return null;
	}

	public void removeParticipant(int participantNumber) {
		for(Participant participant : listOfParticipants) {
			if(participant.getParticipantNumber() == participantNumber) {

				this.listOfParticipants.remove(participant);

				System.out.println(participant.toString() + " removed");

				return;
			}
		}
	}

	public ArrayList<Result> listResultsByEvent(String eventName) {

		ArrayList<Result> list = getBestResultFromEachParticipantInEvent(eventName);

		if(!(list == null)) {
			return sortList(list);
		}
		return null;
	}

	private ArrayList<Result> getBestResultFromEachParticipantInEvent(String eventName) {
		ArrayList<Result> list = new ArrayList<>();

		for(Participant participant : listOfParticipants) {

			Result bestResult = participant.getBestResultByEventName(eventName);
			if(bestResult != null) {
				list.add(bestResult);
			}
		}
		return list;
	}

	private ArrayList<Result> sortList(ArrayList<Result> list) {
		Collections.sort(list, new Comparator<Result>() {
			public int compare(Result firstResult, Result secondResult) {
				return Double.compare(firstResult.getResult(), secondResult.getResult());
			}
		});
		Collections.reverse(list);
		return list;
	}

	public String getResultlistByEvent (ArrayList<Result> list, String eventName) {

		String resultList = "Results for " + eventName + "\n";

		int count = 1;
		int position = 1;
		double lastresult = 0;

		for(Result result : list) {

			if(lastresult != result.getResult()) {
				position = count;
			}

			resultList += position + " " + result.getResult() + " " + result.getParticipant().getFullName() + "\n";
			lastresult = result.getResult();
			count++;
		}

		return resultList;
	}

	public String listTeamsResults(ArrayList<Event> listOfEvents, ArrayList<Team> listOfTeams) {

		int[][] medalList = calculateMedalList(listOfEvents, listOfTeams);
		String resultList = String.format("%n%-6s %-6s %-6s %-1s %n", "1st", "2nd", "3rd", "Team name");

		for(int i = 0; i < 30; i++) {
			resultList += "*";
		}
		resultList += String.format("%n");

		if(medalList.length > 0) {
			int[][] sortedMedalList = sortMedalList(medalList, listOfTeams);
			resultList += formatMedalList(sortedMedalList, listOfTeams);
		} else {
			resultList += "No result for any team yet...\n";
		}
		return resultList;
	}

	private int[][] calculateMedalList(ArrayList<Event> listOfEvents, ArrayList<Team> listOfTeams) {
		int[][] medalList = new int[listOfTeams.size()][4];
		//fack [3] är teamIndex

		for(int i = 0; i < listOfTeams.size(); i++) {
			medalList[i][3] = i;
		}

		for(Event event : listOfEvents) {

			ArrayList<Result> temp = listResultsByEvent(event.getEventName());

			int position = 0;
			int count = 0;
			double lastResult = 0;

			for(Result result : temp) {
				if(lastResult > result.getResult()) {
					position = count;
				}
				if(position > 2) {
					break;
				}

				medalList[listOfTeams.indexOf(result.getParticipant().getTeam())][position]++;
				lastResult = result.getResult();
				count++;
			}
		}
		return medalList;
	}

	private int[][] sortMedalList(int[][] medalList, ArrayList<Team> listOfTeams) {

		int[][] tempListForReturn = new int[medalList.length][4];
		int[][] tempList = medalList.clone();
		int[] tempBest = new int[4];
		int highestIndex = -1;

		for (int i = 0; i<medalList.length; i++) {

			for(int j = 0; j < medalList.length; j++) {

				if(tempList[j][3] != -1) {

					highestIndex = j;
					System.arraycopy(tempList[j], 0, tempBest, 0, 4);
					break;
				}
			}

			for(int n = 0; n < medalList.length; n++) {

				if(tempList[n][3] != -1) {

					if(medalList[n][0] > tempBest[0]) {

						highestIndex = n;
						System.arraycopy(medalList[n], 0, tempBest, 0, 4);

					} else if (medalList[n][0] == tempBest[0]) {

						if(medalList[n][1] > tempBest[1]) {

							highestIndex = n;
							System.arraycopy(medalList[n], 0, tempBest, 0, 4);

						} else if (medalList[n][1] == tempBest[1]) {

							if(medalList[n][2] > tempBest[2]) {

								highestIndex = n;
								System.arraycopy(medalList[n], 0, tempBest, 0, 4);

							} else if(medalList[n][2] == tempBest[2]) {
								
								int res = listOfTeams.get(medalList[n][3]).getTeamName().compareTo(listOfTeams.get(tempBest[3]).getTeamName());
								
								if(res < 0) {
									highestIndex = n;
									System.arraycopy(medalList[n], 0, tempBest, 0, 4);
								}
							}
						}
					}
				}
			}

			System.arraycopy(tempBest, 0, tempListForReturn[i], 0, 4);

			tempList[highestIndex][3] = -1;
			highestIndex = -1;
		}

		return tempListForReturn;
	}

	private String formatMedalList(int[][] sortedMedalList, ArrayList<Team> listOfTeams) {
		String resultList = "";
		for(int s = 0; s < sortedMedalList.length; s++) {
			resultList += String.format("%-6d %-6d %-6d %-1s %n", sortedMedalList[s][0], sortedMedalList[s][1], sortedMedalList[s][2], listOfTeams.get(sortedMedalList[s][3]).getTeamName());
		}
		return resultList;
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < listOfParticipants.size(); i++) {
			s += listOfParticipants.get(i).toString() + "\n";
		}
		return s;
	}
}
