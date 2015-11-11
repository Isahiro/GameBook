package com.GameBook;

import javax.swing.*;

/**
 * Created by Joshua Dalton, Melissa Stricker, and Jake Hatfield on 11/4/2015.
 */
public class App
{
    public static void main(String[] args)
    {
        // Create the UI elements
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                UIManager UI = new UIManager();
                UI.pack();
                UI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                UI.setVisible(true);
            }
        });

    }
}
