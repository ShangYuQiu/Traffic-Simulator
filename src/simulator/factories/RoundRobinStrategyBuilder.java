package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {

		int timeSlot =1;// timeSlot por defecto
		
		if ( !data.isEmpty()) {
			
			if (data.has("timeslot")) {
			timeSlot = data.getInt("timeslot");
			}	
		}
		
		
		return new RoundRobinStrategy(timeSlot);
	}

}
