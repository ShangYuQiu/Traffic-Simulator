package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{

	protected Junction source;
	protected Junction destination;
	protected int length;
	protected int _maxSpeed;
	protected int currentSpeedLimit;
	protected int contaminationAlarmLimit;
	protected Weather weather;
	protected int totalContamination;
	protected List <Vehicle> vehicles;
	
	
	Road(String id, Junction srcJunc, Junction destJunc, 
			int maxSpeed, int contLimit, int length, Weather weather) {
		
			super(id);
			// comprueba los datos
			if ( maxSpeed < 1 || length <= 0 || contLimit < 0 || srcJunc == null 
					|| destJunc == null || weather == null) {
				throw new IllegalArgumentException(" Entrada invalida");
			}
			
			else {
				currentSpeedLimit = maxSpeed;
			}
			this.vehicles = new ArrayList<Vehicle>();
			this.source = srcJunc;
			this.destination = destJunc;
			this._maxSpeed = maxSpeed;
			this.contaminationAlarmLimit = contLimit;
			this.length = length;
			this.weather = weather;
			this.totalContamination = 0;
			source.addOutgoingRoad(this);
			destination.addIncomingRoad(this);
		}

	@Override
	public void advance(int time) {
		
		this.reduceTotalContamination(); // reduce la contaminacion
		updateSpeedLimit(); // actualiza la veloc limit
		
		for (Vehicle  v : vehicles) {
			if (v.getStatus() == VehicleStatus.TRAVELING ) {
				v.setSpeed(calculateVehicleSpeed(v));
				v.advance(time);}
		}
		
		CompareVehiculo cmp = new CompareVehiculo();
		vehicles.sort(cmp);
		// ordenar la lista de vehiculo por su localizacion
		
	}

	@Override
	public JSONObject report() {
		
		JSONObject object = new JSONObject();
		
		object.put("id", _id);
		object.put("speedlimit", currentSpeedLimit);
		object.put("co2", totalContamination);

		object.put("weather", weather.toString());
		
		JSONArray array = new JSONArray();
		object.put("vehicles", array);
		
		for ( Vehicle vehicle : vehicles) {
			array.put(vehicle.getId());
		}
		
		

		return object;
	}
	
	public void enter ( Vehicle v) {
		
		if ( v.getLocation() == 0 && v.getSpeed() == 0) {
			vehicles.add(v);
			
		}
		
		else {
			throw new IllegalArgumentException(" Localizacion o velocidad = 0");
		}
	}
	
	public void exit (Vehicle v) {
		vehicles.remove(v);
	}
	
	public void setWeather ( Weather w ) {
		if ( w != null) {
			weather = w;
		}
		else {
			throw new IllegalArgumentException(" Entrada invalida");
		}
	}
	
	public void addContamination(int c){
		if ( c < 0) {
			throw new IllegalArgumentException(" Entrada invalida (c < 0)");
		}
		
		else {
			totalContamination += c;
		}
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);

	public int getLength() {
		
		return length;
	}
	
	public Junction getDest() {
		return destination;
	}
	
	public Junction getSrc() {
		return source;
	}
	
	public Weather getWeather () {
		return weather;
	}
	
	public int getContLimit() {
		return contaminationAlarmLimit;
	}
	
	public int getMaxSpeed() {
		return _maxSpeed;
	}
	
	public int getTotalCO2() {
		return totalContamination;
	}
	
	public int getSpeedLimit () {
		return currentSpeedLimit;
	}
	
	public List <Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicles);
	}
	
}
