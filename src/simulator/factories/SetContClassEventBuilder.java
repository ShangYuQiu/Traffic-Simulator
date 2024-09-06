package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{

	public SetContClassEventBuilder() {
		super("set_cont_class");
	
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		if ( data.isEmpty()) {
			throw new IllegalArgumentException("data vacia");
		}

		else {
			
			JSONArray array = data.getJSONArray("info");
			List<Pair<String,Integer>> cs = new ArrayList<Pair<String,Integer>>();
			
			for ( int i =0; i < array.length();i++) {
				
				Pair<String, Integer> p = 
						new Pair<String, Integer>(array.getJSONObject(i).getString("vehicle"),
							(array.getJSONObject(i).getInt("class")));
				cs.add(p);
			}
			
			return new SetContClassEvent(data.getInt("time"), cs);
		}
	}

}
