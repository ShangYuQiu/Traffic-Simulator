package simulator.model;

import org.json.JSONObject;

public abstract class SimulatedObject {

	protected String _id;

	SimulatedObject(String id) {
		
		if ( id == "" || id == null ) {
			throw new IllegalArgumentException("id invalido");
		}
		_id = id;
	}

	public String getId() {
		return _id;
	}

	@Override
	public String toString() {
		return _id;
	}

	abstract public void advance(int time);

	abstract public JSONObject report();
}
