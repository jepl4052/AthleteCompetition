/*
 * Jens Plate. jepl4052
 * Erik Hörnström, erho7892
 */

import java.util.ArrayList;

public class TeamList {

	private ArrayList<Team> listOfTeams = new ArrayList<>();

	public TeamList () {

	}

	public void addTeam(String teamName) {
		listOfTeams.add(new Team(teamName));
	}

	public ArrayList<Team> getTeamList() {
		return listOfTeams;
	}

	public boolean isTeam(String teamName) {

		for(Team team : listOfTeams) {
			if (team.getTeamName().equals(teamName))
				return true;
		}
		return false;
	}

	public Team getTeam(String teamName) {
		for(Team team : listOfTeams) {
			if(team.getTeamName().equals(teamName)) {
				return team;
			}
		}
		return null;
	}

	public void removeTeam(String teamName) {
		Team team = getTeam(teamName);
		listOfTeams.remove(team);
	}

	public String toString() {
		String s = "";
		for(Team team : listOfTeams) {
			s += team.toString() + "\n";
		}
		return s;
	}

}
