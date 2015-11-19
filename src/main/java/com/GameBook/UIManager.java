package com.GameBook;

import org.json.simple.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Joshua Dalton on 11/9/2015.
 */
public class UIManager extends JFrame
{
    private static int currentUser;
    private DataBaseManager DBManager;

    private Container contentPane;
    private JMenuBar menuBar;
    private JTextField txtUsername;
    private JPasswordField pasPassword;
    private GridLayout uiLayout;

    final Font TITLEFONT = new Font("Arial Black", Font.PLAIN, 20);
    final Font LARGEFONT = new Font("Arial", Font.PLAIN, 16);

    private JButton btnAccounts;
    private JButton btnEvents;

    public UIManager()
    {
        // Instantiate a manager for querying the database
        DBManager = new DataBaseManager();
        currentUser = 0;

        // Create the layout for the UI
        this.setTitle("Gamebook");
        contentPane = this.getContentPane();
        uiLayout = new GridLayout(20, 5, 1, 1);
        contentPane.setLayout(uiLayout);

        // Initialize the Accounts and Events buttons
        btnAccounts = new JButton("Accounts");
        btnEvents = new JButton("Events");

        // Set MouseListeners for Accounts and Events
        btnAccounts.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                accountsForm(UIManager.currentUser);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        btnEvents.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                eventsForm();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        // Initialize the content
        greeting();
        logout();
    }

    public void reDraw()
    {
        this.revalidate();
        this.repaint();
    }

    public void greeting()
    {
        JLabel labTitle = new JLabel("Gamebook");
        labTitle.setFont(TITLEFONT);
        JLabel labDescription = new JLabel("A contact manager for your gaming community");

        contentPane.add(labTitle);
        contentPane.add(labDescription);
    }

