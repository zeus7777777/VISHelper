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
	
	public void addPlayer(Player player)
	{
		playerList.add(player);
	}
	
	public void deletePlayerByIndex(int index)
	{
		playerList.remove(index);
	}
	
	public Player getPlayer(int index)
	{
		return playerList.get(index);
	}
	
	public void movePlayer(int from, int to)
	{
		Player tmp;
		if(from < to)
		{
			if(to==playerList.size()-1)
			{
				playerList.add(playerList.get(from));
			}
			else
			{
				playerList.add(to+1, playerList.get(from));
			}
			playerList.remove(from);
		}
		else
		{
			playerList.add(to, playerList.get(from));
			playerList.remove(from+1);
		}
	}
}
