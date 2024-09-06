package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{

	private int timeSlot;
	
	public MostCrowdedStrategy (int timeSlot) {
		
		if ( timeSlot < 0) {
			
			throw new IllegalArgumentException("Invalid timeSlot");
		}
		
		
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		
		if( roads.size() == 0) { // si la carretera esta vacia

			return -1;
		}
		
		else if (currGreen != -1 && 
				(currTime - lastSwitchingTime) < timeSlot) {
			return currGreen;
		}
		
		else {
			return searchNextGreen(qs, (currGreen + 1) % roads.size());
		}
		
	}

	private int searchNextGreen(List<List<Vehicle>> qs, int start) {

		int max = qs.get(start).size();
		int maxIndex = start;
		
		int aux = (start + 1) % qs.size();
		
		while  ( aux != start) {
			
			int s = qs.get(aux).size();
			
			if ( s > max) {
				max = s;
				maxIndex = aux;
			}
			
			aux = (aux + 1) % qs.size();
		}

		return maxIndex;
	}
	

}
