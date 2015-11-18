package com.GameBook;

/**
 * Created by Joshua Dalton on 11/11/2015.
 */
public class Game
{
    private String game;
    private String name;
    private String server;

    Game(){}

    Game(String g, String n, String s)
    {
        game = g.toString();
        name = n.toString();
        server = s.toString();
    }

    public void setGame(String g) {game = g.toString();}
    public void setName(String n) {name = n.toString();}
    public void setServer(String s) {server = s.toString();}

    public String getGame(){return game;}
    public String getName(){return name;}
    public String getServer(){return server;}
}
