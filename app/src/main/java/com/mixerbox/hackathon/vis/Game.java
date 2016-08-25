package com.mixerbox.hackathon.vis;
import java.util.ArrayList;

public class Game {
	int myScore, oppositeScore;
	Team myTeam;
	ArrayList<Record> recordList;
	Player[] gameLocation; // 0-6
	
	public Game(Team _team, int _myScore, int oppoScore)
	{
		myTeam = _team;
		recordList = new ArrayList<>();
		myScore = _myScore;
		oppositeScore = oppoScore;
		gameLocation = new Player[7];
	}
	
	public void shiftClockWise()
	{
		Player tmp = gameLocation[0];
		for(int i=0;i<5;i++)
		{
			gameLocation[i] = gameLocation[i+1];
		}
		gameLocation[5] = tmp;
	}
	
	public void shiftCounterClockWise()
	{
		Player tmp = gameLocation[5];
		for (int i=5;i>0;i--)
		{
			gameLocation[i] = gameLocation[i-1];
		}
		gameLocation[0] = tmp;
	}

	public  void  liberoUp()
	{
		Player tmp = gameLocation[6];
		gameLocation[6] = gameLocation[0];
		gameLocation[0] = tmp;
	}

	public void liberoDown()
	{
		Player tmp = gameLocation[6];
		gameLocation[6] = gameLocation[3];
		gameLocation[3] = tmp;
	}

	public void substitute(Player playerUp, int loc)
	{
		gameLocation[loc] = playerUp;
	}
	
	public void setLocation(int loc, Player player)
	{
		gameLocation[loc] = player;
	}
	
	public void setLocation(Player[] players)
	{
		gameLocation = players;
	}
	
	public void addRecord(Record record)
	{
		recordList.add(record);
		updateScore(record.getEffect());
	}
	
	public void popRecord()
	{
		updateScore(recordList.get(recordList.size()-1).getEffect()*-1);
		recordList.remove(recordList.size()-1);
	}
	
	public void editRecord(Record record, int pos)
	{
		updateScore(recordList.get(pos).getEffect()*-1);
		recordList.remove(pos);
		recordList.add(record);
		updateScore(record.getEffect());
	}
	
	public void deleteRecord(int pos)
	{
		updateScore(recordList.get(pos).getEffect()*-1);
		recordList.remove(pos);
	}
	
	public void insertRecord(Record record, int pos)
	{
		updateScore(record.getEffect());
		recordList.add(pos, record);
	}
	
	public void updateScore(int effect)
	{
		if (effect==1)
			myScore++;
		else if (effect==-1)
			oppositeScore++;
	}
}
