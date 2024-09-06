package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder{

	public NewCityRoadEventBuilder() {
		super("new_city_road");

	}

	@Override
	protected Event createTheRoad(JSONObject data) {

		if ( data.isEmpty()) {
			throw new IllegalArgumentException("data vacia");
		}
		
		else {

			return new NewCityRoadEvent(data.getInt("time"), data.getString("id"), 
					data.getString("src"), data.getString("dest"), data.getInt("length"),
					data.getInt("co2limit"), data.getInt("maxspeed"), Weather.valueOf(data.getString("weather")));
		}
	}

}
