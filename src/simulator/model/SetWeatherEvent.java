package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	private List<Pair<String,Weather>> _ws;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		
		if ( ws.size() == 0) {
			throw new IllegalArgumentException(" La lista ws es null");
		}
		
		this._ws = ws;
	}

	@Override
	void execute(RoadMap map) {

		for ( int i =0; i < _ws.size(); i++) {
			
			Road r = map.getRoad(_ws.get(i).getFirst());
			
			if ( r != null ) { // actualiza el weather si existe
				r.setWeather(_ws.get(i).getSecond());
			}
			
			else {
				throw new IllegalArgumentException("La carretera no existe en el mapa de carreteras");
			}
		}
		
	}
	
	@Override
	public String toString() {
		String s = "Change Weather: [ ";

		for ( int i =0; i < _ws.size(); i++) {
			
			Pair<String, Weather> p = _ws.get(i);
			
			s+= " ( " + p.getFirst() + "," + p.getSecond().toString() + " ) ";
		}
		
		s+= " ]" ;
		return s;
	}
}
