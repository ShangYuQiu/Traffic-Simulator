package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event{

	private List<Pair<String,Integer>> _cs;
	
	public SetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		
		if ( cs.size() == 0) {
			throw new IllegalArgumentException(" La lista cs es null");
		}
		
		this._cs = cs;
	} 


	@Override
	void execute(RoadMap map) {
		
		for ( int i =0; i < _cs.size(); i++) {
			
			Vehicle v = map.getVehicle(_cs.get(i).getFirst());
			
			if ( v != null ) { // actualiza la contaminacion si existe
				v.setContClass(_cs.get(i).getSecond());
			}
			
			else {
				throw new IllegalArgumentException("El vehiculo no existe en el mapa de carreteras");
			}
		}
		
	}
	
	@Override
	public String toString() {
		String s = "Change CO2 Class: [ ";

		for ( int i =0; i < _cs.size(); i++) {
			
			Pair<String, Integer> p = _cs.get(i);
			
			s+= " ( " + p.getFirst() + "," + p.getSecond() + " ) ";
		}
		
		s+= " ]" ;
		return s;
	}
	
}
