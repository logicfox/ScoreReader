package com.blah.reader;


public class Match {

	private String matchName;
	private String location;
	private String score;
	private String playerScore;
	private String matchStatus;
	
	public String getMatchName() {
		return matchName;
	}
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getPlayerScore() {
		return playerScore;
	}
	public void setPlayerScore(String playerScore) {
		this.playerScore = playerScore;
	}
	public String getMatchStatus() {
		return matchStatus;
	}
	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}
	
	@Override
	public String toString() {		
		return String.format("(matchName=%s, location=%s, score=%s, playerScore=%s, matchStatus=%s)", matchName, location, score, playerScore, matchStatus);
	}
}
