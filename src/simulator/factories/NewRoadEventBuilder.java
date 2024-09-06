package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;

public abstract class NewRoadEventBuilder extends Builder<Event>{

	NewRoadEventBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {

		return createTheRoad(data);
	}
	
	abstract protected Event createTheRoad(JSONObject data); 
	// pasa la data a las clases hijas 
	//y se construira la clase correspondiente

}
