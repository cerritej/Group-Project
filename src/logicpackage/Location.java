package logicpackage;

/***********************************************************************
 * The Location class helps with recording where the player is
 * located while playing the game.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson)
 * @version (11 / 29 / 19)
 ***********************************************************************/
public class Location {
    /** The name of the current location. */
    private String location;

    /******************************************************************
     * Returns the current location of the player.
     * @return String
     ******************************************************************/
    public String getLocation() {
        return location;
    }

    /******************************************************************
     * Passes the currentArea parameter to the current location.
     * @param currentArea The current location of the player.
     ******************************************************************/
    public void setLocation(final String currentArea) {
        this.location = currentArea;
    }
}
