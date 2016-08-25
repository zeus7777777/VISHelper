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
		return null;
	}
	
	public int getEffect()
	{
		return 0;
	}
}
