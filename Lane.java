
public class Lane {
	private int carsWaiting; 
	public int getCarsWaiting() {
		return carsWaiting;
	}

	public void setCarsWaiting(int carsWaiting) {
		this.carsWaiting = carsWaiting;
	}

	public Light getTrafficLight() {
		return trafficLight;
	}

	public int getTrafficLightId() {
		return trafficLight.getId();
	}
	
	public void setTrafficLight(Light trafficLight) {
		this.trafficLight = trafficLight;
	}

	public int getCarsDeparted() {
		return carsDeparted;
	}

	public void setCarsDeparted(int carsDeparted) {
		this.carsDeparted = carsDeparted;
	}

	public double getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(double waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getTypeOfLane() {
		return typeOfLane;
	}

	public void setTypeOfLane(int typeOfLane) {
		this.typeOfLane = typeOfLane;
	}
			int id;
	private Light trafficLight;
	private int carsDeparted;
	private double waitingTime;
	private int typeOfLane; // 1 is for left, 2 is for center, 3 is for right lane 
	

	
	Lane(int id) {
		this.id = id;
	}
	
	Lane(int newCarsWaiting, Light newTrafficLight, int newCarsDeparted, double newWaitingTime) {
		carsWaiting = newCarsWaiting;
		trafficLight = newTrafficLight;
		carsDeparted = newCarsDeparted;
		waitingTime = newWaitingTime;
	}
}