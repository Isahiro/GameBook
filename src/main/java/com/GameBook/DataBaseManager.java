package com.GameBook;

/**
 * Created by Joshua Dalton, Melissa Stricker, and Jake Hatfield on 11/4/2015.
 */
import org.json.simple.JSONObject;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * This class will serve as the manager between the Java application and
 * the database.  This class creates the connection obtained through Tomcat
 */
public class DataBaseManager
{
    private Connection connection;
    private PreparedStatement prepStatement;
    private Statement statement;
    private ResultSet results;

    DataBaseManager()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone_gamespace",
                    "root", "ITTCapstone");
            statement = connection.createStatement();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAccounts(int currentUser)
    {
        ArrayList<String> users = new ArrayList<String>();
        try
        {
            String query = "SELECT accountUsername from account WHERE accountID != " + currentUser;
            results = statement.executeQuery(query);

            while(results.next() )
            {
                String user = results.getString("accountUsername");
                users.add(user);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return users;
    }

    public ArrayList<String> getAccounts(String input)
    {
        ArrayList<String> users = new ArrayList<String>();
        try
        {
            String query = "select * from account where locate('" + input + "', accountUsername)";
            results = statement.executeQuery(query);

            while(results.next() )
            {
                String user = results.getString("accountUsername");
                users.add(user);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return users;
    }

    public ArrayList<Game> getGames(String username)
    {
        ArrayList<Game> games = new ArrayList<Game>();

        try
        {
            String query = "select contactCharacter_AccountName, contactGame_ServiceName, contactServerName from contact" +
                    " join account on contact.FK_accountID = account.accountID where account.accountUsername = \"" +
                    username + "\"";
            results = statement.executeQuery(query);

            while(results.next() )
            {
                Game game = new Game();
                if(results.getString("contactCharacter_AccountName") != null)
                {
                    game.setName(results.getString("contactCharacter_AccountName"));
                }
                else
                    game.setName("N/A");
                if(results.getString("contactGame_ServiceName") != null)
                {
                    game.setGame(results.getString("contactGame_ServiceName"));
                }
                else
                    game.setGame("N/A");
                if(results.getString("contactServerName") != null)
                {
                    game.setServer(results.getString("contactServerName"));
                }
                else
                    game.setServer("N/A");
                games.add(game);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return games;
    }

    public ArrayList<CalEvent> getEvents(Date date)
    {
        ArrayList<CalEvent> events = new ArrayList<CalEvent>();

        try
        {
            String query = ("select * from calendarevent where eventDateTime >= \"" +
                    (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate() +
                    "\" order by eventDateTime asc");
            results = statement.executeQuery(query);

            while(results.next() )
            {
                CalEvent event = new CalEvent();

                event.setName(results.getString("eventName"));

                // Get the date as a string, then convert to a java.util.Date
                String strDate = results.getString("eventDateTime");
                int year = Integer.parseInt(strDate.substring(0, 4) ) - 1900;
                int month = Integer.parseInt(strDate.substring(5, 7) ) - 1;
                int dateInMonth = Integer.parseInt(strDate.substring(8, 10) );
                int hours = Integer.parseInt(strDate.substring(11, 13) );
                int minutes = Integer.parseInt(strDate.substring(14, 16) );

                // Initialize  the date
                event.setDate(new java.util.Date(year, month, dateInMonth) );

                // Set the time for the event
                event.getDate().setHours(hours);
                event.getDate().setMinutes(minutes);

                if(results.getString("eventGame") != null)
                {
                    event.setGame(results.getString("eventGame"));
                }
                else
                {
                    event.setGame("N/A");
                }
                if(results.getString("eventLocation") != null)
                {
                    event.setLocation(results.getString("eventLocation"));
                }
                else
                {
                    event.setLocation("N/A");
                }
                if(results.getString("eventGame") != null)
                {
                    event.setDescription(results.getString("eventDescription"));
                }
                else
                {
                    event.setDescription("N/A");
                }

                events.add(event);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return events;
    }

    public ArrayList<CalEvent> getEvents(Date date, String input, String column)
    {
        ArrayList<CalEvent> events = new ArrayList<CalEvent>();

        try
        {
            String query = ("select * from calendarevent where eventDateTime >= \"" +
                    (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate() +
                    "\" and locate(\"" + input + "\", " + column + ") order by eventDateTime asc");
            results = statement.executeQuery(query);

            while(results.next() )
            {
                CalEvent event = new CalEvent();

                event.setName(results.getString("eventName"));

                // Get the date as a string, then convert to a java.util.Date
                String strDate = results.getString("eventDateTime");
                int year = Integer.parseInt(strDate.substring(0, 4) ) - 1900;
                int month = Integer.parseInt(strDate.substring(5, 7) ) - 1;
                int dateInMonth = Integer.parseInt(strDate.substring(8, 10) );
                int hours = Integer.parseInt(strDate.substring(11, 13) );
                int minutes = Integer.parseInt(strDate.substring(14, 16) );

                // Initialize  the date
                event.setDate(new java.util.Date(year, month, dateInMonth) );

                // Set the time for the event
                event.getDate().setHours(hours);
                event.getDate().setMinutes(minutes);

                if(results.getString("eventGame") != null)
                {
                    event.setGame(results.getString("eventGame"));
                }
                else
                {
                    event.setGame("N/A");
                }
                if(results.getString("eventLocation") != null)
                {
                    event.setLocation(results.getString("eventLocation"));
                }
                else
                {
                    event.setLocation("N/A");
                }
                if(results.getString("eventGame") != null)
                {
                    event.setDescription(results.getString("eventDescription"));
                }
                else
                {
                    event.setDescription("N/A");
                }

                events.add(event);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return events;
    }

    public int login(String username, String password)
    {
        int userId = 0;

        try
        {
            // Query for a matching login
            String query = "select accountID, accountUsername, accountPassword from account where accountUsername = '" +
                    username + "' and accountPassword = '" + password + "'";
            results = statement.executeQuery(query);

            while(results.next() )
            {
                userId = results.getInt("accountId");

                System.out.println(username + " was located as accountId " + userId);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return userId;
    }

    public boolean checkUsername(String uName)
    {
        try
        {
            String query = "select accountUsername from account";
            results = statement.executeQuery(query);

            while(results.next() )
            {
                if(uName.equalsIgnoreCase(results.getString("accountUsername") ) )
                {
                    return true;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public void addAccount(JSONObject newUser)
    {
        // Construct a query from the JSON object obtained in the registration form
        String query = "INSERT INTO account(accountUsername, accountPassword, accountEmail, accountFirstName, " +
                "accountLastName, accountBirthday) VALUES (";
        query += "\"" + newUser.get("username").toString() + "\", ";
        query += "\"" + newUser.get("password").toString() + "\", ";
        query += "\"" + newUser.get("email").toString() + "\", ";
        query += "\"" + newUser.get("fName").toString() + "\", ";
        query += "\"" + newUser.get("lName").toString() + "\", ";
        query += "\"" + newUser.get("year").toString() + "-";

        // convert the month to an int and add a 0 placeholder if less than 10
        int month = Integer.parseInt(newUser.get("month").toString() );
        if(month < 10)
            query += "0";
        query += month + "-";

        // convert the month to an int and add a 0 placeholder if less than 10
        int day = Integer.parseInt(newUser.get("day").toString() );
        if(day < 10)
            query += "0";
        query += day + "\")";

        try
        {
            prepStatement = connection.prepareStatement(query);
            prepStatement.execute();
            System.out.println("A new user " + newUser.get("username").toString() + " was created");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updatePassword(String newPassword, int currentUser)
    {
        try
        {
            String query = "update account set accountPassword = \"" + newPassword +
                    "\" where accountID = " + currentUser;
            prepStatement = connection.prepareStatement(query);
            prepStatement.execute();
            System.out.println("Password updated");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addEvent(String date, String name, String game, String location, String description)
    {
        try
        {
            String query = "insert into calendarevent (eventDateTime, eventName, eventGame, " +
                    "eventLocation, eventDescription) values (\"" + date + "\", \"" +
                    name + "\", \"" + game + "\", \"" + location + "\", \"" + description + "\")";
            prepStatement = connection.prepareStatement(query);
            prepStatement.execute();
            System.out.println("A new event \"" + name + "\" was created");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addGame_Service(String username, String game_Service, String server, int currentUser)
    {
        try
        {
            String query = "insert into contact (FK_accountID, contactCharacter_accountName," +
                    "contactGame_ServiceName, contactServerName) values (" + currentUser + ", \"" +
                    username + "\", \"" + game_Service + "\", \"" + server + "\")";
            prepStatement = connection.prepareStatement(query);
            prepStatement.execute();
            System.out.println("A new contact was created");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
