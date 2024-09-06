package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewVehicleEvent extends Event{

	private String _id;
	private int max_Speed;
	private List<String> itinerary;
	private int contamination;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int
			contClass, List<String> itinerary) {
			super(time);
			
			this._id = id;
			this.max_Speed = maxSpeed;
			this.contamination = contClass;
			this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
			}

	@Override
	void execute(RoadMap map) {
		
		List < Junction> j = new ArrayList<Junction>();
		String s ;
		
		for ( int i = 0; i < itinerary.size(); i++) { // añade todos los junctions de la lista al mapa
			s = itinerary.get(i);
			j.add(map.getJunction(s));
		}
		Vehicle v = new Vehicle(_id, max_Speed, contamination, j);
		
		map.addVehicle(v);
		v.moveToNextRoad();
		
	}
	
	@Override
	public String toString() {
		return "New Vehicle '"+_id+"'";
	}
}
