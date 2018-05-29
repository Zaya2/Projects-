/**********************************************************
 * Class: Ball                                            *
 *                                                        *
 * Author: Zubaidah Alqaisi                               *
 *                                                        *
 *  Private data member: start, stop, animationPanel,     *
 *                      speedSlider.                      *
 * Public data member:BouncingBallPanel(), ActionPerformed*
 *                                                        *
 * Purpose: This subclass of JPanel encapsulates the ball *
 *          animation and will handle the button events.  *
 *********************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BouncingBallPanel extends JPanel implements ActionListener{

    // private data members
    private JButton start;
    private JButton stop;
    private AnimationPanel animationPanel;
    private JSlider speedSlider;

    // class constructor
    public BouncingBallPanel()
    {
        // creating two buttons for the stop and start
        start = new JButton("Start");
        stop = new JButton("Stop");

        // create the speed slider to slow and fasten the balls' speed
        speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 75);

        // change the value of the slider by 5 every time moving the slider it adds 5
        speedSlider.setMajorTickSpacing(5);

        // create instance of the AnimationPanel class
        animationPanel = new AnimationPanel();

        // set the frame layout
        setLayout(new BorderLayout());

        // creating the panel for the frame
        JPanel buttonPanels = new JPanel();

        // adding the buttons and the speed slider to the upper panel
        buttonPanels.add(start);
        buttonPanels.add(stop);
        buttonPanels.add(speedSlider);

        // add the panel to the frame
        add(buttonPanels, BorderLayout.PAGE_START);
        add(animationPanel, BorderLayout.CENTER);

        // listener for the buttons
        start.addActionListener(this);
        stop.addActionListener(this);

        speedSlider.addChangeListener(g -> {
            //copy a slider into a local variable
            JSlider slider = (JSlider) g.getSource();

            // get the current value of the slider
            animationPanel.setDelay(100 - slider.getValue());
        } );

    }// end of the constructor ()

    /***********************************************************
     * Function: actionPerformed()                             *
     * Purpose: this is the action listener for the two buttons*
     *          the start and the stop. When clicking on these *
     *          buttons, the balls start the movement or stop  *
     *          based on user clicking on a button.            *
     * @param e                                                *
     * Return: none void                                       *
     */

    public void actionPerformed(ActionEvent e)
    {
        // if the button clicked is start
        if (e.getActionCommand() == "Start")
        {
            // disable the start button and not letting the user click it again while the thread is running
            start.setEnabled(false);
            // enable the stop button to stop the balls after clicking on the start button
            stop.setEnabled(true);
            // start the thread
            animationPanel.start();
        }
        // if the button clicked is stop
        if ( e.getActionCommand() == "Stop")
        {
            // enable the start button
            start.setEnabled(true);
            // disable the stop button so it cannot be clicked twice at the same time
            stop.setEnabled(false);
            // stop the thread
            animationPanel.stop();
        }

    }// end of actionPerformed()

} // end of BouncingBallPanel class
