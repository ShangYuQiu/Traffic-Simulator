package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		
		int timeSlot =1; // timeslot por defecto
		
		if ( !data.isEmpty() && data.has("timeslot")) {
			
			timeSlot = data.getInt("timeslot");
				
		}
		
		
		return new MostCrowdedStrategy(timeSlot);
	}
}

