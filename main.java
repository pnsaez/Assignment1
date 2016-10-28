
public class main {
	public static void main(String[] args) {

		Lane[] myLane = new Lane[12];
		Light[] myLight = new Light[12];
		
		for (int i = 0 ; i < myLane.length; i++) {
			
			myLane[i] = new Lane(i);
			myLight[i] = new Light(i);
			myLane[i].setTrafficLight(myLight[i]);
			
			
		}
		for (int i = 0 ; i < 4 ; i++){
			myLane[i].setTypeOfLane(1);
			System.out.println("Lane " + i + " type " + myLane[i].getTypeOfLane() + " created, Light " + myLane[i].getTrafficLightId() + " assigned" );
		}
		for (int i = 4 ; i < 8 ; i++){
			myLane[i].setTypeOfLane(2);
			System.out.println("Lane " + i + " type " + myLane[i].getTypeOfLane() + " created, Light " + myLane[i].getTrafficLightId() + " assigned");
		}
		for (int i = 8 ; i < 12 ; i++){
			myLane[i].setTypeOfLane(3);
			System.out.println("Lane " + i + " type " + myLane[i].getTypeOfLane() + " created, Light " + myLane[i].getTrafficLightId() + " assigned" );
		}

	}
	}
