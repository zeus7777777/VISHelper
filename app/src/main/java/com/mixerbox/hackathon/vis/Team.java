package com.mixerbox.hackathon.vis;
import java.util.ArrayList;

public class Team {
	ArrayList<Player> playerList;
	String teamName;
	
	public Team(String _teamName, ArrayList<Player> _playerList)
	{
		teamName = _teamName;
		playerList = _playerList;
	}
	
	public Team(String _teamName)
	{
		teamName = _teamName;
	}
	
	public void setTeamName(String _teamName)
	{
		teamName = _teamName;
	}
	
	public void addPlayer()
	{
	}
	
	public void deletePlayerByIndex(int index)
	{
	}
	
	public Player getPlayer(int index)
	{
		return null;
	}
	
	public void movePlayer(int from, int to)
	{
	}
}
