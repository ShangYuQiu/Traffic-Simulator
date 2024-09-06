package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction> listCruce;
	private List<Road> roadList;
	private List<Vehicle> vehiclesList;
	private Map<String, Junction> mapCruces;
	private Map <String, Road> mapRoad;
	private Map<String, Vehicle> mapVehicles;
	
	
	public RoadMap() {
		
		this.listCruce = new ArrayList<>();
		this.roadList = new ArrayList<>();
		this.vehiclesList = new ArrayList<>();
		this.mapCruces = new HashMap<String, Junction>();
		this.mapRoad = new HashMap<String, Road>();
		this.mapVehicles = new HashMap<String, Vehicle>();
	};
	
	public void addJunction ( Junction j) {
		
		if (mapCruces.containsKey(j.getId())) { // comprueba que no existe el cruce
			throw new IllegalArgumentException(" Ya existe este cruce");
		}
		
		else {
		listCruce.add(j);
		// modificar mapa
		mapCruces.put(j.getId(), j);
		}
	}
	
	public void addRoad ( Road r) {
		// comprueba que no existe la road
		if (mapRoad.containsKey(r.getId()) && mapCruces.containsValue(r.getDest()) && mapCruces.containsValue(r.getSrc())) {
			throw new IllegalArgumentException(" Ya existe este road");
		}
		
		
		else {
			roadList.add(r);
			
			// modificar mapa raod 
			mapRoad.put(r.getId(), r);
		}
		
	}
	
	public void addVehicle( Vehicle v) {
		
		//i.
		if ( mapVehicles.containsKey(v.getId())) {// comprueba que no existe el vehiculo
			throw new IllegalArgumentException(" Ya existe este vehiculo");
		}
		// ii.
		
		List<Junction> iti = v.getItinerary();
		
		for ( int i = 0; i < iti.size() -1; i++) {
			if ( iti.get(i).roadTo(iti.get(i+1)) == null) {
				throw new IllegalArgumentException("El itinerario no es valido");
			}
		}

		vehiclesList.add(v);
		// modificar mapa
		mapVehicles.put(v.getId(), v);
		
	}
	
	
	public void reset() {
		this.listCruce.clear();
		this.roadList.clear();
		this.vehiclesList.clear();
		this.mapCruces.clear();
		this.mapRoad.clear();
		this.mapVehicles.clear();
	}
	
	public JSONObject report() {
		
		JSONObject jsonObject = new JSONObject();
		
		JSONArray junctionArray = new JSONArray();
		JSONArray roadArray = new JSONArray();
		JSONArray vehicleArray = new JSONArray();
		
		jsonObject.put("junctions", junctionArray);
		
		for (Junction j: listCruce ) {
			
			junctionArray.put(j.report());
		}

		jsonObject.put("roads", roadArray);
		
		for (Road r: roadList ) {
			
			roadArray.put(r.report());
		}
		
		
		jsonObject.put("vehicles", vehicleArray);
		
		for (Vehicle v: vehiclesList ) {
			
			vehicleArray.put(v.report());
		}
		
		return jsonObject;
	}
	
	public Junction getJunction( String id) {
		
		if ( !mapCruces.containsKey(id)) {
			return null;
		}
		
		else {
		return mapCruces.get(id);}
	}
	
	public Road getRoad(String id) {
		if ( !mapRoad.containsKey(id)) {
			return null;
		}
		
		else {
		return mapRoad.get(id);}
	}
	
	public Vehicle getVehicle (String id) {
		
		if ( !mapVehicles.containsKey(id)) {
			return null;
		}
		else {
		return mapVehicles.get(id);}
	}
	
	public List<Junction> getJunctions(){
		
		return Collections.unmodifiableList(listCruce);
	}
	
	public List<Road> getRoads(){
		
		return Collections.unmodifiableList(roadList);

	}
	
	public List<Vehicle> getVehicles(){
		
		return Collections.unmodifiableList(vehiclesList);

	}
}
