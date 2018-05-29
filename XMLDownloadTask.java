/**********************************************************
 * Class: XMLDownloadTask                                 *
 *                                                        *
 * Author: Zubaidah Alqaisi                               *
 *                                                        *
 * Private members: StringUrl, downloadPanel.             *
 * Protected members: process()                           *
 *                                                        *
 * Public members: XMLDownloadTask(), doInBackgorund(),   *
 *                                                        *
 * Purpose:This is a subclass of SwingWorker that is used *
 *       to download the XML data in a background thread. *
 *       This class may add the published Album objects to*
 *       the text area in the parent class one at a time, *
 *       and/or add them to an ArrayList and return the   *
 *       list when the task is complete so that they can  *
 *       be displayed all at once.                        *
 *********************************************************/

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class XMLDownloadTask extends SwingWorker<List<Album>, Album> {

    // creating instance variables
    private String StringUrl;
    private XMLDownloadPanel downloadPanel;

    /*****************************************************
     * Function: XMLDownloadTask ()                      *
     * Purpose: This is the constructor of the class     *
     * @param url                                        *
     * @param downloadPanel                              *
     * Return: none
     */

    public XMLDownloadTask(String url, XMLDownloadPanel downloadPanel)
    {
        // setting the default values of url and downloadPanel
        StringUrl = url;
        this.downloadPanel = downloadPanel;

    } // end of XMLDownloadTask()

    /*******************************************************
     * Function: doInBackground()                          *
     * Porpuse: Downloads the XML data as a string, creates*
     *          a SAX parser, creates an instance of the   *
     *          AlbumHandler class to handle SAX parse     *
     *          events, and parses the XML. It will then   *
     *          publish the new album.                     *
     * Arguments: none                                     *
     * @return: list of albums                             *
     */

    public List<Album> doInBackground()
    {
        // creating instance of the string builder class
        StringBuilder page = new StringBuilder();

        // create the actual connection to a website
        HttpURLConnection httpUrl = null;

        try {
            // create instance of the URL class and pass the string url to it
            URL url = new URL(StringUrl);
            //create a connection
            httpUrl = (HttpURLConnection) url.openConnection();

            httpUrl.setRequestMethod("GET"); // set the connection type

            // if it is connected or if connection is available
            if ( httpUrl.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                // connect the connection to buffer and starting pulling data into the buffer
                BufferedReader buffer = new BufferedReader(new InputStreamReader(httpUrl.getInputStream()));

                // read individual line from buffer and store into line
                String line = buffer.readLine();  // read first line prime read

                //while loop to read the XML
                while (line != null )
                {
                    // taking the line read and add it to the page
                    page.append(line);
                    // getting next line
                    line = buffer.readLine(); // second read

                } // end of while

                // converting the string builder to string
                String xmlString = page.toString();

                // creating the parser
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();

                //parse the page. Parse take two requirments: input source and  handler to handel tasks
                parser.parse(new InputSource(new ByteArrayInputStream(xmlString.getBytes("utf-8"))), new AlbumHandler());

            }// end of if

        } // end of try
        // catch blocks to catch many exceptions
        catch (MalformedURLException e)
        {
            System.out.println("Bad Url.");
        }
        catch (ProtocolException e)
        {
            System.out.println(e.getMessage());
        }
        catch ( IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (ParserConfigurationException e)
        {
            System.out.println(e.getMessage());
        }
        catch ( SAXException e)
        {
            System.out.println(e.getMessage());
        }

        return null;

    } // end of doInBackground()

    /*****************************************************
     * Function: process ()                              *
     * Purpose: Processes a List of Album objects that   *
     *         have been published by the AlbumHandler   *
     *         class.                                    *
     * @param chunks                                     *
     * Return: none void                                 *
     */

    @Override
    protected void process(List<Album> chunks) {

        // foreach loop to loop through the list of albums
        for (Album album: chunks)
        {
            // calling a public function to display the data in the text area
            downloadPanel.displayData(album);
        }

    } // end of process()

    /**********************************************************
     * Class: AlbumHandler                                    *
     *                                                        *
     * Author: Zubaidah Alqaisi                               *
     *                                                        *
     * Private members: title, artist, category, sTitle,      *
     *                  sArtist, sCategory.                   *
     * Protected members: process()                           *
     *                                                        *
     * Public members: startElement(), endElement(),          *
     *                 characters().                          *
     *                                                        *
     * Purpose: This is a subclass of DefaultHandler. It  is  *
     *          an inner class of the XMLDownloadTask, since  *
     *          that will give its methods direct access to   *
     *        the methods and data members of the outer class.*
     *        It handles information from the parser.         *
     *********************************************************/

    public class AlbumHandler extends DefaultHandler {

        // craeting boolean data members
        private boolean title = false;
        private boolean artist = false;
        private boolean category = false;

        // data members of the AlbumHandler class
        private String sTitle;
        private String sArtist;
        private String sCategory;

        /*******************************************************
         * Function: startElement()                            *
         * Purpose: handle various events that occur as the XML*
         *         is parsed like open tags.                   *
         * @param uri                                          *
         * @param localName                                    *
         * @param qName                                        *
         * @param attributes                                   *
         * @throws SAXException                                *
         * Return: none void                                   *
         */

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            //if the open tag is im:name
            if (qName.equalsIgnoreCase("im:name")) {

                title = true;     // set the title boolean equal to true means found it and between the two tags
                sTitle = "";      // set the title string equal empty string to start a new title
            }
            // if the open tag is im:artist
            if (qName.equalsIgnoreCase("im:artist")) {

                artist = true; // set the boolean data member to true
                sArtist = "";     // set the string to empty to start a new artist
            }
            // if the open tag is entry
            if (qName.equalsIgnoreCase("entry") )
            {
                category = true;    // it is found so set the boolean to true
            }
            // if the open tag is category
            if (qName.equalsIgnoreCase("category") && category)
            {
                sCategory = attributes.getValue("label");   // get the label tag in the category tag only
                category = false;     // found so set it equal to false because there is no ending tag for category
            }
        }// end of startElement()

        /*******************************************************
         * Function: endElement()                              *
         * Purpose: handle various events that occur as the XML*
         *          is parsed like closing tags.               *
         * @param uri                                          *
         * @param localName                                    *
         * @param qName                                        *
         * @throws SAXException                                *
         * Return: none void                                   *
         */

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

            // if the ending tag is im:name
            if (qName.equalsIgnoreCase("im:name")) {

                title = false;   // set the boolean to false
            }
            // if the ending tag is im:artist
            if (qName.equalsIgnoreCase("im:artist")) {

                artist = false;          // set the boolean to false
            }
            // if the ending tag is entry
            if (qName.equalsIgnoreCase("entry") )
            {
                Album album = new Album(sTitle, sArtist, sCategory); // create instance of the Album class and pass the string data member to it
                publish(album);    // publish the album
            }

        } // end of endElement ()


        /******************************************************
         * Function: characters()                             *
         * Purpose: To read the characters from the parser.   *
         * @param ch                                          *
         * @param start                                       *
         * @param length                                      *
         * @throws SAXException                               *
         * Return: none void                                  *
         */

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {

            // if it is in between the tags, take the current characters found and add them to the string
            if (title)
                sTitle = sTitle + new String(ch, start, length);

            // if it is in between the tags, take the current characters found and add them to the string
            if (artist)
                sArtist = sArtist + new String(ch, start, length);
        }
    } // end of character()


}// end of the class AlbumHandler
