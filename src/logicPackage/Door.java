package logicPackage;

public class Door {
	
	// For status, 0 is locked and 1 is unlocked
	
	public int status;
	public String name;
	public String key;
	
	public Door(int status, String name, String key) {
		
		this.status = status;
		this.name = name;
		this.key = key;
		
	}
	
	public void unlockDoor() {
		status = 1;
	}
	
	public void lockDoor() {
		status = 0;
	}
	
	public void userUnlockAttempt(String currentKey) {
		if(currentKey == key) {
			unlockDoor();
			//TO DO: print "door unlocked" in GUI terminal
		}
		else {
			//TO DO: print "this door seems to be locked, I'll need a key to get past" in GUI terminal
		}
	}
	

}
