package com.mixerbox.hackathon.vis;
import java.util.ArrayList;

public class Match {
	int numGame;
	ArrayList<Game> games ;
	String oppositeTeamName;
	int winScore, maxScore, lastGameWinScore;
	int myGamePoint, oppositeGamePoint;
	Team myTeam;
	String dateTime;
	String note = "";
	
	public Match(int _numGame, String oppo, int _winScore, int _maxScore, int _lastGameWinScore, Team _team)
	{
		numGame = _numGame;
		oppositeTeamName = oppo;
		winScore = _winScore;
		maxScore = _maxScore;
		lastGameWinScore = _lastGameWinScore;
		myTeam = _team;
	}
	
	public void addGame(Game _game)
	{
		//games.add(new Game());
	}
	
	
}
