package com.mixerbox.hackathon.vis;

public class Record {
	RecordType recordType;
	String substitutionUp, substitutionDown; 
	ActionType actionType;
	ActionResultType actionResultType;
	Player player;
	
	public Record(RecordType _recordType)
	{
	}
	
	public Record(RecordType _recordType, Player up, Player down)
	{
	}
	
	public Record(RecordType _recordType, Player player, 
			ActionType _actionType, ActionResultType _actionResultType)
	{
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
