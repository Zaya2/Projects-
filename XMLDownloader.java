/****************************************************************
 *  Programmer: Zubaidah Alqaisi                                *
 *                                                              *
 *  Course: CSCI 470  Spring 2018      Assignment 4             *
 *                                                              *
 *  Program Function: Java application to download XML,parse it,*
 *                    and display the results. The XML will     *
 *                    contain information about albums available*
 *                    on the Apple iTunes Store. Add animation  *
 *                    that runs in a background thread.         *
 * Class: XMLDownloader                                         *
 *                                                              *
 * Private members: XMLDownloader(), CreateAndShowGUI(),        *
 *                  createMenu().                               *
 * Public Members: main(), actionPerformed                      *
 *                                                              *
 * Purpose: This class is the main class that extends JFrame and*
 *          it contains the menu bar and handel handel events   *
 *          from the menu bar's menu items.                     *
 *                                                              *
 ***************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class XMLDownloader extends JFrame implements ActionListener {

    // creating instance of the XMLDownloadPanel class
    XMLDownloadPanel downloadPanel = new XMLDownloadPanel();

    // create instance of the BouncingBallPanel class
    BouncingBallPanel bouncingBall = new BouncingBallPanel();

    // main method just to invoke the GUI and run it on a seperate thread
    public static void main(String [] arg)
    {
        // run the gui in a seperate thread
        EventQueue.invokeLater(() -> {
            XMLDownloader downloader = new XMLDownloader(); } );

    }

    // Constructor to set up the frame title
    private XMLDownloader()
    {
        super("iTunes Store Album");
        createAndShowGUI();
    }

    /******************************************************************
     * Function: createAndShowGUI()                                   *
     * Purpose: Sets up the layout for the application as a whole and *
     *          calls method like createMenu() to create the menu bar *
     * Arguments: none                                                *
     * Return: none void                                              *
     *
      */

    private void createAndShowGUI()
    {
        // close the application with an x button at the top
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // creating layout type border layout
        setLayout(new BorderLayout());
        // adding panel to the center of the layout
        add(downloadPanel,BorderLayout.CENTER);

        //add the panel of BouncingBallPanel class
        add(bouncingBall, BorderLayout.LINE_END);

        // call the createMenu() method
        createMenu();

        pack();

        // set the location of the frame
        setLocationRelativeTo(null);

        // set the JFrame to visible
        setVisible(true);

    } // end createAndShowGUI

    /********************************************************
     * Function: createMenu()                               *
     * Purpose: Encapsulates code to create the menu bar,   *
     *          menus, and menu items, and to add listeners *
     *          for the menu items.                         *
     * Arguments: none                                      *
     * Return: none void                                    *
     *
     */

    private void createMenu()
    {
        // create instance of the JMenuBar class
        JMenuBar menuBar = new JMenuBar();

        // create instances of the JMenu class called type,limit, and explicit
        JMenu type = new JMenu("Type");
        JMenu limit = new JMenu("Limit");
        JMenu explicit = new JMenu("Explicit");

        // the the menu items to the menu bar
        menuBar.add(type);
        menuBar.add(limit);
        menuBar.add(explicit);

        // create a button group
        ButtonGroup buttonGroupType = new ButtonGroup();
        ButtonGroup buttonGroupLimit = new ButtonGroup();
        ButtonGroup buttonGroupExplicit = new ButtonGroup();

        // create radio buttons for the type menu item
        JRadioButtonMenuItem rbMenuItemType1 = new JRadioButtonMenuItem("New Music");
        JRadioButtonMenuItem rbMenuItemType2 = new JRadioButtonMenuItem("Recent Releases");
        JRadioButtonMenuItem rbMenuItemType3 = new JRadioButtonMenuItem("Top Albums");

        // create radio buttons for the limit menu item
        JRadioButtonMenuItem rbMenuItemLimit1 = new JRadioButtonMenuItem("10");
        JRadioButtonMenuItem rbMenuItemLimit2 = new JRadioButtonMenuItem("25");
        JRadioButtonMenuItem rbMenuItemLimit3 = new JRadioButtonMenuItem("50");
        JRadioButtonMenuItem rbMenuItemLimit4 = new JRadioButtonMenuItem("100");

        // create radio buttons for the explicit menu item
        JRadioButtonMenuItem rbMenuItemExiplicit1 = new JRadioButtonMenuItem("Yes");
        JRadioButtonMenuItem rbMenuItemExiplicit2 = new JRadioButtonMenuItem("No");

        // adding the menu items to the menu group
        buttonGroupType.add(rbMenuItemType1);
        buttonGroupType.add(rbMenuItemType2);
        buttonGroupType.add(rbMenuItemType3);

        buttonGroupLimit.add(rbMenuItemLimit1);
        buttonGroupLimit.add(rbMenuItemLimit2);
        buttonGroupLimit.add(rbMenuItemLimit3);
        buttonGroupLimit.add(rbMenuItemLimit4);

        buttonGroupExplicit.add(rbMenuItemExiplicit1);
        buttonGroupExplicit.add(rbMenuItemExiplicit2);

        //add the group to the menu item
        type.add(rbMenuItemType1);
        type.add(rbMenuItemType2);
        type.add(rbMenuItemType3);

        limit.add(rbMenuItemLimit1);
        limit.add(rbMenuItemLimit2);
        limit.add(rbMenuItemLimit3);
        limit.add(rbMenuItemLimit4);

        explicit.add(rbMenuItemExiplicit1);
        explicit.add(rbMenuItemExiplicit2);

        // add the menu bar to JMenuBar
        setJMenuBar(menuBar);

        // set the default value for each item in the menu
        rbMenuItemType1.setSelected(true);

        rbMenuItemLimit1.setSelected(true);
        rbMenuItemExiplicit1.setSelected(true);

        // action listeners for type, limit, and explicit
        rbMenuItemType1.addActionListener(this);
        rbMenuItemType2.addActionListener(this);

        rbMenuItemType3.addActionListener(this);

        rbMenuItemLimit1.addActionListener(this);
        rbMenuItemLimit2.addActionListener(this);
        rbMenuItemLimit3.addActionListener(this);

        rbMenuItemLimit4.addActionListener(this);

        rbMenuItemExiplicit1.addActionListener(this);
        rbMenuItemExiplicit2.addActionListener(this);

        // setting the accelerator for the type buttons
        rbMenuItemType1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        rbMenuItemType2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        rbMenuItemType3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        // setting the tool tip for the type menu
        type.setToolTipText("Type of the album");

        // setting the tool tip for the Limit menu
        limit.setToolTipText("Number of the albums to display");

        // setting the tool tip for the explicit menu
        explicit.setToolTipText("Explicit or Not");


    }

    /*******************************************************
     * Function: actionPerformed()                         *
     * Purpose: this method handles action events from the *
     *          menu items. If an option is selected from  *
     *          any of the menu bar items, it will set the *
     *       type of the downloadPanel to the corresponding*
     *       selected type.                                *
     * Arguments: ActionEvent event                        *
     * Return: none void                                   *
     */

    public void actionPerformed(ActionEvent event)
    {
        // if the Recent Release type is selected of the type item in the menue bar
        if (event.getActionCommand() == "Recent Releases")
        {
            // set the panel type to recent-release which corresponde to the type selected by the user
            downloadPanel.setType("recent-releases");
        }
        if ( event.getActionCommand() == "New Music")
        {
            downloadPanel.setType("new-music");
        }
        if (event.getActionCommand() == "Top Albums")
        {
            downloadPanel.setType("top-albums");
        }
        if (event.getActionCommand() == "10")
        {
            downloadPanel.setLimit("10");
        }
        if (event.getActionCommand() == "25")
        {
            downloadPanel.setLimit("25");
        }
        if (event.getActionCommand() == "50")
        {
            downloadPanel.setLimit("50");
        }
        if (event.getActionCommand() == "100")
        {
            downloadPanel.setLimit("100");
        }
        if (event.getActionCommand() == "Yes")
        {
            downloadPanel.setExplicit(true);
        }
        if (event.getActionCommand() == "No")
        {
            downloadPanel.setExplicit(false);
        }
    } // end of the actionPerformed() method


}// end of the XMLDownloader class
