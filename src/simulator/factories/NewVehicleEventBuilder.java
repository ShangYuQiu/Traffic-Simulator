package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	public NewVehicleEventBuilder() {
		super("new_vehicle");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		if(data.isEmpty()) {
            throw new IllegalArgumentException("data vacia");
        }

        else {
        	
        	List<String> itinerary = new ArrayList<String>();  
        	JSONArray it=data.getJSONArray("itinerary");
        	
        	for (int i = 0; i <it.length();i++ ) {// recoge los ids en una lista
        		itinerary.add(it.getString(i));
        		
        	}
        	
            return new NewVehicleEvent(data.getInt("time"), data.getString("id"), 
            		data.getInt("maxspeed"), 
                    data.getInt("class"), itinerary);
        }
      
	}

}