    public void logout()
    {
        // Clear the menu and reinitialize it
        if (menuBar != null)
            this.remove(menuBar);
        menuBar = new JMenuBar();

        // Initialize the login menu items
        JLabel labUsername = new JLabel(" Username: ");
        JLabel labPassword = new JLabel(" Password: ");
        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");

        txtUsername = new JTextField(null, 8);
        pasPassword = new JPasswordField(null, 8);

        btnLogin.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentUser = DBManager.login(txtUsername.getText(), pasPassword.getText());
                if (currentUser != 0)
                    login(txtUsername.getText());
                else {
                    contentPane.removeAll();
                    JLabel invLogin = new JLabel("Username and password do not match an existing login");
                    contentPane.add(invLogin);
                    contentPane.add(btnAccounts);
                    contentPane.add(btnEvents);
                    reDraw();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        btnRegister.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                registerForm();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        // Add the components to the menuBar
        menuBar.add(labUsername);
        menuBar.add(txtUsername);
        menuBar.add(labPassword);
        menuBar.add(pasPassword);
        menuBar.add(btnLogin);
        menuBar.add(btnRegister);

        contentPane.add(btnAccounts);
        contentPane.add(btnEvents);

        this.setJMenuBar(menuBar);
        reDraw();
    }

    public void login(String username)
    {
        // Clear the menu and reinitialize it
        if (menuBar != null)
            this.remove(menuBar);
        menuBar = new JMenuBar();

        JLabel labUser = new JLabel(" " + username + " ");
        JButton btnManage = new JButton("Manage Account");
        JButton btnLogout = new JButton("Logout");

        btnManage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manageForm();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        btnLogout.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentUser = 0;
                contentPane.removeAll();
                logout();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        menuBar.add(labUser);
        menuBar.add(btnManage);
        menuBar.add(btnLogout);

        contentPane.removeAll();
        JLabel labWelcome = new JLabel("Welcome " + username);
        labWelcome.setFont(TITLEFONT);
        contentPane.add(labWelcome);
        contentPane.add(btnAccounts);
        contentPane.add(btnEvents);

        this.setJMenuBar(menuBar);
        reDraw();
    }

    public void accountsForm(int currentUser)
    {
        // Set up default UI elements
        contentPane.removeAll();
        JLabel labAccounts = new JLabel("Accounts");
        labAccounts.setFont(TITLEFONT);
        contentPane.add(labAccounts);
        contentPane.add(btnEvents);

        // Add a TextField and button for queries
        final JTextField txtUsername = new JTextField();
        contentPane.add(txtUsername);
        JButton btnQuery = new JButton("Search usernames");

        // Set the MouseListener to search the database for usernames containing the text in the TextField
        btnQuery.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                queryAccounts(txtUsername.getText());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        contentPane.add(btnQuery);

        // Add accounts to the UI layout
        // Store the accounts in a ArrayList
        ArrayList<String> accounts = DBManager.getAccounts(UIManager.currentUser);

        // Create an ArrayList that will store the JLabels
        ArrayList<JLabel> labArray = new ArrayList<JLabel>();

        // For each user obtained create a JLabel and add it to the ArrayList
        for(int i = 0; i < accounts.size(); i++)
        {
            final JLabel label = new JLabel(accounts.get(i).toString() );

            // Set a MouseListener to detect if a user wishes to see the registered games
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    getRegisteredGames(label.getText());
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            labArray.add(label);
        }

        // For each label add it o the form
        for(int i = 0; i < labArray.size(); i++)
        {
            contentPane.add(labArray.get(i));
        }

        this.reDraw();
    }

    public void eventsForm()
    {
        // Set up Default UI Elements
        contentPane.removeAll();
        JLabel labEvents = new JLabel("Events");
        labEvents.setFont(TITLEFONT);
        contentPane.add(labEvents);
        contentPane.add(btnAccounts);

        // Add a section for querying events
        // Add radio buttons to allow searching by event name or by game
        final JRadioButton radName = new JRadioButton("Search by event name");
        final JRadioButton radGame = new JRadioButton("Search by game");

        // Create a ButtonGroup so that the radio buttons are mutually exclusive
        ButtonGroup radios = new ButtonGroup();
        radios.add(radName);
        radios.add(radGame);

        contentPane.add(radName);
        contentPane.add(radGame);

        final JTextField txtEventQuery = new JTextField();
        contentPane.add(txtEventQuery);
        JButton btnQuery = new JButton("Search usernames");

        // Set the MouseListener to search the database for usernames containing the text in the TextField
        btnQuery.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (radName.isSelected() == false && radGame.isSelected() == false) {
                    JOptionPane.showMessageDialog(null, "Please select whether to search by event name or by game");
                } else {
                    queryEvents(txtEventQuery.getText(), radName.isSelected());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        contentPane.add(btnQuery);

        JLabel labCreateEvent = new JLabel("Know an event not listed? ");
        contentPane.add(labCreateEvent);
        JButton btnCreateEvent = new JButton("Create an event");
        btnCreateEvent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addEvent();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        contentPane.add(btnCreateEvent);

        // Get all the events on or after today's date
        ArrayList<CalEvent> events = DBManager.getEvents(new Date());

        // Create an ArrayList that will store the JLabels
        ArrayList<JLabel> labArray = new ArrayList<JLabel>();

        // For each event obtained create a JLabel for the date and for the name
        for(int i = 0; i < events.size(); i++)
        {
            // Convert the date to a JLabel
            JLabel date = new JLabel((events.get(i).getDate().getMonth() + 1) + "/" +
                    events.get(i).getDate().getDate() + "/" + (events.get(i).getDate().getYear() + 1900) );
            labArray.add(date);

            // Create an EventLabel for the event
            final EventLabel label = new EventLabel(events.get(i) );

            // Set a MouseListener to detect if a user wishes to see the event
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    getEvent(label.getEvent());
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            labArray.add(label);
        }

        // For each label add it o the form
        for(int i = 0; i < labArray.size(); i++)
        {
            contentPane.add(labArray.get(i));
        }

        this.reDraw();
    }

    public void getEvent(CalEvent event)
    {
        // Clear the layout and add the details of the event
        contentPane.removeAll();
        JLabel name = new JLabel(event.getName() );
        name.setFont(TITLEFONT);
        JLabel date = new JLabel(event.getDate().toString() );
        JLabel game = new JLabel(event.getGame() );
        JLabel location = new JLabel(event.getLocation() );
        JLabel description = new JLabel(event.getDescription() );

        contentPane.add(btnEvents);
        contentPane.add(btnAccounts);
        contentPane.add(name);
        contentPane.add(date);
        contentPane.add(game);
        contentPane.add(location);
        contentPane.add(description);

        this.reDraw();
    }

    public void queryEvents(String input, boolean name) {
        contentPane.removeAll();
        contentPane.add(btnAccounts);
        contentPane.add(btnEvents);

        ArrayList<CalEvent> events = new ArrayList<CalEvent>();
        JLabel labResults = new JLabel();

        if(name)
        {
            labResults.setText("Events matching the name \"" + input + "\"");
        }
        else
        {
            labResults.setText("Events matching the game \"" + input + "\"");
        }

        labResults.setFont(TITLEFONT);
        contentPane.add(labResults);

        String column;

        if(name)
        {
            column = "eventName";
        }
        else
        {
            column = "eventGame";
        }

        events = DBManager.getEvents(new java.util.Date(), input, column);

        if(events.size() > 0)
        {
            // Create an ArrayList that will store the JLabels
            ArrayList<JLabel> labArray = new ArrayList<JLabel>();

            // For each event obtained create a JLabel for the date and for the name
            for(int i = 0; i < events.size(); i++)
            {
                // Convert the date to a JLabel
                JLabel date = new JLabel((events.get(i).getDate().getMonth() + 1) + "/" +
                        events.get(i).getDate().getDate() + "/" + (events.get(i).getDate().getYear() + 1900) );
                labArray.add(date);

                // Create an EventLabel for the event
                final EventLabel label = new EventLabel(events.get(i) );

                // Set a MouseListener to detect if a user wishes to see the event
                label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        getEvent(label.getEvent());
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });

                labArray.add(label);
            }

            // For each label add it o the form
            for(int i = 0; i < labArray.size(); i++)
            {
                contentPane.add(labArray.get(i));
            }
        }
        else
        {
            JLabel labNoMatches = new JLabel("No results were found");
            contentPane.add(labNoMatches);
        }

        this.reDraw();
    }

