package com.mixerbox.hackathon.vis;
import java.util.ArrayList;

public class Game {
	int myScore, oppositeScore;
	Team myTeam;
	ArrayList<Record> recordList;
	Player[] gameLocation; // 0-5
	
	public Game(Team _team)
	{
		myTeam = _team;
	}
	
	public void shiftClockWise()
	{
	}
	
	public void shiftCounterClockWise()
	{
	}
	
	public void substitute(Player playerUp, int loc)
	{
	}
	
	public void setLocation(int loc, Player player)
	{
	}
	
	public void setLocation(Player[] player)
	{
	}
	
	public void addRecord(Record record)
	{
	}
	
	public void popRecord()
	{
	}
	
	public void editRecord(Record record, int pos)
	{
	}
	
	public void deleteRecord(int pos)
	{
	}
	
	public void insertRecord(Record record, int pos)
	{
	}
	
	public void updateScore(int effect)
	{
	}
}
