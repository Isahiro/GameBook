package com.GameBook;

import org.json.simple.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Joshua Dalton on 11/9/2015.
 */
public class UIManager extends JFrame
{
    private int currentUser;
    private DataBaseManager DBManager;

    private Container contentPane;
    private JMenuBar menuBar;
    private JTextField txtUsername;
    private JPasswordField pasPassword;
    private GridLayout uiLayout;

    final Font TITLEFONT = new Font("Arial Black", Font.PLAIN, 20);

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
        uiLayout = new GridLayout(15, 5, 1, 1);
        contentPane.setLayout(uiLayout);

        // Initialize the Accounts and Events buttons
        btnAccounts = new JButton("Accounts");
        btnEvents = new JButton("Events");

        // Set MouseListeners for Accounts and Events
        btnAccounts.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                accountsForm();
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

        btnManage.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
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

        btnLogout.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
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

    public void accountsForm()
    {
        System.out.println("Accounts button clicked");
    }

    public void eventsForm()
    {
        System.out.println("Events button clicked");
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
        JComboBox cmbDay = new JComboBox();
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
                newUser.put("day", cmbMonth.getSelectedItem() );
                newUser.put("year", txtYear);

                if (validateRegistration(newUser) )
                    DBManager.addAccount(newUser);
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

        if(DBManager.checkUsername(newUser.get("username").toString() ) )
        {
            contentPane.getComponent(3).setForeground(Color.red);
            errorMessage += "A user by that username already exists\n";
            isValid = false;
        }
        else if(newUser.get("username").toString().isEmpty() ||
                newUser.get("username").toString().length() < 4)
        {
            contentPane.getComponent(3).setForeground(Color.red);
            errorMessage += "Usernames must be at least 4 characters long\n";
            isValid = false;
        }

        if(newUser.get("password").toString().length() < 8 )
        {
            contentPane.getComponent(5).setForeground(Color.red);
            errorMessage += "Passwords must be at least 8 characters long\n";
            isValid = false;
        }

        String numRegex = ".*[0-9].*";
        String upperAlphaRegex = ".*[A-Z].*";
        String lowerAlphaRegex = ".*[a-z].*";

        if(!(newUser.get("password").toString().matches(numRegex) &&
           newUser.get("password").toString().matches(upperAlphaRegex) &&
           newUser.get("password").toString().matches(lowerAlphaRegex) ) )
        {
            contentPane.getComponent(5).setForeground(Color.red);
            errorMessage += "Passwords must contain letters, uppercase letters, and lowercase numbers\n";
            isValid = false;
        }

        if(!newUser.get("password").toString().equals(newUser.get("confPassword").toString()) )
        {
            contentPane.getComponent(7).setForeground(Color.red);
            errorMessage += "Confirmation password must match password\n";
            isValid = false;
        }

        String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9+]\\.)+[a-zA-Z]{2,}))$";
        //java.util.regex.Pattern emailPattern = java.util.regex.Pattern.compile(emailRegex);
        //java.util.regex.Matcher matcher = emailPattern.matcher(newUser.get("email").toString() );

        if(!newUser.get("email").toString().matches(emailRegex) )
        {
            contentPane.getComponent(9).setForeground(Color.red);
            errorMessage += "Must register a valid email address\n";
            isValid = false;
        }

        if(!isValid)
        {
            this.reDraw();
            JOptionPane.showMessageDialog(null, errorMessage);
        }

        return isValid;
    }

    public void manageForm()
    {
        System.out.println("Manage button clicked");
    }
}
