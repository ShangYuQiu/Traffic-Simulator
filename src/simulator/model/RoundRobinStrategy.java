package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {

private int timeSlot;
	
	public RoundRobinStrategy (int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		// TODO Auto-generated method stub
		
		if( roads.size() == 0) {

			return -1;
		}
		
		else if ( currGreen == -1) {
			
			return 0;
		}
		else if ((currTime - lastSwitchingTime) < timeSlot) {
			return currGreen;
		}
		
		else {
			return ((currGreen+1)%roads.size());
		}
	}

}
