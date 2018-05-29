/**********************************************************
 * Class: AnimationPanel                                  *
 *                                                        *
 * Author: Zubaidah Alqaisi                               *
 *                                                        *
 * Private members: ArrayList<Ball> balls, Dimension      *
 *                 dimension , Thread thread, Clip        *
 *                 bounceSound, Clip backgroundMusic.     *
 *                 int delay.                             *
 * Public members: AnimationPanel, start(), stop(), run() *
 * Protected members: void paintComponent(Graphics g).    *
 * Purpose:This subclass of JPanel will be used to display*
 *         the animation in a separate background thread. *
 *   Therefore, it should implement the Runnable interface*
 *********************************************************/

import javax.imageio.IIOException;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AnimationPanel extends JPanel implements  Runnable{

    // data member of the class
    private ArrayList<Ball> balls;
    private Dimension dimension;
    private Thread thread;
    private Clip bounceSound;
    private Clip backgroundMusic;
    private int delay;

    /*********************************************************
     * Function: AnimationPanel()                            *
     * Purpose: This is the constructor of the AnimationPanel*
     *          class. The constructor handles the panel size*
     *         as well as displaying the background sound and*
     *         the bouncing sound of the ball.               *
     * Argument: none                                        *
     * Return: none                                          *
     */

    public AnimationPanel ()
    {
        balls = new ArrayList<Ball>();  // create instance of the arrayList
        dimension = null;
        thread = null;
        delay = 25; // starting value of the delay

        // setting the panel size
        setPreferredSize(new Dimension(300, 600));

        // getting a list of available list of systems that make sounds
        Mixer.Info[] mixeInfo = AudioSystem.getMixerInfo();

        // use the first mixer in the list
        Mixer mixer = AudioSystem.getMixer(mixeInfo[0]);

        //creates a data info. sending a stream of data for the file
        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);

        try {
            // setting up memory for the sound
            bounceSound = (Clip) mixer.getLine(dataInfo);

            // setting up memory for the background sound
            backgroundMusic = (Clip) mixer.getLine(dataInfo);
        }
        catch ( LineUnavailableException e)
        {
            e.printStackTrace();
        }
        try {
            //assign the sound to a variable and open it
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream( new File("bounce.wav") );
            bounceSound.open(audioInputStream);

            //assign the sound to a variable and open it
            AudioInputStream backgroundStream = AudioSystem.getAudioInputStream( new File("backgroundMusic.wav") );
            backgroundMusic.open(backgroundStream);


        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }

    }// end of AnimationPanel()

    /******************************************************
     * Function: start()                                  *
     * Purpose: this function test if the Thread reference*
     *       is null, create a new Thread object from this*
     *      class and call the Thread’s start() method to *
     *      make it runnable by the thread scheduler.     *
     * Argument: none                                     *
     * Return: none                                       *
     *
     */

    public void start()
    {
        // if there is no thread running
        if (thread == null)
        {
            // create a new thread object if the current thread is null
            thread = new Thread(this);
            // play the background music
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            // start the new thread
            thread.start();
        }
    } // end of start()


    /*******************************************************
     * Function: stop()                                    *
     * Purpose: This function Call interrupt() for the     *
     *          Thread and then set its reference to null. *
      *        This will cause the loop in the run() method*
     *         to exit. Once the loop is finished, the     *
     *         method is finished as well, so the thread   *
     *         will terminate.                             *
     * Argument: none                                      *
     * Return: none  void                                  *
     *
     */

    public void stop()
    {
        // interrupt the current thread
        thread.interrupt();
        // stop the background music
        backgroundMusic.stop();
        thread = null;

    }  // end of stop()

    /*******************************************************
     * Function: paintComponent(Graphics g)                *
     * Purpose: This method should be overridden to call   *
     *          the superclass version of the method,      *
     *          creates a set of Ball objects and add them *
     *         to the ArrayList, then get the dimensions of*
     *         the panel by calling getSize(), Draw the    *
     *         white background, Call the move() and draw()*
     *       methods for each Ball object in the ArrayList.*
     *                                                     *
     * @param g                                            *
     * Return: none void                                   *
     *
     */

    @Override
    protected void paintComponent(Graphics g) {
        //Call the superclass version of the method
        super.paintComponent(g);

        //If the Dimension object reference is null
        if (dimension == null)
        {
         //create a set of Ball objects and add them to the ArrayList, then get the dimensions of the panel by calling getSize().
            balls.add(new Ball(Color.BLUE, 20, 250, 250, 7, 6, bounceSound));
            balls.add(new Ball(Color.YELLOW, 20, 150, 150, -4, -5, bounceSound));
            balls.add(new Ball(Color.RED, 20, 100, 100, 3, 8, bounceSound));

            // get the size of the panel and return the width of the panel area and the height
            dimension = getSize();
        }
        // set the color of the background panel to white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, dimension.width, dimension.height);  // drawing the background panel

        // set the frame for the panel to black
        g.setColor((Color.BLACK));
        g.drawRect(0, 0, dimension.width, dimension.height );

        // draw each ball according to the dimensions and colors provided
        for(Ball ball : balls)
        {
            ball.move(dimension);
            ball.draw(g);
        }
    }// end paintComponent()

    /***********************************************************
     * Function: run()                                         *
     * Purpose: This is effectively the “main” method that will*
     *          run in the separate background thread. It will *
     *          have a loop that continues while the current   *
     *          thread is equal to the Thread reference data   *
     *          member.                                        *
     * Argument: none                                          *
     * Return: none void                                       *
     *
     */

    public void run()
    {
        // if the current thread is equal to the thread data member
        while (Thread.currentThread() == thread)
        {
            try {
                // put the thread to sleep
                Thread.sleep(delay);

                // call the paint component to do its work
                repaint();
            }
            catch (InterruptedException e)
            {
                return;
            }
        }
    } // end of run()

    // setter for the delay data member to set the delay equal to specific value
    public void setDelay(int delay) {
        this.delay = delay;
    }


} // end of the AnimationPanel class
