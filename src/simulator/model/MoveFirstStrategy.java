package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{

	public MoveFirstStrategy() {};
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// TODO Auto-generated method stub
		List<Vehicle> aux = new ArrayList<Vehicle>(1);
		
		if(q.size()!=0) {
			aux.add(q.get(0));
		}
		return aux;
	}

}
