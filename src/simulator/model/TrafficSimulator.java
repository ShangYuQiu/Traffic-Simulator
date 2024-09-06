package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	private RoadMap mapRoad;
	private List <Event> listEvents;
	private int time;
	private List<TrafficSimObserver> listObservables;
	
	public TrafficSimulator() {
		
		mapRoad = new RoadMap();
		listEvents = new SortedArrayList<Event>();
		time = 0;
		listObservables = new ArrayList<TrafficSimObserver>();
	};
	
	public void reset() {
		mapRoad.reset();
		listEvents.clear();
		time = 0;
		
		for ( TrafficSimObserver to: listObservables) {
			to.onReset(mapRoad, listEvents, time);
		}
		
	}
	
	
	public void addEvent(Event e) {
		
		listEvents.add(e);
		
		for ( TrafficSimObserver to: listObservables) {
			to.onEventAdded(mapRoad, listEvents, e, time);
		}
	}
	
	public void advance() {
		
		time += 1;

		for ( TrafficSimObserver to: listObservables) {
			to.onAdvanceStart(mapRoad, listEvents, time);
		}

		while ( listEvents.size() > 0 && listEvents.get(0).getTime() == time) {
			listEvents.remove(0).execute(mapRoad);
		}
		
		try {
			for ( Junction j : mapRoad.getJunctions()) {
				j.advance(time);
			}
			
			for ( Road r : mapRoad.getRoads()) {
				r.advance(time);
			}
		}
		catch ( IllegalArgumentException ie) {
			
			for ( TrafficSimObserver to: listObservables) {
				to.onError(ie.getMessage());
			}
			
			throw ie;
		}
		for ( TrafficSimObserver to: listObservables) {
			to.onAdvanceEnd(mapRoad, listEvents, time);
		
		}
	}
	
	public JSONObject report() {
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("time", time);
		
		jsonObject.put("state", mapRoad.report());
		
		return jsonObject;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		
		if( !listObservables.contains(o)) {
			listObservables.add(o);
			o.onRegister(mapRoad, listEvents, time);

		}

	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		listObservables.remove(o);
		
	}
}
