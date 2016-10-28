
public class Light {
	
	boolean red;
	boolean green;
	boolean left;
	boolean right;
	int id;

Light(int id) {
	red = true;
	green = false;
	left = false;
	right = false;
	this.id = id; 
}

Light(boolean newRed, boolean newGreen, boolean newLeft, boolean newRight) {
	red = newRed;
	green = newGreen;
	left = newLeft;
	right = newRight;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}





}