package logicPackage;

/***********************************************************************
 * The door class is used to give a door certain attributes
 * and functionality.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson, Austin Jarema)
 * @version (11 / 29 / 19)
 ***********************************************************************/
public class Openable {
    // For status, 0 is locked and 1 is unlocked.
    public int status;
    public String name;
    public String key;

    public Openable(int status, String name, String key) {
        this.status = status;
        this.name = name;
        this.key = key;
    }

    private void openActionDenied() {
        status = 0;
    }

    private void openActionAccepted() {
        status = 1;
    }

    public void userUnlockAttempt(String currentKey) {
        if (currentKey.equals(key)) {
            openActionDenied();
        } else {
            openActionAccepted();
        }
    }
}
