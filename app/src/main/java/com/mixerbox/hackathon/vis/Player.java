package com.mixerbox.hackathon.vis;

public class Player {
    String name, number, nickName;
    Position position;


    public Player(String _name, String _nickName, Position _position, String _number)
    {
        name = _name;
        nickName = _nickName;
        position = _position;
        number = _number;
    }

    public void editPlayer(String _nickName, Position _position, String _number)
    {
        nickName = _nickName;
        position = _position;
        number = _number;
    }
}
