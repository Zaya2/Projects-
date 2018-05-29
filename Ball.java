/**********************************************************
 * Class: Ball                                            *
 *                                                        *
 * Author: Zubaidah Alqaisi                               *
 *                                                        *
 * Private data member:  color, radius, x, y, dx, dy,     *
 *                       bounce.                          *
 * Public data member: Ball(), move(), draw()             *
 *                                                        *
 * Purpose: This class represents a single bouncing ball  *
 *          in the animation.                             *
 * ********************************************************/

import javax.sound.sampled.Clip;
import java.awt.*;

public class Ball {

    // class data members
    private Color color;
    private int radius;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private Clip bounce;

    // class constructor:  allowing you to initialize the ball.
    public Ball(Color inColor, int inRadius, int inX, int inY, int inDx, int inDy, Clip inBounce)
    {
        color = inColor;
        radius = inRadius;
        x = inX;
        y = inY;
        dx = inDx;
        dy = inDy;
        bounce = inBounce;

    } // end of Ball()

    /*********************************************************
     * Function: move()                                      *
     * Purpose: Determines the directions of the balls based *
     *         on some calculations.                         *
     * @param d                                              *
     * Return: none void                                     *
     *
     */

    public void move(Dimension d)
    {
       // If the x coordinate of the ball is less than or equal its radius OR greater than or equal the width
       // of the panel minus the radius of the ball
        if (x <= radius || x >= (d.width - radius) )
        {
            //reverse the horizontal direction of the ball
            dx *= -1;
            bounce.loop(1);   // start the clip sound and plays it once

        }
        //If the y coordinate of the ball is less than or equal its radius OR greater than or equal the
        //height of the panel minus the radius of the ball
        if (y <= radius || y >= (d.height - radius ))  // hieght of the panel
        {
            // reverse the vertical direction of the ball
            dy *= -1;
            bounce.loop(1);
        }
        // add dx to x
        x += dx;
        // add dy to y
        y += dy;

    } // end of move()

    /***************************************************
     * Function: draw()                                *
     * Purpose:This function is responsible for drawing*
     *       Use the Graphics object to set the drawing*
     *       color to the Color of the ball, and then  *
     *      use it to call fillOval() to draw the ball.*
     * @param g                                        *
     * Return: none void                               *
     *
     */

    public void draw(Graphics g)
    {
        // set the color of the balls
        g.setColor(color);
        // calling fillOval() to draw the ball
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);

    } // end of draw()

} // end of Ball class
