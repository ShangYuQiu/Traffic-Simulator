package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent{

	public NewInterCityRoadEvent(int time, String id, String srcJun,
			String destJunc, int length, int co2Limit, int maxSpeed, Weather
			weather) {
			
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
			// ...
	}
	
	protected Road createRoadObject(RoadMap map) {
		
		return new InterCityRoad( _id,  map.getJunction(source),  map.getJunction(destination), 
				 _maxSpeed, contaminationAlarmLimit, length, weather );
		
	}
}
