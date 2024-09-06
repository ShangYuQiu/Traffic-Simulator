package simulator.model;

public abstract class NewRoadEvent extends Event{

	protected String _id;
	protected String source; // id de source
	protected String destination; // id de dest
	protected int length;
	protected int _maxSpeed;
	protected int contaminationAlarmLimit;
	protected Weather weather;
	
	public NewRoadEvent(int time, String id, String srcJun, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather)
			{
			super(time);

			this._id = id;
			this.source = srcJun;
			this.destination = destJunc;
			this._maxSpeed = maxSpeed;
			this.contaminationAlarmLimit = co2Limit;
			this.length = length;
			this.weather = weather;
			}

	@Override
	public void execute(RoadMap map) { // crea la carretera y lo añade al mapa
				
		map.addRoad(createRoadObject(map));
		
	}
	
	abstract protected Road createRoadObject(RoadMap map);
	// cityRoad e InterCityRoad creara su propio objecto
	
	
	@Override
	public String toString() {
		return "New Road '"+_id+"'";
	}
}
