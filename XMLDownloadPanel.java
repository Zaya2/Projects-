/**********************************************************
 * Class: XMLDownloadPanel                                *
 *                                                        *
 * Author: Zubaidah Alqaisi                               *
 *                                                        *
 * Private members: timeLabel, albumInfo, albumData,      *
 *                  type, limit, second, timer, download()*
 *                  setTime().                            *
 * Public members: XMLDownloadPanel(), actionPerformed(), *
 *                 displayData().                         *
 * Purpose: This is a subclass of JPanel, and contain most*
 *          of the user interface for this assignment. It *
 *        will handle action events from the “Get Albums” *
 *         button                                         *
 *********************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import static javax.swing.SwingWorker.StateValue.DONE;
import static javax.swing.SwingWorker.StateValue.STARTED;

public class XMLDownloadPanel extends JPanel {

    // setting up a label for the timer
    private JLabel timeLabel = new JLabel("0:00");

    // setting up the get album button
    private JButton albumInfo = new JButton("Get Albums");

    // setting up the text area
    private JTextArea albumData = new JTextArea(30, 60);

    // private data members of the class
    private String type;
    private String limit;
    private Boolean explicit;

    private int second = 0;
    private Timer timer;                    // setting a storage for the timer

    // setters to set the value of type, limit, and explicit
    public void setType(String type) {
        this.type = type;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
    }

    /*******************************************************
     * Function: XMLDownloadPanel()                        *
     * Purpose: This is the constructor of the XMLDownload-*
     *          Panel class and it set up the frame by     *
     *          adding a panel, pane, actionListener to    *
     *          albumInfo button, and a default value for  *
     *         type, limit, and exciplicit for the menu bar*
     * Argument: none                                      *
     * Return: none void                                   *
     */

    public XMLDownloadPanel() {
        // set frame layout to borderLayout
        setLayout(new BorderLayout());
        // create the panel
        JPanel panel = new JPanel(new FlowLayout());
        // create a scroll pane to scroll through data
        JScrollPane pane = new JScrollPane(albumData);
        //add the button to the upper pane
        panel.add(albumInfo);
        // adding the timer label to the upper pane
        panel.add(timeLabel);

        add(pane, BorderLayout.CENTER);   // adding text area to the center of the main pannel
        add(panel, BorderLayout.PAGE_START);   // add the pannel to the top

        // default values for type, limit, and explicit
        type = "new-music";
        limit = "10";
        explicit = true;

        // actionListener for the get album button
        albumInfo.addActionListener(this::actionPerformed);

    } // end of XMLDownloadPanel()

    /******************************************************
     * Function: actionPerformed()                        *
     * Purpose: To handle action events from the button   *
     *          like clear up the text area and call the  *
     *          download() method.                        *
     * @param e                                           *
     * Return: none void                                  *
     */

    public void actionPerformed(ActionEvent e)
    {
        // clear the text area
        albumData.setText("");

        download();

    } // end of actionPerformed() method

    /**********************************************************
     * Function: download()                                   *
     * Purpose: To initiate the download of the XML data.     *
     * Argument: none                                         *
     * Return: none void                                      *
     */

    private void download()
    {
        //Build a URL string for the requested fetch
        String albumUrl = "https://rss.itunes.apple.com/api/v1/us/itunes-music/";
        albumUrl += type + "/all/" + limit + "/";

        // if explicit is Yes, add it to the url
        if (explicit) {
            albumUrl += "explicit.atom";
        }
        else
        {
            albumUrl += "non-explicit.atom";   // if the explicit is No, add non to the url
        }

        // creating a new XMLDownloadTask and pass it the URL string and a reference to thistask
        XMLDownloadTask task = new XMLDownloadTask(albumUrl,this);

        // disabling the button so it cannot be clicked multiple time while the program is processing
        task.addPropertyChangeListener(g -> {
            if (g.getPropertyName() == "state")
            {
                if ( (SwingWorker.StateValue) g.getNewValue() == STARTED)
                {
                    albumInfo.setEnabled(false);     // disable the get album button
                    second = 0;                   // reset the timer when enabling the button again
                    timeLabel.setText(setTime(second));    // update the timer to match the updating seconds

                    // setting up the timer and start it
                    timer = new Timer(1000, s -> {
                        second++; // incrument the seconds
                        timeLabel.setText(setTime(second));   // update the timer to match the updating seconds
                    });

                    timer.start();  // start up the timer
                }
                if ((SwingWorker.StateValue) g.getNewValue() == DONE)
                {
                    albumInfo.setEnabled(true);   // enable the button when processing is done
                    timer.stop();    // stoping the timer when disapling the button

                } // end of inner if

            } // end of outer if

        });
        task.execute();

    } // end of download()

    /*****************************************************
     * Function: setTime()                               *
     * Purpose: this method is for the timer to display  *
     *          the processing time until it is done.    *
     * Argument: none                                    *
     * Return: displayTime                               *
     */

    private String setTime(int second)
    {
        int minute;    // declaring a variable to store minutes
        int seconds;   // variable to store seconds
        String displayTime;    // string to hold the time to display

        minute = second/60;
        seconds = second - minute;

        // format the time
        displayTime = String.format("%d:%02d", minute, seconds);

        return displayTime;
    }

    /********************************************************
     * Function: displayData()                              *
     * Purpose: To display the information in the text area *
     * @param album                                         *
     * Return: none void                                    *
     */

    public void displayData (Album album)
    {
        // concatenate the information and store it in line
        String line = album.getAlbumName() + "; " + album.getSingerName() + "; " + album.getMusicType() + "\n";
        albumData.append(line);   // append each line in the text area

    } // end of displayData()

} // end of the class XMLDownloadPanel()
