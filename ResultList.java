/*
 * Jens Plate. jepl4052
 * Erik Hörnström, erho7892
 */

import java.util.ArrayList;

public class ResultList {

	private ArrayList<Result> listOfResults = new ArrayList<>();

	public ResultList () {

	}

	public void addResult(Result result) {
		listOfResults.add(result);
	}

	private boolean isResultInList(String eventName) {

		for(Result result : listOfResults) {
			if(result.getEvent().getEventName().equals(eventName)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkNoOfAttempts(Event event, Participant participant) {
		int count = 0;
		for(Result result : listOfResults) {
			if(participant.equals(result.getParticipant())) {
				count++;
			}
		}
		if(event.getAttemptsAllowed() <= count) {
			return true;
		} else {
			return false;

		}
	}

	public Result getBestResultByEventName(String eventName) {

		if(listOfResults.isEmpty()) {
			return null;
		}
		if(isResultInList(eventName)) {
			return findBestResultByEventName(eventName);
		} else {
			return null;
		}
	}

	private Result findBestResultByEventName(String eventName) {
		double bestResultValue = 0;
		Result bestResult = null;

		for (Result result : listOfResults) {
			if (result.getResult() > bestResultValue && result.getEvent().getEventName().equals(eventName)) {
				bestResult = result;
				bestResultValue = result.getResult();
			}
		}
		return bestResult;
	}

	public double[] getResultsByEvent(Event event) {

		double[] resultValues = new double[getQtyOfResultsByEvent(event)];
		int index = 0;

		for(Result result : listOfResults) {

			if(result.getEvent().equals(event)) {
				resultValues[index] = result.getResult();
				index++;
			}

		}
		return resultValues;
	}

	public int getQtyOfResultsByEvent(Event event) {
		int count = 0;
		for(Result result : listOfResults) {
			if(result.getEvent().equals(event)) {
				count++;
			}
		}
		return count;
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < listOfResults.size(); i++) {
			s += listOfResults.get(i).toString() + "\n";
		}
		return s;
	}
}
