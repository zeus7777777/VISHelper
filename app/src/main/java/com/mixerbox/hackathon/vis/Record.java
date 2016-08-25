package com.mixerbox.hackathon.vis;

public class Record {
	RecordType recordType;
	String substitutionUp, substitutionDown; 
	ActionType actionType;
	ActionResultType actionResultType;
	String playerName;
	
	public Record(RecordType _recordType)
	{
		recordType = _recordType;
	}
	
	public Record(RecordType _recordType, String up, String down)
	{
		recordType = _recordType;
		substitutionUp = up;
		substitutionDown = down;
	}
	
	public Record(RecordType _recordType, String _playerName,
			ActionType _actionType, ActionResultType _actionResultType)
	{
		recordType = _recordType;
		playerName = _playerName;
		actionType = _actionType;
		actionResultType = _actionResultType;
	}
	
	@Override
	public String toString()
	{
		if(recordType==RecordType.TEAM_FAULT)
		{
			return "Team fault occurred.";
		}
		else if(recordType==RecordType.OPPONENT_ERROR)
		{
			return "Opponent error.";
		}
		else if(recordType==RecordType.SUBSTITUTION)
		{
			return substitutionUp+" up. " + substitutionDown + " down.";
		}
		else
		{
			return playerName +" " + actionType.toString().toLowerCase() + " -> "
				+ actionResultType.toString().toLowerCase();
		}
	}
	
	public int getEffect()
	{
		if(recordType==RecordType.OPPONENT_ERROR)
			return 1;
		if(recordType==RecordType.TEAM_FAULT)
			return -1;
		if(recordType==RecordType.SUBSTITUTION)
			return 0;
		if(recordType==RecordType.ACTION)
		{
			if(actionResultType==ActionResultType.FAULT)
				return -1;
			if(actionResultType==ActionResultType.ATTEMPT)
				return 0;
			if(actionResultType==ActionResultType.EXCELLENT)
				return 1;
		}
		return 0;
	}
}
