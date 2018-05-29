/**********************************************************
 * Class: Album                                           *
 *                                                        *
 * Author: Zubaidah Alqaisi                               *
 *                                                        *
 * Private members: albumName, singerName, musicType      *
 *                                                        *
 * Public members: getAlbumName(), getSingerName(),       *
 *                  getMusicType(), Album() constructor   *
 *                                                        *
 * Purpose: A class to hold all of the information about  *
 *          an album.                                     *
 *********************************************************/

public class Album {

    // creating instance variables for the Album class
    private String albumName;
    private String singerName;
    private String musicType;

    /******************************************************
     * Function: Album()                                  *
     * Purpose: this is the constructor of the Album class*
     * @param title                                       *
     * @param artist                                      *
     * @param category                                    *
     * Return: none                                       *
     */

    public Album (String title, String artist, String category)
    {
        albumName = title;
        singerName = artist;
        musicType = category;

    } // end of Album()

    // getters for the Album class data members
    public String getAlbumName() {
        return albumName;
    }

    public String getSingerName() {
        return singerName;
    }

    public String getMusicType() {
        return musicType;
    }

}// end of the class
