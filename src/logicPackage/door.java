package logicPackage;

/***********************************************************************
 * The door class is used to give a door certain attributes
 * and functionality.
 *
 * @author (Jeremiah Cerriteno, Kyle Jacobson, Austin Jarema)
 * @version (11/29/19)
 ***********************************************************************/
public class door {

	// For status, 0 is locked and 1 is unlocked.
	public int status;
	public String name;
	public String key;
	String lockedMessage;
	String unlockedMessage;
	
	public door(int status, String name, String key) {
		this.status = status;
		this.name = name;
		this.key = key;
	}

	private void lockDoor() {
		status = 0;

		String lockedMessage = "The door is locked. I will need to look for something to gain access.";
	}

	private void unlockDoor() {
		status = 1;

		String unlockedMessage = "The door is now unlocked. I can now continue exploring the ship.";
	}
	
	public void userUnlockAttempt(String currentKey) {
		if(currentKey.equals(key)) {
			unlockDoor();
		}

		else {
			lockDoor();
		}
	}
}
