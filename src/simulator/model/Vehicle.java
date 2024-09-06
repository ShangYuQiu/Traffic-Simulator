package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private int max_Speed;
	private List<Junction> itinerary;
	private int currentSpeed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contamination;
	private int totalContamination;
	private int totalDistance;
	private int currentPos; //ind del vehiculo actual 

	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);

		// comprobar datos

		if (maxSpeed < 1 || contClass < 0 || contClass > 10 || itinerary.size() < 2) {
			throw new IllegalArgumentException("Entrada Invalida");
		}
		
		
		this.road =null;
		this.location = 0;
		this.currentSpeed = 0;
		this.max_Speed = maxSpeed;
		this.contamination = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		this.status = VehicleStatus.PENDING;
		totalDistance = 0;
		currentPos = 0;
	}

	@Override
	public void advance(int time) {
		
		
		if (status == VehicleStatus.TRAVELING) {

			int l = location;
			location = Math.min(location + currentSpeed, road.getLength());

			l = location - l; // distancia recorrida

			totalDistance += l;
			int c = l * contamination;

			totalContamination += c;

			road.addContamination(c);
			
			if (location == road.getLength()) { // si ha llegado al final de la carretera

				road.getDest().enter(this);
				status = VehicleStatus.WAITING;
				currentSpeed = 0;
				currentPos++;
			}
		}
		
		else {
			currentSpeed =0 ;
			System.out.println("Vehiculo parado");
		}
		

	}

	@Override
	public JSONObject report() {

		JSONObject object = new JSONObject();

		object.put("distance", totalDistance);
		if (status != VehicleStatus.PENDING && status != VehicleStatus.ARRIVED) {
			object.put("road", road.getId());
		}

		object.put("co2", totalContamination);

		if (status != VehicleStatus.PENDING && status != VehicleStatus.ARRIVED) {
			object.put("location", location);
		}

		object.put("id", _id);
		object.put("class", contamination);
		object.put("speed", currentSpeed);
		object.put("status", status.toString());

		return object;
	}

	public void setSpeed(int s) {
		
		if ( status == VehicleStatus.TRAVELING) {
			if (s < 0) {
				throw new IllegalArgumentException(" Entrada invalida");
			}
		
			else {
				this.currentSpeed = Math.min(s, max_Speed);
			}
		}
	}

	public void setContClass(int c) {

		if (c >= 0 && c <= 10) {
			contamination = c;
		}

		else {
			throw new IllegalArgumentException(" Entrada invalido");
		}

	}

	public void moveToNextRoad() {
		// ir al cruce destino de road

		if (status != VehicleStatus.PENDING && status != VehicleStatus.WAITING) {

			throw new IllegalArgumentException(" Estado invalida");
		}

		if (road != null) {
			road.exit(this);
		}

		if (currentPos == itinerary.size() - 1) { // si ha llegado al final
			road = null;
			status = VehicleStatus.ARRIVED;
			location = 0;

		}

		else {
			location = 0;
			Road nextRoad = itinerary.get(currentPos).roadTo(itinerary.get(currentPos+1));
			nextRoad.enter(this);
			road = nextRoad;
			status = VehicleStatus.TRAVELING;

		}
	}

	public int getLocation() {
		return location;
	}

	public int getSpeed() {
		return currentSpeed;
	}

	public int getMaxSpeed() {
		return max_Speed;
	}

	public int getContClass() {
		return contamination;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public int getTotalCO2() {
		return totalContamination;
	}

	public List<Junction> getItinerary() {
		return itinerary;
	}

	public Road getRoad() {
		return road;
	}

}
