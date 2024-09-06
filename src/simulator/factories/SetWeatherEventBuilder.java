package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	public SetWeatherEventBuilder() {
		super("set_weather");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		if ( data.isEmpty()) {
			throw new IllegalArgumentException("data vacia");
		}
		
		/*else if ( !data.has("set_weather")) {
			return null;
		}*/
		else {
			
			JSONArray array = data.getJSONArray("info");
			List<Pair<String,Weather>> ws = new ArrayList<Pair<String,Weather>>();
			
			for ( int i =0; i < array.length();i++) {
				
				Pair<String, Weather> p = 
						new Pair<String, Weather>(array.getJSONObject(i).getString("road"),
								Weather.valueOf(array.getJSONObject(i).getString("weather")));
				ws.add(p);
			}
			
			return new SetWeatherEvent(data.getInt("time"), ws);
		}
	}

}
