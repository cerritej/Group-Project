package logicPackage;

/***********************************************************************
 * The Location class helps with recording where the player is
 * located while playing the game.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson, Austin Jarema)
 * @version (11 / 29 / 19)
 ***********************************************************************/
public class Location {
    /** The name of the current location. */
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String currentArea) {
        this.location = currentArea;
    }
}