    public void registerForm()
    {
        System.out.println("Register button clicked");

        contentPane.removeAll();

        // Create the registration form
        JLabel labRegister = new JLabel("Register an account");
        labRegister.setFont(TITLEFONT);
        JButton btnBack = new JButton("Back");
        JLabel labRegUsername = new JLabel("Username: ");
        final JTextField txtRegUsername = new JTextField();
        JLabel labRegPassword = new JLabel("Enter a password: ");
        final JPasswordField pasRegPassword = new JPasswordField();
        JLabel labConfPassword = new JLabel("Confirm password: ");
        final JPasswordField pasConfPassword = new JPasswordField();
        JLabel labRegEmail = new JLabel("Enter an email address: ");
        final JTextField txtRegEmail = new JTextField();
        JLabel labRegFName = new JLabel("First name: ");
        final JTextField txtRegFName = new JTextField();
        JLabel labRegLName = new JLabel("Last name: ");
        final JTextField txtRegLName = new JTextField();
        JLabel labRegBDMonth = new JLabel("Month you were born: ");
        final JComboBox cmbMonth = new JComboBox();
        for(int i = 1; i <= 12; i++)
            cmbMonth.addItem(i);

        JLabel labRegBDDay = new JLabel("Day of the month you were born: ");
        final JComboBox cmbDay = new JComboBox();
        for(int i = 1; i <= 31; i++)
            cmbDay.addItem(i);

        JLabel labRegBDYear = new JLabel("Year you were born: ");
        final JTextField txtYear = new JTextField();
        JButton btnRegSubmit = new JButton("Submit");

        // Add the MouseListener for the back button
        btnBack.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPane.removeAll();
                logout();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        // Add the MouseListener for the submit button
        btnRegSubmit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //turn inputs into a JSON object
                JSONObject newUser = new JSONObject();
                newUser.put("username", txtRegUsername.getText() );
                newUser.put("password", pasRegPassword.getText() );
                newUser.put("confPassword", pasConfPassword.getText() );
                newUser.put("email", txtRegEmail.getText() );
                newUser.put("fName", txtRegFName.getText() );
                newUser.put("lName", txtRegLName.getText() );
                newUser.put("month", cmbMonth.getSelectedItem() );
                newUser.put("day", cmbDay.getSelectedItem() );
                newUser.put("year", txtYear.getText() );

                if (validateRegistration(newUser) )
                {
                    DBManager.addAccount(newUser);
                    currentUser = DBManager.login(txtRegUsername.getText(), pasRegPassword.getText() );
                    login(newUser.get("username").toString() );
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        // Add labels and inputs to the form
        contentPane.add(labRegister);
        contentPane.add(btnBack);
        contentPane.add(labRegUsername);
        contentPane.add(txtRegUsername);
        contentPane.add(labRegPassword);
        contentPane.add(pasRegPassword);
        contentPane.add(labConfPassword);
        contentPane.add(pasConfPassword);
        contentPane.add(labRegEmail);
        contentPane.add(txtRegEmail);
        contentPane.add(labRegFName);
        contentPane.add(txtRegFName);
        contentPane.add(labRegLName);
        contentPane.add(txtRegLName);
        contentPane.add(labRegBDMonth);
        contentPane.add(cmbMonth);
        contentPane.add(labRegBDDay);
        contentPane.add(cmbDay);
        contentPane.add(labRegBDYear);
        contentPane.add(txtYear);
        contentPane.add(btnRegSubmit);

        this.reDraw();
    }

    public boolean validateRegistration(JSONObject newUser)
    {
        boolean isValid = true;
        String errorMessage = "";
        for(int i = 3; i <= 19; i++)
            contentPane.getComponent(i).setForeground(Color.black);

        // Has the desired username been used previously
        if(DBManager.checkUsername(newUser.get("username").toString() ) )
        {
            contentPane.getComponent(3).setForeground(Color.red);
            errorMessage += "A user by that username already exists\n";
            isValid = false;
        }

        // Is the username at least 4 characters long
        else if(newUser.get("username").toString().isEmpty() ||
                newUser.get("username").toString().length() < 4)
        {
            contentPane.getComponent(3).setForeground(Color.red);
            errorMessage += "Usernames must be at least 4 characters long\n";
            isValid = false;
        }

        // Is the password at least 8 characters long
        if(newUser.get("password").toString().length() < 8 )
        {
            contentPane.getComponent(5).setForeground(Color.red);
            errorMessage += "Passwords must be at least 8 characters long\n";
            isValid = false;
        }

        String numRegex = ".*[0-9].*";
        String upperAlphaRegex = ".*[A-Z].*";
        String lowerAlphaRegex = ".*[a-z].*";

        // Make sure the password includes numbers, uppercase letters, and lowercase letters
        if(!(newUser.get("password").toString().matches(numRegex) &&
           newUser.get("password").toString().matches(upperAlphaRegex) &&
           newUser.get("password").toString().matches(lowerAlphaRegex) ) )
        {
            contentPane.getComponent(5).setForeground(Color.red);
            errorMessage += "Passwords must contain letters, uppercase letters, and lowercase numbers\n";
            isValid = false;
        }

        // Test that the password confirmation matches the first password
        if(!newUser.get("password").toString().equals(newUser.get("confPassword").toString()) )
        {
            contentPane.getComponent(7).setForeground(Color.red);
            errorMessage += "Confirmation password must match password\n";
            isValid = false;
        }

        // Establish a proper email pattern
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        java.util.regex.Pattern emailPattern = java.util.regex.Pattern.compile(emailRegex);
        java.util.regex.Matcher matcher = emailPattern.matcher(newUser.get("email").toString() );

        // Test the email field for a valid email pattern
        if(!matcher.matches() )
        {
            contentPane.getComponent(9).setForeground(Color.red);
            errorMessage += "Must register a valid email address\n";
            isValid = false;
        }

        //Ensure the first name field is not empty
        if(newUser.get("fName").toString().isEmpty() )
        {
            contentPane.getComponent(11).setForeground(Color.red);
            errorMessage += "Must enter a first name\n";
            isValid = false;
        }

        // Ensure the last name field is not empty
        if(newUser.get("lName").toString().isEmpty() )
        {
            contentPane.getComponent(13).setForeground(Color.red);
            errorMessage += "Must enter a last name\n";
            isValid = false;
        }

        int month = Integer.parseInt(newUser.get("month").toString() );
        int day = Integer.parseInt(newUser.get("day").toString() );
        int year = Integer.parseInt(newUser.get("year").toString() ) - 1900;
        // Java Dates are calculated as being 1900 less than the real year

        // Test if the date entered is valid
        if(!isValidBirthday(month, day, year) )
        {
            for(int i = 15; i <= 19; i += 2)
                contentPane.getComponent(i).setForeground(Color.red);
            errorMessage += "The date of birth must be a valid date and users must be at least 13 years of age\n";
            isValid = false;
        }

        //If the form is not valid display an error message and redraw the screen
        if(!isValid)
        {
            this.reDraw();
            JOptionPane.showMessageDialog(null, errorMessage);
        }

        return isValid;
    }

    public boolean isValidDate(int month, int day, int year)
    {
        int maxDaysInMonth = 0;

        switch (month) {
            case 1:
                maxDaysInMonth = 31;
                break;
            case 2:
                if (isLeapYear(year + 1900)) // Adding 1900 as a workaround because of how Dates are handled
                    maxDaysInMonth = 29;
                else
                    maxDaysInMonth = 28;
                break;
            case 3:
                maxDaysInMonth = 31;
                break;
            case 4:
                maxDaysInMonth = 30;
                break;
            case 5:
                maxDaysInMonth = 31;
                break;
            case 6:
                maxDaysInMonth = 30;
                break;
            case 7:
                maxDaysInMonth = 31;
                break;
            case 8:
                maxDaysInMonth = 31;
                break;
            case 9:
                maxDaysInMonth = 30;
                break;
            case 10:
                maxDaysInMonth = 31;
                break;
            case 11:
                maxDaysInMonth = 30;
                break;
            case 12:
                maxDaysInMonth = 31;
                break;
            default:
                contentPane.getComponent(15).setForeground(Color.red);
                break;
        }

        if (day > maxDaysInMonth)
            return false;
        else
            return true;
    }

    public boolean isValidBirthday(int month, int day, int year)
    {
        if(!isValidDate(month, day, year) )
            return false;

        Date currDate = new Date();

        if(currDate.getYear() - year > 13)
            return true;
        else if(currDate.getYear() - year == 13)
            if(currDate.getMonth() + 1 - month > 0)
                return true;
            else if(currDate.getMonth() + 1 - month == 0)
                if(currDate.getDate() - day >= 0)
                    return true;

        return false;
    }

    public boolean isLeapYear(int year)
    {
        if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
        {
            System.out.println(year + " is a leap year");
            return true;
        }
        else
        {
            System.out.println(year + " is not a leap year");
            return false;
        }
    }

    public void getRegisteredGames(String username)
    {
        ArrayList<Game> games = DBManager.getGames(username);

        contentPane.removeAll();
        contentPane.add(btnAccounts);
        contentPane.add(btnEvents);
        JLabel userGames = new JLabel(username + "'s Games/Services");
        userGames.setFont(TITLEFONT);
        contentPane.add(userGames);

        for(int i = 0; i < games.size(); i++)
        {
            Game g = games.get(i);
            JLabel labGame = new JLabel(g.getGame() );
            labGame.setFont(LARGEFONT);
            contentPane.add(labGame);
            JLabel labName = new JLabel(g.getName() );
            contentPane.add(labName);
            JLabel labServer = new JLabel(g.getServer() );
            contentPane.add(labServer);
        }

        this.reDraw();
    }

    public void queryAccounts(String input)
    {
        contentPane.removeAll();
        contentPane.add(btnAccounts);
        contentPane.add(btnEvents);
        JLabel labResults = new JLabel("Usernames containing \"" + input + "\"");
        contentPane.add(labResults);

        ArrayList<String> accounts = DBManager.getAccounts(input);

        if(accounts.size() > 0) // If users were found
        {
            // Create an ArrayList that will store the JLabels
            ArrayList<JLabel> labArray = new ArrayList<JLabel>();

            // For each user obtained create a JLabel and add it to the ArrayList
            for(int i = 0; i < accounts.size(); i++)
            {
                final JLabel label = new JLabel(accounts.get(i).toString() );

                // Set a MouseListener to detect if a user wishes to see the registered games
                label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        getRegisteredGames(label.getText() );
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });

                labArray.add(label);
            }

            // For each label add it o the form
            for(int i = 0; i < labArray.size(); i++)
            {
                contentPane.add(labArray.get(i));
            }
        }
        else // If no users were found
        {
            JLabel labNoMatch = new JLabel("No users were found containing \"" + input + "\"");
            contentPane.add(labNoMatch);
        }

