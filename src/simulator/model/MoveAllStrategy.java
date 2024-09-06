package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	public MoveAllStrategy() {};
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {

		List<Vehicle> aux = new ArrayList<Vehicle>();  

		if(q.size()!=0) {
			aux.addAll(q);
		}
		return aux;
	}

}
