package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class Junction extends SimulatedObject{

	private List<Road> roadEnt;
	private Map<Junction, Road> mapRoadSal;
	private List<List<Vehicle>>  colaList;
	private Map <Road, List<Vehicle>> carreteraCola;
	private int indGreen;
	private int lastChangeGreen;
	private LightSwitchingStrategy strategySemf;
	private DequeuingStrategy strategyElem;
	private int x;
	private int y;
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
			
		super(id);
		
		if ( lsStrategy == null  ||  dqStrategy == null || xCoor < 0 || yCoor < 0) {
			
			throw new IllegalArgumentException(" Entradas invalidas");
		}
		
		this.roadEnt = new ArrayList<Road>();
		this.mapRoadSal = new HashMap<Junction, Road>();
		this.colaList = new ArrayList<>();
		this.carreteraCola = new HashMap<>();
		this.indGreen=-1;
		this.lastChangeGreen=0;
		this.strategySemf = lsStrategy;
		this.strategyElem = dqStrategy;
		this.x = xCoor;
		this.y = yCoor;
	}
	
	
	public void addIncomingRoad(Road road) {
		
		if ( !this.equals(road.getDest())) {
			throw new IllegalArgumentException( "No es el mismo cruce");
		}
		
		//añade la carretera
		roadEnt.add(road);
		
		List <Vehicle> cola = new LinkedList<Vehicle>();
		colaList.add(cola);
		carreteraCola.put(road, cola); // pone la carretera a la mapa
	}
	
	
	public void addOutgoingRoad(Road road) {
		
		if ( !this.equals(road.getSrc())) {
			throw new IllegalArgumentException( "No es el mismo cruce");
		}
		
		else if (mapRoadSal.get(road.getDest()) != null ) {
			throw new IllegalArgumentException( "Hay mas de un cruce");
		}
		
		mapRoadSal.put(road.getDest(), road);// pone la carretera a la mapa
		
	}
	
	public void enter(Vehicle v) {
		
		Road r = v.getRoad();
		List<Vehicle> q = carreteraCola.get(r);
		q.add(v);
		
	}
	
	public Road roadTo (Junction j) {// devuelve la carretera saliente que corresponde a j
		
		Road r = null;
		
		r = mapRoadSal.get(j);
		
		return r;
	}


	@Override
	public void advance(int time) {
		
		if (indGreen != -1 ) { // si el sem no es rojo
			
			List <Vehicle> l = colaList.get(indGreen);
			List <Vehicle> listAvanza = strategyElem.dequeue(l);
			
			for (Vehicle v: listAvanza) { // los vehiculos avanza
				v.moveToNextRoad();
				l.remove(v);
			}
			
		}
		
		// cambia el ind Green
		int i = strategySemf.chooseNextGreen(roadEnt, colaList, indGreen, lastChangeGreen, time);
		
		if ( indGreen != i) {
			indGreen = i;
			lastChangeGreen = time;
		}
		
		
	}


	@Override
	public JSONObject report() {

		JSONObject object = new JSONObject();
		String s = "";
		
		object.put("id", _id);
		
		if ( indGreen == -1) {
			
			s = "none";
		}
		else {
			s = roadEnt.get(indGreen).getId();
		}
		object.put( "green", s);
		
		JSONArray array = new JSONArray();
		object.put("queues", array);
		
		for ( int i = 0; i < roadEnt.size();i++) {
			
			JSONObject objRoad = new JSONObject();
			array.put(objRoad);
			objRoad.put("road", roadEnt.get(i).getId());
			
			
			JSONArray array2 = new JSONArray();
			objRoad.put("vehicles", array2);
			
			for ( Vehicle vehicle : carreteraCola.get(roadEnt.get(i))) {
				array2.put(vehicle.getId());
			}
		}
		
		return object;
	}
	
	
	public int getX () {
		return x;
	}
	
	public int getY () {
		return y;
	}


	public int getGreenLightIndex() {
		// TODO Auto-generated method stub
		return indGreen;
	}


	public List<Road> getInRoads() {
		// TODO Auto-generated method stub
		return roadEnt;
	}
	
	public Map <Road, List<Vehicle>> getCola ()
	{
		return carreteraCola;
	}
}