        this.reDraw();
    }

    public void manageForm()
    {
        contentPane.removeAll();
        JLabel labManage = new JLabel("Edit Account");
        contentPane.add(labManage);
        JButton btnBack = new JButton("Back");

        btnBack.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPane.removeAll();
                contentPane.add(btnAccounts);
                contentPane.add(btnEvents);
                contentPane.revalidate();
                contentPane.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        contentPane.add(btnBack);

        // Option to update a password
        JLabel labNewPassword = new JLabel("Update your password: ");
        contentPane.add(labNewPassword);
        final JPasswordField pasNewPassword = new JPasswordField();
        contentPane.add(pasNewPassword);
        JLabel labConfNewPass = new JLabel("Confirm new password: ");
        contentPane.add(labConfNewPass);
        final JPasswordField pasConfNewPass = new JPasswordField();
        contentPane.add(pasConfNewPass);
        JButton btnUpdatePassword = new JButton("Update Password");
        btnUpdatePassword.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (validateNewPassword(pasNewPassword.getText(), pasConfNewPass.getText()))
                {
                    DBManager.updatePassword(pasNewPassword.getText(), currentUser);
                    pasNewPassword.setText(null);
                    pasConfNewPass.setText(null);
                    JOptionPane.showMessageDialog(null, "Password updated");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        contentPane.add(btnUpdatePassword);
        JLabel labAccountName = new JLabel("Username");

        // Add the ability to register a game or service
        contentPane.add(labAccountName);
        final JTextField txtAccountName = new JTextField();
        contentPane.add(txtAccountName);
        JLabel labGame_Service = new JLabel("Which game or service is this?");
        contentPane.add(labGame_Service);
        final JTextField txtGame_Service = new JTextField();
        contentPane.add(txtGame_Service);
        JLabel labServer = new JLabel("Name of the server if applicable: ");
        contentPane.add(labServer);
        final JTextField txtServer = new JTextField(null);
        contentPane.add(txtServer);
        JButton btnAddGame_Service = new JButton("Register a game or service: ");
        btnAddGame_Service.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!txtAccountName.getText().isEmpty() && !txtGame_Service.getText().isEmpty() )
                {
                    DBManager.addGame_Service(txtAccountName.getText(), txtGame_Service.getText(),
                            txtServer.getText(), currentUser);
                    txtAccountName.setText(null);
                    txtGame_Service.setText(null);
                    txtServer.setText(null);
                    contentPane.revalidate();
                    contentPane.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        contentPane.add(btnAddGame_Service);
        this.reDraw();
    }

    public boolean validateNewPassword(String newPass, String confPass)
    {
        boolean isValid = true;
        String errorMessage= "";

        if(newPass.length() < 8)
        {
            errorMessage += "Passwords must be at least 8 characters long\n";
            isValid = false;
        }
        String numRegex = ".*[0-9].*";
        String upperAlphaRegex = ".*[A-Z].*";
        String lowerAlphaRegex = ".*[a-z].*";

        // Make sure the password includes numbers, uppercase letters, and lowercase letters
        if (!(newPass.matches(numRegex) && newPass.matches(upperAlphaRegex) &&
                newPass.matches(lowerAlphaRegex) ) )
        {
            errorMessage += "Passwords must contain letters, uppercase letters, and lowercase numbers\n";
            isValid = false;
        }

        // Test that the password confirmation matches the first password
        if(!newPass.equals(confPass) )
        {
            errorMessage += "Confirmation password must match password\n";
            isValid = false;
        }

        if(!isValid)
            JOptionPane.showMessageDialog(null, errorMessage);

        return isValid;
    }

    public void addEvent()
    {
        contentPane.removeAll();
        contentPane.add(btnAccounts);
        contentPane.add(btnEvents);

        JLabel labNewEvent = new JLabel("Create an event");
        labNewEvent.setFont(TITLEFONT);
        contentPane.add(labNewEvent);
        contentPane.add(new JLabel() ); // This is used for formatting
        JLabel labName = new JLabel("Event Name: ");
        contentPane.add(labName);
        final JTextField txtName = new JTextField();
        contentPane.add(txtName);
        JLabel labGame = new JLabel("Game related if applicable: ");
        contentPane.add(labGame);
        final JTextField txtGame = new JTextField();
        contentPane.add(txtGame);
        JLabel labLocation = new JLabel("Location of the event: ");
        contentPane.add(labLocation);
        final JTextField txtLocation = new JTextField();
        contentPane.add(txtLocation);
        JLabel labDescription = new JLabel("Enter a description for the event: ");
        contentPane.add(labDescription);
        final JTextField txtDescription = new JTextField();
        contentPane.add(txtDescription);

        JLabel labEventMonth = new JLabel("Month of the event: ");
        contentPane.add(labEventMonth);
        final JComboBox cmbMonth = new JComboBox();
        for(int i = 1; i <= 12; i++)
            cmbMonth.addItem(i);
        contentPane.add(cmbMonth);

        JLabel labEventDay = new JLabel("Day of the month the event takes place: ");
        contentPane.add(labEventDay);
        final JComboBox cmbDay = new JComboBox();
        for(int i = 1; i <= 31; i++)
            cmbDay.addItem(i);
        contentPane.add(cmbDay);

        JLabel labEventYear = new JLabel("Year the event takes place: ");
        contentPane.add(labEventYear);
        final JTextField txtYear = new JTextField();
        contentPane.add(txtYear);

        JLabel labHour = new JLabel("Hour the event will occur (military hours): ");
        contentPane.add(labHour);
        final JComboBox cmbHour = new JComboBox();
        for(int i = 0; i < 24; i++)
            cmbHour.addItem(i);
        contentPane.add(cmbHour);

        JLabel labMinute = new JLabel("Minute of the hour the event starts: ");
        contentPane.add(labMinute);
        final JComboBox cmbMinute = new JComboBox();
        for(int i = 0; i < 60; i++)
            cmbMinute.addItem(i);
        contentPane.add(cmbMinute);

        JButton btnSubmitEvent = new JButton("Submit");

        btnSubmitEvent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isValidDate(Integer.parseInt(cmbMonth.getSelectedItem().toString()),
                        Integer.parseInt(cmbDay.getSelectedItem().toString()),
                        Integer.parseInt(txtYear.getText().toString()) - 1900)){
                    if (txtName.getText().toString().isEmpty())
                        JOptionPane.showMessageDialog(null, "Events must have a name");
                    else {
                        String date = txtYear.getText().toString() + "-";

                        if (Integer.parseInt(cmbMonth.getSelectedItem().toString()) < 10)
                            date += "0";
                        date += cmbMonth.getSelectedItem().toString() + "-";

                        if (Integer.parseInt(cmbDay.getSelectedItem().toString()) < 10)
                            date += "0";
                        date += cmbDay.getSelectedItem().toString() + " ";

                        if (Integer.parseInt(cmbHour.getSelectedItem().toString()) < 10)
                            date += "0";
                        date += cmbHour.getSelectedItem().toString() + ":";

                        if (Integer.parseInt(cmbMinute.getSelectedItem().toString()) < 10)
                            date += "0";
                        date += cmbMinute.getSelectedItem().toString() + ":00";

                        DBManager.addEvent(date, txtName.getText().toString(), txtGame.getText().toString(),
                                txtLocation.getText().toString(), txtDescription.getText().toString() );

                        txtName.setText(null);
                        txtGame.setText(null);
                        txtLocation.setText(null);
                        txtDescription.setText(null);
                        txtYear.setText(null);
                        cmbDay.setSelectedIndex(0);
                        cmbMonth.setSelectedIndex(0);
                        cmbHour.setSelectedIndex(0);
                        cmbMinute.setSelectedIndex(0);
                        contentPane.revalidate();
                        contentPane.repaint();
                        JOptionPane.showMessageDialog(null, "Event successfully added");
                    }
                } else
                    JOptionPane.showMessageDialog(null, "Must enter a valid date");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        contentPane.add(btnSubmitEvent);
        this.reDraw();
    }
}